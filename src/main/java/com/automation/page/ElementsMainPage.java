package com.automation.page;

import com.automation.utility.PropertyReader;
import com.automation.utility.WebDriverManager;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

@Slf4j
public class ElementsMainPage extends BasePage {
    protected final String textBoxUrl;
    private final List<Map<String,String>> expectedSubMenuNamesAndRoutes;

    public ElementsMainPage(Page page) {
        super(page);
        PropertyReader.getInstance();
        String mainRoute = homeUrl + PropertyReader.getProperty("url.elements");
        this.textBoxUrl = homeUrl+PropertyReader.getProperty("url.textbox");
        this.expectedSubMenuNamesAndRoutes = List.of(
                Map.of("Text Box", textBoxUrl),
                Map.of("Check Box", homeUrl+"/checkbox"),
                Map.of("Radio Button", homeUrl+"/radio-button"),
                Map.of("Web Tables", homeUrl+"/webtables"),
                Map.of("Buttons", homeUrl+"/buttons"),
                Map.of("Links", homeUrl+"/links"),
                Map.of("Broken Links - Images", homeUrl+"/broken"),
                Map.of("Upload and Download", homeUrl+"/upload-download"),
                Map.of("Dynamic Properties", homeUrl+"/dynamic-properties")
        );
        page.navigate(mainRoute);
    }

    public void printAllElementsSubPage() {
        Locator elementsMainMenu = page.locator(".element-group", new Page.LocatorOptions().setHasText("Elements"));
        List<String> actualSubPagesNames = elementsMainMenu.locator(".menu-list li span").allInnerTexts();
        for (int i = 0; i < actualSubPagesNames.size(); i++) {
            String expectedSubPageName = expectedSubMenuNamesAndRoutes.get(i).keySet().stream().findFirst()
                    .orElseThrow(IllegalStateException::new);
            String actualSubPageName = actualSubPagesNames.get(i);
            Assert.assertEquals(actualSubPageName, expectedSubPageName);
            String expextedSubPageRoute = expectedSubMenuNamesAndRoutes.get(i).values().stream().findFirst()
                    .orElseThrow(IllegalStateException::new);
            page.getByText(actualSubPageName, new Page.GetByTextOptions().setExact(true)).click();
            String actualSubPageRoute = page.url();
            Assert.assertEquals(actualSubPageRoute, expextedSubPageRoute);
            page.goBack();
        }
        log.info("Elements Menu subpages names and routes are verified");
    }
}
