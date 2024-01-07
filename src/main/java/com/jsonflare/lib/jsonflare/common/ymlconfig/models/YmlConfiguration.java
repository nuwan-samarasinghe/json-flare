package com.jsonflare.lib.jsonflare.common.ymlconfig.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Author: NUWAN
 * Date: 2024-01-07
 * Description:
 * set of configuration that needed for the conversion
 */

@Data
public class YmlConfiguration {
    private String name;
    private String description;
    private String dataType;
    private String dataConverter;
    private Integer maxLength;
    private String className;
    private Integer order;
    private List<YmlConfiguration> properties;

    // Method to flatten and retrieve a list of MyObject instances
    public List<YmlConfiguration> flattenAndGetObjects() {
        List<YmlConfiguration> objectList = new ArrayList<>();
        objectList.add(this); // Add the current object
        if (properties != null) {
            for (YmlConfiguration child : properties) {
                objectList.addAll(child.flattenAndGetObjects()); // Recursively collect objects
            }
        }
        return objectList;
    }
}
