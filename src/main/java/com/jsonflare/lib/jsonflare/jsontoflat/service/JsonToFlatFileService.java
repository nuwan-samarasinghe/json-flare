package com.jsonflare.lib.jsonflare.jsontoflat.service;

import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfiguration;

import java.util.List;

/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * json data to flat file converter service
 */
public interface JsonToFlatFileService {

    /**
     * convert the json file content into a flat-file
     *
     * @param ymlConfiguration yml configurations to follow to create flat-file
     * @param data             json content
     * @return flat-file content
     * @throws JsonFlareException data conversion errors
     */
    String convert(YmlConfiguration ymlConfiguration, String data) throws JsonFlareException;
}
