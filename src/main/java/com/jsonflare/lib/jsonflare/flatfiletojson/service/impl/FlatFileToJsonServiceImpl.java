package com.jsonflare.lib.jsonflare.flatfiletojson.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfiguration;
import com.jsonflare.lib.jsonflare.flatfiletojson.models.FlatFileToJsonConfigurationWrapper;
import com.jsonflare.lib.jsonflare.flatfiletojson.service.FlatFileToJsonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * implementation of the json to flat file conversion service
 */

@Slf4j
@Service
public class FlatFileToJsonServiceImpl implements FlatFileToJsonService {

    public static final String OBJECT_NODE = "ObjectNode";
    public static final String ARRAY_NODE = "ArrayNode";
    public static final String STRING = "String";
    public static final String INTEGER = "Integer";
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
            buildJsonObject(objectMapper, root, flatFileToJsonConfigurationWrapper.getYmlConfigurationMap(), tokenize);
            return root.toPrettyString();
        } catch (Exception ex) {
            throw new JsonFlareException("An error occurred while converting the data into json", ex);
        }
    }

    private void buildJsonObject(ObjectMapper objectMapper,
                                 ObjectNode parent,
                                 YmlConfiguration config,
                                 FieldSet tokenize) throws JsonFlareException {
        for (YmlConfiguration ymlConfig : config.getProperties()) {
            if (!(isValidDataType(ymlConfig))) {
                throw new JsonFlareException("Invalid Data type provided in the configuration. Please check.");
            }
            if (ymlConfig.getDataType().equals(OBJECT_NODE)) {
                ObjectNode childNode = objectMapper.createObjectNode();
                parent.set(ymlConfig.getName(), childNode);
                buildJsonObject(objectMapper, childNode, ymlConfig, tokenize);
            } else if (ymlConfig.getDataType().equals(ARRAY_NODE)) {
                parent.set(ymlConfig.getName(), createArrayNode(objectMapper, ymlConfig, tokenize));
            } else {
                parent.put(ymlConfig.getName(), tokenize.readString(ymlConfig.getName()));
            }
        }
    }

    private boolean isValidDataType(YmlConfiguration ymlConfig) {
        return ymlConfig.getDataType().equals(OBJECT_NODE)
                || ymlConfig.getDataType().equals(ARRAY_NODE)
                || ymlConfig.getDataType().equals(STRING)
                || ymlConfig.getDataType().equals(INTEGER);
    }

    private JsonNode createArrayNode(ObjectMapper objectMapper, YmlConfiguration ymlConfig, FieldSet tokenize) {
        // Implement createArrayNode logic here if required
        return null;
    }
}
