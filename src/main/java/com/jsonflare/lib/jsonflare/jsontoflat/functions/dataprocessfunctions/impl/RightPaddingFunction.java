package com.jsonflare.lib.jsonflare.jsontoflat.functions.dataprocessfunctions.impl;

import com.jsonflare.lib.jsonflare.jsontoflat.functions.dataprocessfunctions.DataProcessFunction;
import org.apache.commons.lang3.StringUtils;

/**
 * Author: NUWAN
 * Date: 2024-01-20
 * Description:
 * adding implementation for the padding function
 */
public class RightPaddingFunction implements DataProcessFunction {

    @Override
    public String process(String parameter, Integer desiredLength, String originalString) {
        return StringUtils.rightPad(originalString, desiredLength, parameter);
    }
}
