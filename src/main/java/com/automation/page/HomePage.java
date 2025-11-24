package com.automation.page;

import com.automation.base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HomePage extends BasePage {
    private final Locator mainMenuCards;

    public HomePage(Page page) {
        super(page);
        this.mainMenuCards = page.locator(".category-cards");
    }

    public HomePage navigateToPage(){
        page.navigate(baseUrl);
        return this;
    }

    public Locator getMenuByName(String name) {
        return mainMenuCards.locator(".card-body")
                .filter(new Locator.FilterOptions().setHas(
                        page.locator("h5", new Page.LocatorOptions().setHasText(name))));
    }

    public void clickOnMenu(String name) {
        getMenuByName(name).click();
    }
}
