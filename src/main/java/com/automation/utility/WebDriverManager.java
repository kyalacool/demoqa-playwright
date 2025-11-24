package com.automation.utility;

import lombok.extern.slf4j.Slf4j;

import static com.automation.utility.PropertyReader.getProperty;

@Slf4j
public class WebDriverManager {


    public static String getHomeUrl() {
        String env = getProperty("env").toLowerCase();
        switch (env) {
            case "local" -> {
                return urlType.LOCAL.url;
            }
            default -> throw new IllegalArgumentException("Environment not found." + env);
        }
    }

    public enum urlType {
        LOCAL("https://demoqa.com");

        public final String url;

        urlType(String url) {
            this.url = url;
        }
    }
}
