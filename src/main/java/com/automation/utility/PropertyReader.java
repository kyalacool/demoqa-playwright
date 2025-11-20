package com.automation.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    private static PropertyReader instance;
    private static final Properties properties = new Properties();

    public PropertyReader() {
    }

    public synchronized static PropertyReader getInstance() {
        if (instance == null) {
            instance = new PropertyReader();
        }
        return instance;
    }

    public static String getProperty(String key) {
        FileInputStream file;
        try {
            file = new FileInputStream("config.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            properties.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String result = properties.getProperty(key);
        return System.getProperty(key, result);
    }
}
