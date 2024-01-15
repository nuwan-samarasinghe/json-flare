package com.jsonflare.lib.jsonflare.flatfiletojson.functions.datatypetransformers;

import com.jsonflare.lib.jsonflare.flatfiletojson.functions.datatypetransformers.impl.IntegerDataTypeTransformerImpl;
import com.jsonflare.lib.jsonflare.flatfiletojson.functions.datatypetransformers.impl.StringDataTypeTransformerImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Author: NUWAN
 * Date: 2024-01-15
 * Description:
 * factory pattern implementation to get data type converter objects
 */
public class DataTypeTransformerFactory {

    public static final String INTEGER = "Integer";
    public static final String STRING = "String";
    private static DataTypeTransformerFactory instance;
    private final Map<String, DataTypeTransformer<?>> transformers;

    private DataTypeTransformerFactory() {
        transformers = new HashMap<>();
        transformers.put(INTEGER, new IntegerDataTypeTransformerImpl());
        transformers.put(STRING, new StringDataTypeTransformerImpl());
    }

    public static DataTypeTransformerFactory getInstance() {
        if (Objects.nonNull(instance)) {
            return instance;
        } else {
            synchronized (DataTypeTransformerFactory.class) {
                if (!Objects.nonNull(instance)) {
                    instance = new DataTypeTransformerFactory();
                }
                return instance;
            }
        }
    }

    public <T> DataTypeTransformer<T> getTransformer(String type) {
        if (transformers.containsKey(type)) {
            //noinspection unchecked
            return (DataTypeTransformer<T>) transformers.get(type);
        } else {
            throw new IllegalArgumentException("Unsupported data type: " + type);
        }
    }

    public <T> void registerTransformer(String type, DataTypeTransformer<T> transformer) {
        transformers.put(type, transformer);
    }

}
