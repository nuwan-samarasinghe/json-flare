package com.jsonflare.lib.jsonflare.ymlconfig;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(JsonFlareProperties.class)
public class JsonFlareAutoConfiguration {

    private final JsonFlareProperties jsonFlareProperties;

    public JsonFlareAutoConfiguration(JsonFlareProperties jsonFlareProperties) {
        this.jsonFlareProperties = jsonFlareProperties;
    }

    @Bean(name="ymlConfigurationMap")
    public Map<String, String> loadConfigurations() {
        Map<String, String> map = new HashMap<>();
        map.put("json", jsonFlareProperties.getJsonConverterYmlLocation());
        map.put("flatfile", jsonFlareProperties.getFlatFileConverterYmlLocation());
        return map;
    }
}
