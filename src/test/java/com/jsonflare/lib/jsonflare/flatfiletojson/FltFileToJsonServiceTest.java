package com.jsonflare.lib.jsonflare.flatfiletojson;

import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;
import com.jsonflare.lib.jsonflare.common.ymlconfig.config.JsonFlareYmlConfiguration;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfigurationMap;
import com.jsonflare.lib.jsonflare.flatfiletojson.config.FlatFileToJsonConfigurator;
import com.jsonflare.lib.jsonflare.flatfiletojson.service.FlatFileToJsonService;
import com.jsonflare.lib.jsonflare.flatfiletojson.service.impl.FlatFileToJsonServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Author: NUWAN
 * Date: 2024-01-07
 * Description:
 * flat file to json converter service test class
 */
@SpringBootTest(classes = {
        JsonFlareYmlConfiguration.class,
        FlatFileToJsonServiceImpl.class,
        FlatFileToJsonConfigurator.class})
public class FltFileToJsonServiceTest {

    @Autowired
    private FlatFileToJsonService flatFileToJsonService;

    @Autowired
    private YmlConfigurationMap ymlConfigurationMap;

    @Test
    public void givenFLatFileText_whenConvertToJson_thenReturnStringJson() throws JsonFlareException {
        flatFileToJsonService.convert("PersonalDetailsMultiMultiLevel2", "John Doe  30 123 Main St         Example City                  12345ABC CorporationTechnology");
    }

}
