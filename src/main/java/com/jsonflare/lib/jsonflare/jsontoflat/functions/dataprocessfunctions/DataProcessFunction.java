package com.jsonflare.lib.jsonflare.jsontoflat.functions.dataprocessfunctions;

/**
 * Author: NUWAN
 * Date: 2024-01-20
 * Description:
 * data processing before creating the flat-file
 */
public interface DataProcessFunction {

    String process(String parameter, Integer desiredLength, String originalString);

}
