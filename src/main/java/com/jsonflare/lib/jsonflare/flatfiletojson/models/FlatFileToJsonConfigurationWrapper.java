package com.jsonflare.lib.jsonflare.flatfiletojson.models;

import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfiguration;
import lombok.Data;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;

import java.util.List;
import java.util.Map;

/**
 * Author: NUWAN
 * Date: 2024-01-07
 * Description:
 * wrapper object to store configuration data
 */
@Data
public class FlatFileToJsonConfigurationWrapper {
    private String name;
    private FixedLengthTokenizer fixedLengthTokenizer;
    private YmlConfiguration ymlConfigurationMap;
    private List<YmlConfiguration> flatternedList;
}
