package com.jsonflare.lib.jsonflare.jsontoflat.service;

import java.util.Map;

/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * json data to flat file converter service
 */
public interface JsonToFlatFileService {
    String convert(Map<String, Object> stringObjectMap);
}
