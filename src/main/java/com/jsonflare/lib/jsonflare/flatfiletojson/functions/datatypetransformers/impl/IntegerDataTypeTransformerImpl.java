package com.jsonflare.lib.jsonflare.flatfiletojson.functions.datatypetransformers.impl;

import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareRuntimeException;
import com.jsonflare.lib.jsonflare.flatfiletojson.functions.datatypetransformers.DataTypeTransformer;
import lombok.extern.slf4j.Slf4j;

/**
 * Author: NUWAN
 * Date: 2024-01-15
 * Description:
 * data type transformer impl for Integer
 */
@Slf4j
public class IntegerDataTypeTransformerImpl implements DataTypeTransformer<Integer> {
    @Override
    public Integer transform(String value) {
        if (value.matches("\\d+(\\.\\d+)?")) {
            return Integer.parseInt(value);
        } else {
            log.error("Given value cannot transform into Integer data type value: {}", value);
            throw new JsonFlareRuntimeException(String.format("Given value cannot transform into Integer data type value: %s", value));
        }
    }
}
