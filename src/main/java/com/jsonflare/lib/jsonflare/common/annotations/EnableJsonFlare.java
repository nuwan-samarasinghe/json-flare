package com.jsonflare.lib.jsonflare.common.annotations;

import com.jsonflare.lib.jsonflare.common.configs.LibraryAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * Adding enable autoconfiguration annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(LibraryAutoConfiguration.class)
public @interface EnableJsonFlare {
}
