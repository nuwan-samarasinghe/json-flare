package com.jsonflare.lib.jsonflare.ymlconfig.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "jsonflare")
public class JsonFlareProperties {
    private String jsonConverterYmlLocation;
    private String flatFileConverterYmlLocation;
}

