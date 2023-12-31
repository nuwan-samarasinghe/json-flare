/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * yml configuration wrapper object
 */
package com.jsonflare.lib.jsonflare.common.ymlconfig.models;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class YmlConfigurationMap {
    private final Map<String, YmlConfiguration> jsonToFlatFileConfigurationMap = new HashMap<>();
    private final Map<String, YmlConfiguration> FlatFileToJsonConfigurationMap = new HashMap<>();
}
