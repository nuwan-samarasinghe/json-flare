package com.jsonflare.lib.jsonflare.service;

import org.springframework.beans.factory.annotation.Autowired;
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
