/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * interface for conversion
 */
package com.jsonflare.lib.jsonflare.service;

import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;

public interface DataFormatConverterService {

    String convertFlatFileToJson(String className) throws JsonFlareException;

    String convertJsonToFlatFile(String className) throws JsonFlareException;

}
