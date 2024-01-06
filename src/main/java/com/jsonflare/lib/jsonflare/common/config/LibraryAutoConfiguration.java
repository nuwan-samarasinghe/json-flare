package com.jsonflare.lib.jsonflare.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.jsonflare.lib")
public class LibraryAutoConfiguration {
    // no additional auto configuration only component scan will be initiate
}
