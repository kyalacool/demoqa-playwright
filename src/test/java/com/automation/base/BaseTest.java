package com.automation.base;

import com.automation.utility.WebDriverManager;
import com.microsoft.playwright.Page;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    private static final WebDriverManager driverManager = new WebDriverManager();

    public static Page getPage() {
        return driverManager.getPage();
    }

    @BeforeMethod
    public void setup() {
        driverManager.setupDriver();
    }

    @AfterMethod
    public void tearDown() {
        driverManager.tearDownDriver();
    }
}
