package com.jsonflare.lib.jsonflare.jsontoflat.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfiguration;
import com.jsonflare.lib.jsonflare.jsontoflat.configs.FlatFileCreationTask;
import com.jsonflare.lib.jsonflare.jsontoflat.service.JsonToFlatFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ForkJoinPool;

/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * implementation class for the Json to Flat-File conversion
 */
@Slf4j
@Service
public class JsonToFlatFileServiceImpl implements JsonToFlatFileService {

    @Override
    public String convert(YmlConfiguration ymlConfiguration, String data) throws JsonFlareException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(data);
            StringBuilder flatFile = new StringBuilder();
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            if (jsonNode instanceof ArrayNode) {
                for (JsonNode jsNode : jsonNode) {
                    forkJoinPool.invoke(new FlatFileCreationTask(ymlConfiguration, jsNode, flatFile));
                }
            } else if (jsonNode instanceof ObjectNode) {
                forkJoinPool.invoke(new FlatFileCreationTask(ymlConfiguration, jsonNode, flatFile));
                log.info("Using fork join to generate the flat file and configs are {}", forkJoinPool);
            } else {
                log.error("Invalid Json provided");
                throw new JsonFlareException("Invalid Json provided");
            }
            return flatFile.toString();
        } catch (JsonProcessingException e) {
            log.error("Invalid Json provided", e);
            throw new JsonFlareException("Invalid Json provided", e);
        }
    }
}
