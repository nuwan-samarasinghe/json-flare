package com.jsonflare.lib.jsonflare.common;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Objects;

/**
 * Author: NUWAN
 * Date: 2024-01-20
 * Description:
 * load json files
 */
public class JsonLoader {

    private static JsonLoader loader;

    private JsonLoader() {
        // no content
    }

    public static JsonLoader getInstance() {
        if (Objects.nonNull(loader)) {
            return loader;
        } else {
            synchronized (JsonLoader.class) {
                if (!Objects.nonNull(loader)) {
                    loader = new JsonLoader();
                }
                return loader;
            }
        }
    }

    public String getJsonFile(String fileName) throws IOException {
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:json/" + fileName);
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        mapper.findAndRegisterModules();
        return mapper.readValue(resources[0].getFile(), JsonNode.class).toPrettyString();
    }
}
