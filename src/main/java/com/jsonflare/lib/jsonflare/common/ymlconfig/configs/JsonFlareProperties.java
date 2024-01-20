/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * loading the yml configuration into java
 */
package com.jsonflare.lib.jsonflare.common.ymlconfig.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * yml configuration location
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "jsonflare")
public class JsonFlareProperties {
    private String jsonConverterYmlLocation;
    private String flatFileConverterYmlLocation;
}

