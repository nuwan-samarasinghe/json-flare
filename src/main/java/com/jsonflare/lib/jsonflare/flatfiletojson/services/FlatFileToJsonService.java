package com.jsonflare.lib.jsonflare.flatfiletojson.services;

import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;

/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * convert json file to flat-file configuration
 */
public interface FlatFileToJsonService {

    /**
     * convert the flat file content into a json object
     *
     * @param className file that needed to choose from the yml configurations
     * @param data      flat file content
     * @return json object as a string
     * @throws JsonFlareException data conversion errors
     */
    String convert(String className, String data) throws JsonFlareException;
}
