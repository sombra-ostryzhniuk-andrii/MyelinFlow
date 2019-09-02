package com.ifc.populationorderdeterminant.app;

import com.ifc.populationorderdeterminant.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class PropertiesProvider {

    private static Properties properties;
    private static final String PROPERTY_ARRAY_DELIMITER = ",";
    private static final String SCHEMAS = "schemas";
    private static final String SOURCE_SCHEMAS = "source.schemas";
    private static final String EXCLUDE_FUNCTIONS_PROPERTY = "exclude.functions.";

    private static String configFilePath;

    private static Properties getProperties() {
        if (properties == null) {
            synchronized (PropertiesProvider.class) {
                if (properties == null) {
                    if (configFilePath == null) {
                        configFilePath = CommandOptionsProvider.valueOfRequired(CommandOptionsProvider.CONFIG_FILE_PARAMETER);
                    }
                    properties = loadProperties(configFilePath);
                }
            }
        }
        return properties;
    }

    private static Properties loadProperties(final String filePath) {
        try (InputStream input = new FileInputStream(filePath)) {

            Properties properties = new Properties();
            properties.load(input);
            return properties;

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to find the configuration file " + filePath, e);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load configuration properties", e);
        }
    }

    public static String getProperty(String propertyName) {
        String property = getProperties().getProperty(propertyName);
        if (property == null) {
            log.warn("Unable to find property '" + propertyName + "' in the configuration file " + configFilePath);
        }
        return property;
    }

    public static String getRequiredProperty(String propertyName) {
        String property = getProperties().getProperty(propertyName);
        if (property == null) {
            throw new RuntimeException("Unable to find property '" + propertyName + "' in the configuration file " + configFilePath);
        } else if (property.isEmpty()) {
            throw new RuntimeException("Property '" + propertyName + "' is empty. Please, configure the property in the " +
                    "configuration file " + configFilePath);
        }
        return property;
    }

    public static List<String> getPropertyAsList(String propertyName) {
        String property = getProperty(propertyName);
        return StringUtils.isEmpty(property)
                ? Collections.emptyList()
                : Stream.of(property.split(PROPERTY_ARRAY_DELIMITER))
                    .map(StringUtil::validateString)
                    .collect(Collectors.toList());
    }

    public static List<String> getRequiredPropertyAsList(String propertyName) {
        String property = getRequiredProperty(propertyName);
        return Stream.of(property.split(PROPERTY_ARRAY_DELIMITER))
                .map(StringUtil::validateString)
                .collect(Collectors.toList());
    }

    public static List<String> getExcludedFunctions(String schema) {
        return PropertiesProvider.getPropertyAsList(getExcludeFunctionsProperty(schema));
    }

    public static List<String> getSchemas() {
        return PropertiesProvider.getRequiredPropertyAsList(SCHEMAS)
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<String> getSourceSchemas() {
        return PropertiesProvider.getRequiredPropertyAsList(SOURCE_SCHEMAS);
    }

    public static String getExcludeFunctionsProperty(String schema) {
        return EXCLUDE_FUNCTIONS_PROPERTY + schema;
    }
}