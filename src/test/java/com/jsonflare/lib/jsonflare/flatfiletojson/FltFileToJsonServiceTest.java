package com.jsonflare.lib.jsonflare.flatfiletojson;

import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;
import com.jsonflare.lib.jsonflare.common.ymlconfig.configs.JsonFlareYmlConfiguration;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfigurationMap;
import com.jsonflare.lib.jsonflare.flatfiletojson.configs.FlatFileToJsonConfigurator;
import com.jsonflare.lib.jsonflare.flatfiletojson.services.FlatFileToJsonService;
import com.jsonflare.lib.jsonflare.flatfiletojson.services.impl.FlatFileToJsonServiceImpl;
import com.jsonflare.lib.jsonflare.jsontoflat.service.JsonToFlatFileService;
import com.jsonflare.lib.jsonflare.jsontoflat.service.impl.JsonToFlatFileServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

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
public class FltFileToJsonServiceTest {

    @Autowired
    private JsonToFlatFileService jsonToFlatFileService;

    @Autowired
    private YmlConfigurationMap ymlConfigurationMap;

    @Test
    public void givenFLatFileText_whenConvertToJson_thenReturnStringJson() throws JsonFlareException {
        jsonToFlatFileService.convert(ymlConfigurationMap.getJsonToFlatFileConfigurationMap().get("PersonalDetailsMultiMultiLevel2"),
                "{\"person\":{\"name\":\"John Doe\",\"age\":30,\"address\":{\"street\":\"123 Main St\",\"city\":\"Example City\",\"zipcode\":\"12345\"}},\"company\":{\"name\":\"John Doe\",\"industry\":\"Technology\"}}");
    }

    /*@Test
    public void givenFLatFileText_whenConvertToJson_thenReturnStringJson3() throws JsonFlareException {
        jsonToFlatFileService.convert("PersonalDetailsMultiMultiLevel3", "John Doe  30 123 Main St         Example City                  12345      CA         United StatesABC CorporationTechnology");
    }

    @Test
    public void givenFLatFileText_whenConvertToJson_thenReturnStringJson5() throws JsonFlareException {
        jsonToFlatFileService.convert("PersonalDetailsMultiMultiLevel5", "John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5");
    }

    @Test
    public void givenFLatFileText_whenConvertToJson_thenReturnStringJson7() throws JsonFlareException {
        jsonToFlatFileService.convert("PersonalDetailsMultiMultiLevel5", "John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5");
    }

    @Test
    public void givenFLatFileText_whenConvertToJson_thenReturnStringJson2() throws JsonFlareException {
        jsonToFlatFileService.convert("PersonalDetailsMultiMultiLevel", "John Doe  30 123 Main St         Example City                  12345ABC CorporationTechnologyJane Smith     Software EngineJane Smith     Software Engine");
    }
*/
}
