package com.jsonflare.lib.jsonflare.jsontoflat;

import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;
import com.jsonflare.lib.jsonflare.common.ymlconfig.configs.JsonFlareYmlConfiguration;
import com.jsonflare.lib.jsonflare.flatfiletojson.configs.FlatFileToJsonConfigurator;
import com.jsonflare.lib.jsonflare.flatfiletojson.services.FlatFileToJsonService;
import com.jsonflare.lib.jsonflare.flatfiletojson.services.impl.FlatFileToJsonServiceImpl;
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
public class JsontoFlatFileServiceTest {

    @Autowired
    private FlatFileToJsonService flatFileToJsonService;

    @Test
    public void givenFLatFileText_whenConvertToJson_thenReturnStringJson() throws JsonFlareException {
        flatFileToJsonService.convert("PersonalDetailsMultiMultiLevel2", "John Doe  30 123 Main St         Example City                  12345ABC CorporationTechnology");
    }

    @Test
    public void givenFLatFileText_whenConvertToJson_thenReturnStringJson3() throws JsonFlareException {
        flatFileToJsonService.convert("PersonalDetailsMultiMultiLevel3", "John Doe  30 123 Main St         Example City                  12345      CA         United StatesABC CorporationTechnology");
    }

    @Test
    public void givenFLatFileText_whenConvertToJson_thenReturnStringJson5() throws JsonFlareException {
        flatFileToJsonService.convert("PersonalDetailsMultiMultiLevel5", "John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5");
    }

    @Test
    public void givenFLatFileText_whenConvertToJson_thenReturnStringJson7() throws JsonFlareException {
        flatFileToJsonService.convert("PersonalDetailsMultiMultiLevel5", "John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5John Doe  30 123 Main St         Example City                  12345      CA         United StatesA1B2F3G5");
    }

    @Test
    public void givenFLatFileText_whenConvertToJson_thenReturnStringJson2() throws JsonFlareException {
        flatFileToJsonService.convert("PersonalDetailsMultiMultiLevel", "John Doe  30 123 Main St         Example City                  12345ABC CorporationTechnologyJane Smith     Software EngineJane Smith     Software Engine");
    }

}
