package com.jsonflare.lib.jsonflare.ymlconfig;

import com.jsonflare.lib.jsonflare.ymlconfig.config.JsonFlareAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {JsonFlareAutoConfiguration.class})
public class JsonFlareAutoConfigurationTest {
    @Test
    public void testIsYmlFileLocationCorrectlyGettingTheProperty(){

    }
}