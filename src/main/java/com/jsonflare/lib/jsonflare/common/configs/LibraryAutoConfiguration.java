/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * allow the library to scan its own clases
 */
package com.jsonflare.lib.jsonflare.common.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * Adding enable autoconfiguration annotation
 */
@Configuration
@ComponentScan("com.jsonflare.lib")
public class LibraryAutoConfiguration {
    // no additional auto configuration only component scan will be initiate
}
