package com.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream file = new FileInputStream("config.properties")) {
            properties.load(file);
        } catch (IOException e) {
            throw new RuntimeException("'config.properties' file not found", e);
        }
    }

    private PropertyReader() {
    }

    public static String getProperty(String key) {
        String result = properties.getProperty(key);
        return System.getProperty(key, result);
    }
}
