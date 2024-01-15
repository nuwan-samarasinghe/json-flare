package com.jsonflare.lib.jsonflare.flatfiletojson.functions.datatypetransformers;

/**
 * Author: NUWAN
 * Date: 2024-01-15
 * Description:
 * abstract class for the data type transformer
 */
public interface DataTypeTransformer<T> {
    T transform(String value);
}
