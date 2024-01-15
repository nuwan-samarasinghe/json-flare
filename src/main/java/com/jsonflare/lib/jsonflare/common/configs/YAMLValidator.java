package com.jsonflare.lib.jsonflare.common.configs;

import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfiguration;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfigurationMap;

import java.util.Map;
import java.util.Objects;

import static com.jsonflare.lib.jsonflare.common.configs.Constants.ARRAY_NODE;
import static com.jsonflare.lib.jsonflare.common.configs.Constants.CLASS_NAME;
import static com.jsonflare.lib.jsonflare.common.configs.Constants.DATA_TYPE;
import static com.jsonflare.lib.jsonflare.common.configs.Constants.DESCRIPTION;
import static com.jsonflare.lib.jsonflare.common.configs.Constants.NAME;
import static com.jsonflare.lib.jsonflare.common.configs.Constants.OBJECT_NODE;
import static com.jsonflare.lib.jsonflare.common.configs.Constants.PROPERTIES;

/**
 * Author: NUWAN
 * Date: 2024-01-15
 * Description:
 * validating the yml file whether is it in the correct format
 */
public final class YAMLValidator {
    private static final String MSG_TMPL = "Yml Configuration should have [%s] in the configuration [%s].";

    public static void validateStructure(YmlConfigurationMap ymlConfigurationMap) throws JsonFlareException {

        for (Map.Entry<String, YmlConfiguration> entry : ymlConfigurationMap.getFlatFileToJsonConfigurationMap().entrySet()) {
            String className = entry.getKey();
            YmlConfiguration ymlConfiguration = entry.getValue();
            validateNameDescDataType(className, ymlConfiguration);
            if (Objects.isNull(ymlConfiguration.getClassName())) {
                throw new JsonFlareException(String.format(MSG_TMPL, CLASS_NAME, className));
            }
            validateProperties(className, ymlConfiguration);
        }
    }

    private static void validateProperties(String className, YmlConfiguration ymlConfiguration) throws JsonFlareException {
        if (ymlConfiguration.getDataType().equals(OBJECT_NODE) || ymlConfiguration.getDataType().equals(ARRAY_NODE)) {
            if (Objects.isNull(ymlConfiguration.getProperties())) {
                throw new JsonFlareException(String.format(MSG_TMPL, ymlConfiguration.getName() + ":" + PROPERTIES, className));
            }
            for (YmlConfiguration configuration : ymlConfiguration.getProperties()) {
                validateProperty(configuration, className);
            }
        }
    }

    private static void validateNameDescDataType(String className, YmlConfiguration ymlConfiguration) throws JsonFlareException {
        if (Objects.isNull(ymlConfiguration.getName())) {
            throw new JsonFlareException(String.format(MSG_TMPL, NAME, className));
        }
        if (Objects.isNull(ymlConfiguration.getDescription())) {
            throw new JsonFlareException(String.format(MSG_TMPL, DESCRIPTION, className));
        }
        if (Objects.isNull(ymlConfiguration.getDataType())) {
            throw new JsonFlareException(String.format(MSG_TMPL, DATA_TYPE, className));
        }
    }

    private static void validateProperty(YmlConfiguration ymlConfiguration, String className) throws JsonFlareException {
        // Check if the required fields are present for each property
        validateNameDescDataType(className, ymlConfiguration);
        validateProperties(className, ymlConfiguration);
    }

}
