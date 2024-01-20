package com.jsonflare.lib.jsonflare.flatfiletojson.functions.datatypetransformers;

import com.jsonflare.lib.jsonflare.flatfiletojson.functions.datatypetransformers.impl.IntegerDataTypeTransformerImpl;
import com.jsonflare.lib.jsonflare.flatfiletojson.functions.datatypetransformers.impl.StringDataTypeTransformerImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.jsonflare.lib.jsonflare.common.configs.Constants.INTEGER;
import static com.jsonflare.lib.jsonflare.common.configs.Constants.STRING;

/**
 * Author: NUWAN
 * Date: 2024-01-15
 * Description:
 * factory pattern implementation to get data type converter objects
 */
public class DataTypeTransformerFactory {


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

    /**
     * adding new transformation methods
     *
     * @param type        name of the yml for the data type
     * @param transformer transformer implementation
     * @param <T>         what type of transformer it is
     */
    public <T> void registerTransformer(String type, DataTypeTransformer<T> transformer) {
        transformers.put(type, transformer);
    }

}
