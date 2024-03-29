package com.jsonflare.lib.jsonflare.flatfiletojson.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;
import com.jsonflare.lib.jsonflare.flatfiletojson.configs.JsonObjectCreationTask;
import com.jsonflare.lib.jsonflare.flatfiletojson.models.FlatFileToJsonConfigurationWrapper;
import com.jsonflare.lib.jsonflare.flatfiletojson.services.FlatFileToJsonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

/**
 * Author: NUWAN
 * Date: 2024-01-14
 * Description:
 * implementing parallel recursion to build a json object
 */
@Slf4j
@Service
public class FlatFileToJsonServiceImpl implements FlatFileToJsonService {

    private final Map<String, FlatFileToJsonConfigurationWrapper> flatFileToJsonConfigurationWrapperMap;

    public FlatFileToJsonServiceImpl(Map<String, FlatFileToJsonConfigurationWrapper> flatFileToJsonConfigurationWrapperMap) {
        this.flatFileToJsonConfigurationWrapperMap = flatFileToJsonConfigurationWrapperMap;
    }

    @Override
    public String convert(String className, String data) throws JsonFlareException {
        try {
            long startTime = System.currentTimeMillis();
            FlatFileToJsonConfigurationWrapper flatFileToJsonConfigurationWrapper = flatFileToJsonConfigurationWrapperMap.get(className);
            List<String> dataList = textSplitter(flatFileToJsonConfigurationWrapper.getMaxLength(), data);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonText;
            if (dataList.size() == 1) {
                jsonText = concurrentJsonObjectBuilder(dataList.get(0), flatFileToJsonConfigurationWrapper, objectMapper).toString();
            } else if (dataList.size() > 1
                    && !StringUtils.hasText(flatFileToJsonConfigurationWrapper.getYmlConfigurationMap().getName())) {
                ArrayNode arrayNode = objectMapper.createArrayNode();
                arrayNode.addAll(dataList.parallelStream()
                        .map(val -> concurrentJsonObjectBuilder(val, flatFileToJsonConfigurationWrapper, objectMapper))
                        .toList());
                jsonText = arrayNode.toString();
            } else if (dataList.size() > 1
                    && StringUtils.hasText(flatFileToJsonConfigurationWrapper.getYmlConfigurationMap().getName())) {
                ObjectNode objectNode = objectMapper.createObjectNode();
                ArrayNode arrayNode = objectMapper.createArrayNode();
                arrayNode.addAll(dataList.parallelStream()
                        .map(val -> concurrentJsonObjectBuilder(val, flatFileToJsonConfigurationWrapper, objectMapper))
                        .toList());
                objectNode.set(flatFileToJsonConfigurationWrapper.getYmlConfigurationMap().getName(), arrayNode);
                jsonText = objectNode.toString();
            } else {
                log.error("No data to process");
                throw new JsonFlareException("No data to process");
            }
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            log.info("The JSON object was generated in {} milliseconds.", executionTime);
            return jsonText;
        } catch (Exception ex) {
            log.error("An error occurred while converting the data into json", ex);
            throw new JsonFlareException("An error occurred while converting the data into json", ex);
        }
    }

    /**
     * split the given flat file into equal size chunks
     */
    private List<String> textSplitter(Integer maxLength, String data) throws JsonFlareException {
        List<String> values = new ArrayList<>();
        if (data.length() % maxLength != 0) {
            log.error("Invalid data length");
            throw new JsonFlareException("Invalid data length");
        } else {
            int numberOfBrakes = data.length() / maxLength;
            StringBuilder builder = new StringBuilder(maxLength);
            for (int i = 0; i < numberOfBrakes; i++) {
                int start = i * maxLength;
                int end = start + maxLength;
                builder.setLength(0);
                values.add(builder.append(data, start, end).toString());
            }
        }
        log.info("No of split text count {}", values.size());
        return values;
    }

    /**
     * concurrently build the json object
     */
    private ObjectNode concurrentJsonObjectBuilder(
            String data,
            FlatFileToJsonConfigurationWrapper flatFileToJsonConfigurationWrapper,
            ObjectMapper objectMapper) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        FieldSet tokenize = flatFileToJsonConfigurationWrapper.getFixedLengthTokenizer().tokenize(data);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new JsonObjectCreationTask(objectMapper, objectNode, flatFileToJsonConfigurationWrapper.getYmlConfigurationMap(), tokenize));
        log.info("Using fork join to generate the json file and configs are {}", forkJoinPool);
        return objectNode;
    }
}
