package com.jsonflare.lib.jsonflare.flatfiletojson.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;
import com.jsonflare.lib.jsonflare.flatfiletojson.models.FlatFileToJsonConfigurationWrapper;
import com.jsonflare.lib.jsonflare.flatfiletojson.service.FlatFileToJsonService;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ForkJoinPool;

/**
 * Author: NUWAN
 * Date: 2024-01-14
 * Description:
 * implementing parallel recursion to build json object
 */
@Service
public class FlatFileToJsonServiceImpl implements FlatFileToJsonService {

    private final Map<String, FlatFileToJsonConfigurationWrapper> flatFileToJsonConfigurationWrapperMap;

    public FlatFileToJsonServiceImpl(Map<String, FlatFileToJsonConfigurationWrapper> flatFileToJsonConfigurationWrapperMap) {
        this.flatFileToJsonConfigurationWrapperMap = flatFileToJsonConfigurationWrapperMap;
    }

    @Override
    public String convert(String className, String data) throws JsonFlareException {
        try {
            FlatFileToJsonConfigurationWrapper flatFileToJsonConfigurationWrapper = flatFileToJsonConfigurationWrapperMap.get(className);
            FieldSet tokenize = flatFileToJsonConfigurationWrapper.getFixedLengthTokenizer().tokenize(data);
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode root = objectMapper.createObjectNode();
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            forkJoinPool.invoke(new JsonObjectCreationTask(objectMapper, root, flatFileToJsonConfigurationWrapper.getYmlConfigurationMap(), tokenize));
            return root.toPrettyString();
        } catch (Exception ex) {
            throw new JsonFlareException("An error occurred while converting the data into json", ex);
        }
    }
}
