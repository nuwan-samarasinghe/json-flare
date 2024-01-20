package com.jsonflare.lib.jsonflare.flatfiletojson.configs;

import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfiguration;
import com.jsonflare.lib.jsonflare.common.ymlconfig.models.YmlConfigurationMap;
import com.jsonflare.lib.jsonflare.flatfiletojson.models.FlatFileToJsonConfigurationWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Author: NUWAN
 * Date: 2024-01-07
 * Description:
 * creating the tokenizer configuration for the given yml configuration
 */
@Slf4j
@Configuration
public class FlatFileToJsonConfigurator {

    private final YmlConfigurationMap ymlConfigurationMap;

    public FlatFileToJsonConfigurator(YmlConfigurationMap ymlConfigurationMap) {
        this.ymlConfigurationMap = ymlConfigurationMap;
    }

    @Bean("flatFileToJsonConfigurationWrapperMap")
    public Map<String, FlatFileToJsonConfigurationWrapper> loadConfigurationWrapperMap() {
        Map<String, FlatFileToJsonConfigurationWrapper> configurationWrapperMap = new HashMap<>();
        for (Map.Entry<String, YmlConfiguration> entry : ymlConfigurationMap.getFlatFileToJsonConfigurationMap().entrySet()) {
            String name = entry.getKey();
            YmlConfiguration value = entry.getValue();
            FlatFileToJsonConfigurationWrapper flatFileToJsonConfigurationWrapper = new FlatFileToJsonConfigurationWrapper();
            List<YmlConfiguration> flattenedYmlConfig = new ArrayList<>(value.flattenAndGetObjects().stream().filter(ymlConfiguration -> Objects.isNull(ymlConfiguration.getProperties())).toList());
            flattenedYmlConfig.sort(Comparator.comparingInt(YmlConfiguration::getOrder));
            log.info("Creating the tokenizer for {} with the configs of following {}", name, flattenedYmlConfig);
            flatFileToJsonConfigurationWrapper.setName(name);
            flatFileToJsonConfigurationWrapper.setYmlConfigurationMap(value);
            flatFileToJsonConfigurationWrapper.setFlatternedList(flattenedYmlConfig);
            FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
            String[] columns = new String[flattenedYmlConfig.size()];
            Range[] ranges = new Range[flattenedYmlConfig.size()];
            int counter = 0;
            for (YmlConfiguration ymlConfiguration : flattenedYmlConfig) {
                if (counter == 0) {
                    ranges[counter] = createRange(ymlConfiguration, null);
                } else {
                    ranges[counter] = createRange(ymlConfiguration, ranges[counter - 1]);
                }
                columns[counter] = ymlConfiguration.getName();
                counter++;
            }
            tokenizer.setNames(columns);
            tokenizer.setColumns(ranges);
            flatFileToJsonConfigurationWrapper.setFixedLengthTokenizer(tokenizer);
            flatFileToJsonConfigurationWrapper.setMaxLength(ranges[ranges.length - 1].getMax());
            configurationWrapperMap.put(name, flatFileToJsonConfigurationWrapper);
        }
        log.info("No of configurations created {}", configurationWrapperMap.size());
        return configurationWrapperMap;
    }

    private Range createRange(YmlConfiguration ymlConfiguration, Range previousRange) {
        if (Objects.isNull(previousRange)) {
            return new Range(1, ymlConfiguration.getMaxLength());
        } else {
            int upperBound = previousRange.getMax() + ymlConfiguration.getMaxLength();
            return new Range(previousRange.getMax() + 1, upperBound);
        }
    }


}
