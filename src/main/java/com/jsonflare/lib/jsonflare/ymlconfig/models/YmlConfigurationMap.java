package com.jsonflare.lib.jsonflare.ymlconfig.models;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class YmlConfigurationMap {
    private final Map<String, Map<String, Object>> jsonToFlatFileConfigurationMap = new HashMap<>();
    private final Map<String, Map<String, Object>> FlatFileToJsonConfigurationMap = new HashMap<>();
}
