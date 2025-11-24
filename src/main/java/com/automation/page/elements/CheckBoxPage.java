package com.automation.page.elements;

import com.automation.base.BasePage;
import com.automation.utility.PropertyReader;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CheckBoxPage extends BasePage {
    @Getter
    private final Locator mainHeader;

    public CheckBoxPage(Page page) {
        super(page);
        this.mainHeader = page.locator("h1.text-center");
    }

    public CheckBoxPage navigateToPage(){
        String fullUrl = baseUrl + PropertyReader.getProperty("url.checkbox");
        page.navigate(fullUrl);
        return this;
    }

    public void clickOnToogle(String name){
        page.getByRole(AriaRole.BUTTON)
                .filter(new Locator.FilterOptions().setHas(page.locator(".rct.title")))
                .filter(new Locator.FilterOptions().setHasText(name))
                .click();
        System.out.println(name+"clicked");
    }
}
