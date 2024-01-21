package com.jsonflare.lib.jsonflare.jsontoflat.configs;

import com.fasterxml.jackson.databind.JsonNode;
import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;
import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareRuntimeException;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfiguration;
import com.jsonflare.lib.jsonflare.jsontoflat.functions.dataprocessfunctions.DataProcessFunction;
import com.jsonflare.lib.jsonflare.jsontoflat.functions.dataprocessfunctions.DataProcessFunctionFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RecursiveAction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final Pattern pattern = Pattern.compile("(\\w+)\\('([^']+)'\\)");
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
                try {
                    flatFile.append(callFunction(ymlConfig.getFunction(), ymlConfig.getMaxLength(), jsonNode.get(ymlConfig.getName()).toString().replace("\"", "")));
                } catch (JsonFlareException e) {
                    throw new JsonFlareRuntimeException("An error occurred while processing the data", e);
                }
            }
        }
    }


    private String callFunction(String function, Integer desiredLength, String originalString) throws JsonFlareException {
        DataProcessFunctionFactory functionFactory = DataProcessFunctionFactory.getInstance();
        Matcher matcher = pattern.matcher(function);
        if (matcher.find()) {
            String functionName = matcher.group(1).toLowerCase();
            String parameter = matcher.group(2);
            log.info("Process data with function [{}] and parameter [{}]", functionName, parameter);
            return functionFactory.getDataProcessFunction(functionName).process(
                    parameter,
                    desiredLength,
                    originalString);
        } else {
            throw new JsonFlareException("Given function is not available");
        }
    }
}
