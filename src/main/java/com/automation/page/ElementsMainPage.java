package com.automation.page;

import com.automation.utils.PropertyReader;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ElementsMainPage extends BasePage {
    private final String elementsUrl = baseUrl + PropertyReader.getProperty("url.elements");

    public ElementsMainPage(Page page) {
        super(page);
    }

    public ElementsMainPage navigateToPage() {
        page.navigate(elementsUrl);
        return this;
    }

    public void clickOnSubMenu(String name) {
        Locator mainLocator = page.locator("div.element-list.show");
        Locator listLocator = mainLocator.getByRole(AriaRole.LISTITEM);
        Locator targetLocator = listLocator.locator("span.text")
                .getByText(name, new Locator.GetByTextOptions().setExact(true));
        targetLocator.scrollIntoViewIfNeeded();
        targetLocator.waitFor();
        targetLocator.click();
    }
}
