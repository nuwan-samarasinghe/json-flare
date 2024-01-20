/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * implementation for conversion
 */
package com.jsonflare.lib.jsonflare.service.impl;

import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfigurationMap;
import com.jsonflare.lib.jsonflare.flatfiletojson.services.FlatFileToJsonService;
import com.jsonflare.lib.jsonflare.jsontoflat.service.JsonToFlatFileService;
import com.jsonflare.lib.jsonflare.service.DataFormatConverterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class DataFormatConverterServiceImpl implements DataFormatConverterService {

    private final YmlConfigurationMap ymlConfigurationMap;
    private final JsonToFlatFileService jsonToFlatFileService;
    private final FlatFileToJsonService flatFileToJsonService;

    public DataFormatConverterServiceImpl(
            YmlConfigurationMap ymlConfigurationMap,
            JsonToFlatFileService jsonToFlatFileService,
            FlatFileToJsonService flatFileToJsonService) {
        this.ymlConfigurationMap = ymlConfigurationMap;
        this.jsonToFlatFileService = jsonToFlatFileService;
        this.flatFileToJsonService = flatFileToJsonService;
    }

    @Override
    public String convertFlatFileToJson(String className, String data) throws JsonFlareException {
        if (Objects.isNull(ymlConfigurationMap.getFlatFileToJsonConfigurationMap()) || ymlConfigurationMap.getFlatFileToJsonConfigurationMap().isEmpty()) {
            log.error("No yml configuration available for the given name [{}]", className);
            throw new JsonFlareException(String.format("No yml configuration available for the given name [%s]", className));
        }
        return flatFileToJsonService.convert(className, data);
    }

    @Override
    public String convertJsonToFlatFile(String className, String data) throws JsonFlareException {
        if (Objects.isNull(ymlConfigurationMap.getJsonToFlatFileConfigurationMap()) || ymlConfigurationMap.getJsonToFlatFileConfigurationMap().isEmpty()) {
            log.error("No yml configuration available for the given name [{}]", className);
            throw new JsonFlareException(String.format("No yml configuration available for the given name [%s]", className));
        }
        return jsonToFlatFileService.convert(ymlConfigurationMap.getJsonToFlatFileConfigurationMap().get(className), data);
    }
}
