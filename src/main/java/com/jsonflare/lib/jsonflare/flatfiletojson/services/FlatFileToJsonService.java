package com.jsonflare.lib.jsonflare.flatfiletojson.services;

import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;

/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * convert json file to flat-file configuration
 */
public interface FlatFileToJsonService {
    String convert(String className, String data) throws JsonFlareException;
}
