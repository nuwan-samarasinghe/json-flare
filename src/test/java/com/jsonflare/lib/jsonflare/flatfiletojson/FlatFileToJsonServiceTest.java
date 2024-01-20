package com.jsonflare.lib.jsonflare.flatfiletojson;

import com.jsonflare.lib.jsonflare.common.JsonLoader;
import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;
import com.jsonflare.lib.jsonflare.common.ymlconfig.configs.JsonFlareYmlConfiguration;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfigurationMap;
import com.jsonflare.lib.jsonflare.flatfiletojson.configs.FlatFileToJsonConfigurator;
import com.jsonflare.lib.jsonflare.jsontoflat.service.JsonToFlatFileService;
import com.jsonflare.lib.jsonflare.jsontoflat.service.impl.JsonToFlatFileServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * Author: NUWAN
 * Date: 2024-01-07
 * Description:
 * flat file to json converter service test class
 */
@SpringBootTest(classes = {
        JsonFlareYmlConfiguration.class,
        JsonToFlatFileServiceImpl.class,
        FlatFileToJsonConfigurator.class})
public class FlatFileToJsonServiceTest {

    @Autowired
    private JsonToFlatFileService jsonToFlatFileService;

    @Autowired
    private YmlConfigurationMap ymlConfigurationMap;

    @Test
    public void givenJsonLevel1_whenConvertToFlatFile_thenReturnStringFlatFile() throws JsonFlareException, IOException {
        jsonToFlatFileService
                .convert(
                        ymlConfigurationMap.getJsonToFlatFileConfigurationMap().get("PD_L1"),
                        JsonLoader.getInstance().getJsonFile("level1.json")
                );
    }

    @Test
    public void givenJsonLevel1Array_whenConvertToFlatFile_thenReturnStringFlatFile() throws JsonFlareException, IOException {
        jsonToFlatFileService
                .convert(
                        ymlConfigurationMap.getJsonToFlatFileConfigurationMap().get("PD_L1A"),
                        JsonLoader.getInstance().getJsonFile("arraylist-level1.json")
                );
    }
}
