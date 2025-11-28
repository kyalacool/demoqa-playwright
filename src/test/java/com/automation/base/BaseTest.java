package com.automation.base;

import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.nio.file.Paths;

import static com.automation.utils.BrowserManager.*;
import static com.automation.utils.PropertyReader.getProperty;

@Slf4j
public class BaseTest {
    protected static String baseUrl = getHomeUrl();

    @BeforeTest
    public void openLog() {
        log.info("Browser : {}", getProperty("browser"));
        log.info("Environment : {}", getProperty("env"));
        Paths.get("target/traces").toFile().mkdirs();
    }

    @BeforeMethod
    public void methodSetup() {
        setupBrowser();
    }

    @AfterMethod
    public void methodTearDown(ITestResult result) {
        tearDownBrowser(result);
    }

    @AfterTest
    public void closeLog() {
        log.info("Pages, Drivers and Tests are closed.");
    }
}
