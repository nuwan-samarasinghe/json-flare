/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * implementation for conversion
 */
package com.jsonflare.lib.jsonflare.service;

import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfigurationMap;
import com.jsonflare.lib.jsonflare.jsontoflat.service.JsonToFlatFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class DataFormatConverterServiceImpl implements DataFormatConverterService {

    private final YmlConfigurationMap ymlConfigurationMap;
    private final JsonToFlatFileService jsonToFlatFileService;

    public DataFormatConverterServiceImpl(
            YmlConfigurationMap ymlConfigurationMap,
            JsonToFlatFileService jsonToFlatFileService) {
        this.ymlConfigurationMap = ymlConfigurationMap;
        this.jsonToFlatFileService = jsonToFlatFileService;
    }

    @Override
    public String convertFlatFileToJson(String className) throws JsonFlareException {
        if (Objects.isNull(ymlConfigurationMap.getFlatFileToJsonConfigurationMap()) || ymlConfigurationMap.getFlatFileToJsonConfigurationMap().size() <= 0) {
            throw new JsonFlareException(String.format("No yml configuration available for the given name [%s]", className));
        }
        return jsonToFlatFileService.convert(ymlConfigurationMap.getJsonToFlatFileConfigurationMap().get(className));
    }

    @Override
    public String convertJsonToFlatFile(String className) throws JsonFlareException {
        if (Objects.isNull(ymlConfigurationMap.getJsonToFlatFileConfigurationMap()) || ymlConfigurationMap.getJsonToFlatFileConfigurationMap().size() <= 0) {
            throw new JsonFlareException(String.format("No yml configuration available for the given name [%s]", className));
        }
        return jsonToFlatFileService.convert(ymlConfigurationMap.getJsonToFlatFileConfigurationMap().get(className));
    }
}
