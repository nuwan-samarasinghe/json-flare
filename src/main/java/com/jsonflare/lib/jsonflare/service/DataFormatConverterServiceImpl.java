/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * implementation for conversion
 */
package com.jsonflare.lib.jsonflare.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DataFormatConverterServiceImpl implements DataFormatConverterService {

    private final Map<String, String> ymlConfigurationMap;

    public DataFormatConverterServiceImpl(Map<String, String> ymlConfigurationMap) {
        this.ymlConfigurationMap = ymlConfigurationMap;
    }

    @Override
    public String convertFlatFileToJson() {
        System.out.println(ymlConfigurationMap);
        return null;
    }

    @Override
    public String convertJsonToFlatFile() {
        return null;
    }
}
