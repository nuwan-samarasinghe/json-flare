package com.jsonflare.lib.jsonflare.jsontoflat.configs;

import com.fasterxml.jackson.databind.JsonNode;
import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareRuntimeException;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RecursiveAction;

import static com.jsonflare.lib.jsonflare.common.configs.Constants.ARRAY_NODE;
import static com.jsonflare.lib.jsonflare.common.configs.Constants.OBJECT_NODE;

/**
 * Author: NUWAN
 * Date: 2024-01-17
 * Description:
 * Implementing a flat-file creation task
 */
@Slf4j
public class FlatFileCreationTask extends RecursiveAction {


    private final YmlConfiguration ymlConfiguration;
    private final JsonNode jsonNode;
    private final StringBuilder flatFile;

    public FlatFileCreationTask(YmlConfiguration ymlConfiguration, JsonNode jsonNode, StringBuilder flatFile) {
        this.ymlConfiguration = ymlConfiguration;
        this.jsonNode = jsonNode;
        this.flatFile = flatFile;
    }

    @Override
    protected void compute() {
        for (YmlConfiguration ymlConfig : ymlConfiguration.getProperties()) {
            if (ymlConfig.getDataType().equals(OBJECT_NODE)) {
                invokeAll(new FlatFileCreationTask(ymlConfig, jsonNode.get(ymlConfig.getName()), flatFile));
            } else if (ymlConfig.getDataType().equals(ARRAY_NODE)) {
                log.error("Not implemented for this version");
                throw new JsonFlareRuntimeException("Not implemented for this version");
            } else {
                flatFile.append(jsonNode.get(ymlConfig.getName()).toString().replace("\"", ""));
            }
        }
    }
}
