package com.automation;

import com.automation.page.HomePage;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.automation.utils.BrowserManager.getPage;
import static com.automation.utils.PropertyReader.getProperty;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
public class HomePageTest extends BaseTest {

    @DataProvider(name = "menuItems", parallel = true)
    public Object[][] createMenuItems() {
        return new Object[][]{
                {"Elements", baseUrl + getProperty("url.elements")},
                {"Forms", baseUrl + getProperty("url.forms")},
                {"Alerts, Frame & Windows", baseUrl + getProperty("url.alerts")},
                {"Widgets", baseUrl + getProperty("url.widgets")},
                {"Interactions", baseUrl + getProperty("url.interaction")},
                {"Book Store Application", baseUrl + getProperty("url.books")}
        };
    }

    @Test(dataProvider = "menuItems")
    void verifyMainMenuNamesAndRoutes(String name, String url) {
        HomePage homePage = new HomePage(getPage());
        homePage
                .navigateToPage()
                .clickOnMenu(name);
        assertThat(getPage()).hasURL(url);
        log.info("{} menu name and route ({}) are verified", name, url);
    }
}
