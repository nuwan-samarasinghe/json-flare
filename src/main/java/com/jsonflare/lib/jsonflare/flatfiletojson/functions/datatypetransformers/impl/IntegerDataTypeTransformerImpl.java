package com.jsonflare.lib.jsonflare.flatfiletojson.functions.datatypetransformers.impl;

import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareRuntimeException;
import com.jsonflare.lib.jsonflare.flatfiletojson.functions.datatypetransformers.DataTypeTransformer;

/**
 * Author: NUWAN
 * Date: 2024-01-15
 * Description:
 * data type transformer impl for Integer
 */
public class IntegerDataTypeTransformerImpl implements DataTypeTransformer<Integer> {
    @Override
    public Integer transform(String value) {
        if (value.matches("\\d+(\\.\\d+)?")) {
            return Integer.parseInt(value);
        } else {
            throw new JsonFlareRuntimeException(String.format("Given value cannot transform into Integer data type value: %s", value));
        }
    }
}
