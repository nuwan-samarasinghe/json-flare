package com.jsonflare.lib.jsonflare.flatfiletojson.functions.datatypetransformers.impl;

import com.jsonflare.lib.jsonflare.flatfiletojson.functions.datatypetransformers.DataTypeTransformer;

/**
 * Author: NUWAN
 * Date: 2024-01-15
 * Description:
 * data type transformer impl for Integer
 */
public class StringDataTypeTransformerImpl implements DataTypeTransformer<String> {
    @Override
    public String transform(String value) {
        return value;
    }
}
