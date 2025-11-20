package com.automation.base;

import com.automation.page.HomePage;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test
    void verifyMainMenuNamesAndRoutes() {
        HomePage homePage = new HomePage(getPage());
        homePage.verifyAllMainMenuNameAndUrl();
    }
}
