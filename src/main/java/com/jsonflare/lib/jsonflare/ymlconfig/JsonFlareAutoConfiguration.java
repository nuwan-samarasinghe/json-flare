package com.jsonflare.lib.jsonflare.ymlconfig;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JsonFlareProperties.class)
public class JsonFlareAutoConfiguration {

    private final JsonFlareProperties jsonFlareProperties;

    public JsonFlareAutoConfiguration(JsonFlareProperties jsonFlareProperties) {
        this.jsonFlareProperties = jsonFlareProperties;
    }

    @Bean
    public String loadConfigurations() {
        System.out.println("");
        return "";
    }
}
