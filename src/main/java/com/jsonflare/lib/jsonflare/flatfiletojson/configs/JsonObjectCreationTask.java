package com.jsonflare.lib.jsonflare.flatfiletojson.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jsonflare.lib.jsonflare.common.configs.Constants;
import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareRuntimeException;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfiguration;
import com.jsonflare.lib.jsonflare.flatfiletojson.functions.datatypetransformers.DataTypeTransformerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.transform.FieldSet;

import java.util.concurrent.RecursiveAction;
import java.util.function.Function;

import static com.jsonflare.lib.jsonflare.common.configs.Constants.*;

/**
 * Author: NUWAN
 * Date: 2024-01-14
 * Description:
 */
@Slf4j
public class JsonObjectCreationTask extends RecursiveAction {
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
                log.error("Invalid Data type provided in the configuration. Please check.");
                throw new JsonFlareRuntimeException("Invalid Data type provided in the configuration. Please check.");
            }
            if (ymlConfig.getDataType().equals(OBJECT_NODE)) {
                ObjectNode childNode = objectMapper.createObjectNode();
                root.set(ymlConfig.getName(), childNode);
                invokeAll(new JsonObjectCreationTask(objectMapper, childNode, ymlConfig, tokenize));
            } else if (ymlConfig.getDataType().equals(ARRAY_NODE)) {
                log.error("Not implemented for this version");
                throw new JsonFlareRuntimeException("Not implemented for this version");
            } else {
                DataTypeTransformerFactory dataTypeTransformerFactory = DataTypeTransformerFactory.getInstance();
                root.putIfAbsent(ymlConfig.getName(),
                        objectMapper.valueToTree(
                                dataTypeTransformerFactory.getTransformer(ymlConfig.getDataType()).transform(
                                        tokenize.readString(ymlConfig.getName())
                                )
                        )
                );
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
