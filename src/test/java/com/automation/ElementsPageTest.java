package com.automation;

import com.automation.page.ElementsMainPage;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.automation.utils.BrowserManager.getPage;
import static com.automation.utils.PropertyReader.getProperty;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
public class ElementsPageTest extends BaseTest {

    @DataProvider(name = "subPageNameRoute", parallel = true)
    public Object[][] routeDataProvider() {
        return new Object[][]{
                {"Text Box", getProperty("url.textbox")},
                {"Check Box", getProperty("url.checkbox")},
                {"Radio Button", getProperty("url.radiobutton")},
                {"Web Tables", getProperty("url.webtables")},
                {"Buttons", getProperty("url.buttons")},
                {"Links", getProperty("url.links")},
                {"Broken Links - Images", getProperty("url.broken")},
                {"Upload and Download", getProperty("url.upload")},
                {"Dynamic Properties", getProperty("url.dynamic")},
        };
    }

    @Test(dataProvider = "subPageNameRoute")
    void verifyElementsSubPagesNamesAndRoutes(String name, String partialUrl) {
        String fullUrl = baseUrl + partialUrl;
        ElementsMainPage elementsMainPage = new ElementsMainPage(getPage());
        elementsMainPage
                .navigateToPage()
                .clickOnSubMenu(name);
        assertThat(getPage()).hasURL(fullUrl);
        log.info("{} subpage name and route ({}) verified.", name, fullUrl);
    }
}
