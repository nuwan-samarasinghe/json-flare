/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * loading the yml configuration into java
 */
package com.jsonflare.lib.jsonflare.common.ymlconfig.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfiguration;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfigurationMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Configuration
@EnableConfigurationProperties(JsonFlareProperties.class)
public class JsonFlareYmlConfiguration {

    private final JsonFlareProperties jsonFlareProperties;

    public JsonFlareYmlConfiguration(JsonFlareProperties jsonFlareProperties) {
        this.jsonFlareProperties = jsonFlareProperties;
    }

    /**
     * create a bean with YmlConfigurationMap and this has all the configurations
     *
     * @return YmlConfigurationMap object
     * @throws JsonFlareException if any error occurred while reading the yml configurations.
     */
    @Bean(name = "ymlConfigurationMap")
    public YmlConfigurationMap loadConfigurations() throws JsonFlareException {
        YmlConfigurationMap ymlConfigurationMap = new YmlConfigurationMap();
        if (jsonFlareProperties.getJsonConverterYmlLocation().equals(jsonFlareProperties.getFlatFileConverterYmlLocation())) {
            log.error("[YML-CONFIG] json to flat file and flat file to json yml configurations cannot be in the same directory");
            throw new JsonFlareException("json to flat file and flat file to json yml configurations cannot be in the same directory");
        }
        loadConfig(jsonFlareProperties.getJsonConverterYmlLocation(), ymlConfigurationMap.getJsonToFlatFileConfigurationMap());
        loadConfig(jsonFlareProperties.getFlatFileConverterYmlLocation(), ymlConfigurationMap.getFlatFileToJsonConfigurationMap());
        return ymlConfigurationMap;
    }

    /**
     * loading the yml configuration into the YmlConfigurationMap java object
     *
     * @param ymlLocation         yml location (resource folder in spring boot)
     * @param ymlConfigurationMap yml configurations as a java object
     * @throws JsonFlareException if any exception occured based on the cenarios this will throw
     */
    private void loadConfig(String ymlLocation, Map<String, YmlConfiguration> ymlConfigurationMap) throws JsonFlareException {
        log.info("[YML-CONFIG] loading configurations for {}", ymlLocation);
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:" + ymlLocation + "/**/*.yml");
            if (resources.length == 0) {
                String msg = String.format("No Yml Configurations are presented in the given location [%s]", ymlLocation);
                log.error("[YML-CONFIG] {}", msg);
                throw new JsonFlareException(msg);
            }
            for (Resource resource : resources) {
                if (resource.exists() && Objects.requireNonNull(resource.getFilename()).endsWith(".yml")) {
                    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                    mapper.findAndRegisterModules();
                    YmlConfiguration ymlConfiguration = mapper.readValue(resource.getFile(), YmlConfiguration.class);
                    System.out.println();
                    if (Objects.isNull(ymlConfiguration.getClassName())) {
                        log.error("Yml Configuration does not have the class-name please add it");
                        throw new JsonFlareException("Yml Configuration does not have the class-name please add it");
                    }
                    log.error("[YML-CONFIG] configuration loaded for {}", ymlConfiguration.getClassName());
                    ymlConfigurationMap.put(ymlConfiguration.getClassName(), ymlConfiguration);
                } else {
                    log.error(String.format("Given resource does not exists [%s]", resource.getFilename()));
                    throw new JsonFlareException(String.format("Given resource does not exists [%s]", resource.getFilename()));
                }
            }
        } catch (Exception ex) {
            log.error("An error occurred while loading the configurations", ex);
            throw new JsonFlareException("An error occurred while loading the configurations", ex);
        }
    }
}
