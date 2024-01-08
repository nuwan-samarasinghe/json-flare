package com.jsonflare.lib.jsonflare.flatfiletojson.service;

import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;

import java.util.Map;

/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * convert json file to flat-file configuration
 */
public interface FlatFileToJsonService {
    String convert(String className, String data) throws JsonFlareException;
}
