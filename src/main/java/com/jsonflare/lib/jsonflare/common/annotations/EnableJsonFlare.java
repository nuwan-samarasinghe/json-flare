package com.jsonflare.lib.jsonflare.common.annotations;

import com.jsonflare.lib.jsonflare.common.config.LibraryAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(LibraryAutoConfiguration.class)
public @interface EnableJsonFlare {
}
