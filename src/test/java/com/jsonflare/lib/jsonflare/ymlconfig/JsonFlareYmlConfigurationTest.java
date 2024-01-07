package com.jsonflare.lib.jsonflare.ymlconfig;

import com.jsonflare.lib.jsonflare.common.ymlconfig.config.JsonFlareYmlConfiguration;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfiguration;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfigurationMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {JsonFlareYmlConfiguration.class})
public class JsonFlareYmlConfigurationTest {

    @Autowired
    private YmlConfigurationMap ymlConfigurationMap;

    @Test
    public void givenJsonToFlatFileYmlConfig_whenConvertToJavaObject_thenReturnJavaObject() {
        Map<String, YmlConfiguration> jsonToFlatFileConfigurationMap = ymlConfigurationMap.getJsonToFlatFileConfigurationMap();
        assertEquals(1, jsonToFlatFileConfigurationMap.size(), "Configurations are not loaded");
        assertNotNull(jsonToFlatFileConfigurationMap.get("PersonalDetails"), "Class name not available");
    }
}
