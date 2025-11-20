package com.automation.page;

import com.automation.utility.WebDriverManager;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

@Slf4j
public class HomePage extends BasePage {
    private final List<Map<String, String>> expectedMenuNameAndUrlsListOfMaps =
            List.of(
                    Map.of("Elements", "https://demoqa.com/elements"),
                    Map.of("Forms", "https://demoqa.com/forms"),
                    Map.of("Alerts, Frame & Windows", "https://demoqa.com/alertsWindows"),
                    Map.of("Widgets", "https://demoqa.com/widgets"),
                    Map.of("Interactions", "https://demoqa.com/interaction"),
                    Map.of("Book Store Application", "https://demoqa.com/books")
            );

    public HomePage(Page page) {
        super(page);
        page.navigate(homeUrl);
    }

    public void verifyAllMainMenuNameAndUrl() {
        Locator menus = page.locator(".card-body h5");
        List<String> allMenuNameOnPage = menus.allInnerTexts();
        for (int i = 0; i < allMenuNameOnPage.size(); i++) {
            Assert.assertEquals(allMenuNameOnPage.size(), expectedMenuNameAndUrlsListOfMaps.size());
            Map<String, String> actualMap = expectedMenuNameAndUrlsListOfMaps.get(i);
            String expectedMenuName = actualMap.keySet().stream().findFirst().orElseThrow(IllegalStateException::new);
            String expectedMenuUrl = actualMap.values().stream().findFirst().orElseThrow(IllegalStateException::new);
            Assert.assertEquals(allMenuNameOnPage.get(i), expectedMenuName);
            Locator clickableMenuLocator = page.getByText(allMenuNameOnPage.get(i));
            clickableMenuLocator.click();
            Assert.assertEquals(page.url(), expectedMenuUrl);
            page.goBack();
        }
        log.info("VERIFY - MainMenu names and routes are verified");
    }
}
