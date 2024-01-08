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
    private final Map<String, FlatFileToJsonConfigurationWrapper> flatFileToJsonConfigurationWrapperMap;

    public FlatFileToJsonServiceImpl(Map<String, FlatFileToJsonConfigurationWrapper> flatFileToJsonConfigurationWrapperMap) {
        this.flatFileToJsonConfigurationWrapperMap = flatFileToJsonConfigurationWrapperMap;
    }

    @Override
    public String convert(String className, String data) throws JsonFlareException {
        try {
            FlatFileToJsonConfigurationWrapper flatFileToJsonConfigurationWrapper = flatFileToJsonConfigurationWrapperMap.get(className);
            FieldSet tokenize = flatFileToJsonConfigurationWrapper.getFixedLengthTokenizer().tokenize(data);
            return createJsonObject(tokenize, flatFileToJsonConfigurationWrapper);
        } catch (Exception ex) {
            throw new JsonFlareException("An error occurred while converting the data into json", ex);
        }
    }

    private String createJsonObject(FieldSet tokenize,
                                    FlatFileToJsonConfigurationWrapper flatFileToJsonConfigurationWrapper) throws JsonFlareException {
        //TODO need to do the data conversions
        YmlConfiguration ymlConfiguration = flatFileToJsonConfigurationWrapper.getYmlConfigurationMap();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode root = objectMapper.createObjectNode();
        for (YmlConfiguration ymlConfig : ymlConfiguration.getProperties()) {
            if (!(ymlConfig.getDataType().equals(OBJECT_NODE)
                    || ymlConfig.getDataType().equals(ARRAY_NODE)
                    || ymlConfig.getDataType().equals(STRING))) {
                throw new JsonFlareException("Invalid Data type provided in the configuration please check");
            }
            if (ymlConfig.getDataType().equals(OBJECT_NODE)) {
                root.set(ymlConfig.getName(), objectMapper.createObjectNode());
                for (YmlConfiguration configuration : ymlConfig.getProperties()) {
                    createObjectNode(objectMapper, configuration, tokenize, (ObjectNode) root.get(ymlConfig.getName()));
                }
            } else if (ymlConfig.getDataType().equals(ARRAY_NODE)) {
                root.set(ymlConfig.getName(), createArrayNode(objectMapper, ymlConfig, tokenize));
            } else {
                root.put(ymlConfig.getName(), "testing");
            }
        }
        return root.toPrettyString();
    }

    private JsonNode createArrayNode(ObjectMapper objectMapper,
                                     YmlConfiguration ymlConfig,
                                     FieldSet tokenize) {
        return null;
    }

    private void createObjectNode(ObjectMapper objectMapper,
                                  YmlConfiguration ymlConfig,
                                  FieldSet tokenize,
                                  ObjectNode node) {
        if (!(ymlConfig.getDataType().equals(OBJECT_NODE)
                || ymlConfig.getDataType().equals(ARRAY_NODE))) {
            node.put(ymlConfig.getName(), tokenize.readString(ymlConfig.getName()));
        } else {
            node.set(ymlConfig.getName(), objectMapper.createObjectNode());
            for (YmlConfiguration configuration : ymlConfig.getProperties()) {
                createObjectNode(objectMapper, configuration, tokenize, (ObjectNode) node.get(ymlConfig.getName()));
            }
        }
    }
}
