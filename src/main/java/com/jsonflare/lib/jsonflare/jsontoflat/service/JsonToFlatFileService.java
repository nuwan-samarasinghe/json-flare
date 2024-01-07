package com.jsonflare.lib.jsonflare.jsontoflat.service;

import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfiguration;

import java.util.Map;

/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * json data to flat file converter service
 */
public interface JsonToFlatFileService {
    String convert(YmlConfiguration ymlConfiguration, String data);
}
