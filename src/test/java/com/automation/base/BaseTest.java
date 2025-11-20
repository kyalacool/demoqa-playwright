package com.automation.base;

import com.automation.utility.PropertyReader;
import com.automation.utility.WebDriverManager;
import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import static com.automation.utility.PropertyReader.getProperty;

@Slf4j
public class BaseTest {
    private static final WebDriverManager driverManager = new WebDriverManager();

    public static Page getPage() {
        return driverManager.getPage();
    }
    @BeforeTest
    public void openLog(){
        PropertyReader.getInstance();
        log.info("Browser : {}", getProperty("browser"));
        log.info("Environment : {}", getProperty("env"));
    }

    @BeforeMethod
    public void setup() {
        driverManager.setupDriver();
    }

    @AfterMethod
    public void tearDown() {
        driverManager.tearDownDriver();
    }

    @AfterTest
    public void closeLog(){
        log.info("Pages, Drivers and Tests are closed.");
    }
}
