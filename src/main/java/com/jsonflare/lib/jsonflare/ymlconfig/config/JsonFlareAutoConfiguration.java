package com.jsonflare.lib.jsonflare.ymlconfig.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;
import com.jsonflare.lib.jsonflare.ymlconfig.models.YmlConfigurationMap;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.Map;
import java.util.Objects;

@Configuration
@EnableConfigurationProperties(JsonFlareProperties.class)
public class JsonFlareAutoConfiguration {

    private final JsonFlareProperties jsonFlareProperties;

    public JsonFlareAutoConfiguration(JsonFlareProperties jsonFlareProperties) {
        this.jsonFlareProperties = jsonFlareProperties;
    }

    @Bean(name = "ymlConfigurationMap")
    public YmlConfigurationMap loadConfigurations() throws JsonFlareException {
        YmlConfigurationMap ymlConfigurationMap = new YmlConfigurationMap();
        if (jsonFlareProperties.getJsonConverterYmlLocation().equals(jsonFlareProperties.getFlatFileConverterYmlLocation())) {
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
    private void loadConfig(String ymlLocation, Map<String, Map<String, Object>> ymlConfigurationMap) throws JsonFlareException {
        try {
            for (Resource resource : new PathMatchingResourcePatternResolver().getResources("classpath*:" + ymlLocation + "/**/*.yml")) {
                if (resource.exists() && Objects.requireNonNull(resource.getFilename()).endsWith(".yml")) {
                    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                    mapper.findAndRegisterModules();
                    Map<String, Object> configMap = mapper.readValue(resource.getFile(), new TypeReference<>() {
                    });
                    if (Objects.isNull(configMap.get("class-name"))) {
                        throw new JsonFlareException("Yml Configuration does not have the class-name please add it");
                    }
                    ymlConfigurationMap.put(configMap.get("class-name").toString(), configMap);
                } else {
                    throw new JsonFlareException(String.format("Given resource does not exists [%s]", resource.getFilename()));
                }
            }
        } catch (Exception ex) {
            throw new JsonFlareException("An error occurred while loading the configurations", ex);
        }
    }
}
