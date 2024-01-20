package com.jsonflare.lib.jsonflare.service;

import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;

/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * interface for conversion
 */
public interface DataFormatConverterService {

    /**
     * converting the given flat file text message into json data as a text
     *
     * @param className yml configuration file selection based on the class-name
     * @param data      flat message that needed to convert into json
     * @return json as a text
     * @throws JsonFlareException any error occurred while converting the data this exception will throw
     */
    String convertFlatFileToJson(String className, String data) throws JsonFlareException;

    /**
     * converting the given json text message into flat file data as a text
     *
     * @param className yml configuration file selection based on the class-name
     * @param data      json message that needed to convert into flat file
     * @return flat file as a text
     * @throws JsonFlareException any error occurred while converting the data this exception will throw
     */
    String convertJsonToFlatFile(String className, String data) throws JsonFlareException;

}
