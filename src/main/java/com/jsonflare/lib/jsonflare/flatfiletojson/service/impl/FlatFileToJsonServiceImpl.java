package com.jsonflare.lib.jsonflare.flatfiletojson.service.impl;

import com.jsonflare.lib.jsonflare.flatfiletojson.models.FlatFileToJsonConfigurationWrapper;
import com.jsonflare.lib.jsonflare.flatfiletojson.service.FlatFileToJsonService;
import lombok.extern.slf4j.Slf4j;
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

    private final Map<String, FlatFileToJsonConfigurationWrapper> flatFileToJsonConfigurationWrapperMap;

    public FlatFileToJsonServiceImpl(Map<String, FlatFileToJsonConfigurationWrapper> flatFileToJsonConfigurationWrapperMap) {
        this.flatFileToJsonConfigurationWrapperMap = flatFileToJsonConfigurationWrapperMap;
    }

    @Override
    public String convert(String className, String data) {
        System.out.println();
        return null;
    }
}
