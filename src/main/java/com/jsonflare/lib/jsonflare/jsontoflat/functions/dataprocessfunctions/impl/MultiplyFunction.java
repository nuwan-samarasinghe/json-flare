package com.jsonflare.lib.jsonflare.jsontoflat.functions.dataprocessfunctions.impl;

import com.jsonflare.lib.jsonflare.jsontoflat.functions.dataprocessfunctions.DataProcessFunction;

/**
 * Author: NUWAN
 * Date: 2024-01-21
 * Description:
 * implementation for the multiplication
 */
public class MultiplyFunction implements DataProcessFunction {


    @Override
    public String process(String parameter, Integer desiredLength, String originalString) {
        return String.valueOf(Double.parseDouble(originalString) * Double.parseDouble(parameter));
    }
}
