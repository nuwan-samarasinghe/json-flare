package com.jsonflare.lib.jsonflare.flatfiletojson.functions.datatypetransformers;

/**
 * Author: NUWAN
 * Date: 2024-01-15
 * Description:
 * abstract class for the data type transformer
 */
public interface DataTypeTransformer<T> {
    /**
     * converting the object from String into a T type object
     *
     * @param value string value that going to convert
     * @return T type Object that returns
     */
    T transform(String value);
}
