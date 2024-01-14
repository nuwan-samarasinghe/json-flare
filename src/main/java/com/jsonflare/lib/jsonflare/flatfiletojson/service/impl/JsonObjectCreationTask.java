package com.jsonflare.lib.jsonflare.flatfiletojson.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;
import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareRuntimeException;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfiguration;
import org.springframework.batch.item.file.transform.FieldSet;

import java.util.concurrent.RecursiveAction;

/**
 * Author: NUWAN
 * Date: 2024-01-14
 * Description:
 */
public class JsonObjectCreationTask extends RecursiveAction {
    public static final String OBJECT_NODE = "ObjectNode";
    public static final String ARRAY_NODE = "ArrayNode";
    public static final String STRING = "String";
    public static final String INTEGER = "Integer";
    private final ObjectMapper objectMapper;
    private final ObjectNode root;
    private final YmlConfiguration ymlConfigurationMap;
    private final FieldSet tokenize;

    public JsonObjectCreationTask(ObjectMapper objectMapper, ObjectNode root, YmlConfiguration ymlConfigurationMap, FieldSet tokenize) {
        this.objectMapper = objectMapper;
        this.root = root;
        this.tokenize = tokenize;
        this.ymlConfigurationMap = ymlConfigurationMap;
    }


    @Override
    protected void compute() {
        for (YmlConfiguration ymlConfig : ymlConfigurationMap.getProperties()) {
            if (!(isValidDataType(ymlConfig))) {
                throw new JsonFlareRuntimeException("Invalid Data type provided in the configuration. Please check.");
            }
            if (ymlConfig.getDataType().equals(OBJECT_NODE)) {
                ObjectNode childNode = objectMapper.createObjectNode();
                root.set(ymlConfig.getName(), childNode);
                invokeAll(new JsonObjectCreationTask(objectMapper, childNode, ymlConfig, tokenize));
            } else if (ymlConfig.getDataType().equals(ARRAY_NODE)) {
                throw new JsonFlareRuntimeException("Not implemented for this version");
            } else {
                root.put(ymlConfig.getName(), tokenize.readString(ymlConfig.getName()));
            }
        }
    }

    private boolean isValidDataType(YmlConfiguration ymlConfig) {
        return ymlConfig.getDataType().equals(OBJECT_NODE)
                || ymlConfig.getDataType().equals(ARRAY_NODE)
                || ymlConfig.getDataType().equals(STRING)
                || ymlConfig.getDataType().equals(INTEGER);
    }
}