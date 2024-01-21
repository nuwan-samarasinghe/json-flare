package com.jsonflare.lib.jsonflare.jsontoflat.functions.dataprocessfunctions;

import com.jsonflare.lib.jsonflare.common.exceptions.JsonFlareException;
import com.jsonflare.lib.jsonflare.jsontoflat.functions.dataprocessfunctions.impl.LeftPaddingFunction;
import com.jsonflare.lib.jsonflare.jsontoflat.functions.dataprocessfunctions.impl.MultiplyFunction;
import com.jsonflare.lib.jsonflare.jsontoflat.functions.dataprocessfunctions.impl.RightPaddingFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: NUWAN
 * Date: 2024-01-20
 * Description:
 * getting each object to do data processing
 */
public class DataProcessFunctionFactory {

    private static DataProcessFunctionFactory instance;

    private final Map<String, DataProcessFunction> functionMap = new HashMap<>();

    private DataProcessFunctionFactory() {
        functionMap.put("LeftPadding".toLowerCase(), new LeftPaddingFunction());
        functionMap.put("RightPadding".toLowerCase(), new RightPaddingFunction());
        functionMap.put("MultipliedBy".toLowerCase(), new MultiplyFunction());
    }

    public static DataProcessFunctionFactory getInstance() {
        if (Objects.nonNull(instance)) {
            return instance;
        } else {
            synchronized (DataProcessFunctionFactory.class) {
                if (Objects.isNull(instance)) {
                    instance = new DataProcessFunctionFactory();
                }
                return instance;
            }
        }
    }

    public DataProcessFunction getDataProcessFunction(String function) {
        return functionMap.get(function.toLowerCase());

    }

    public void addDataProcessFunction(String functionName, DataProcessFunction function) {
        functionMap.put(functionName.toLowerCase(), function);
    }

}
