package com.automation.page.elements;

import com.automation.base.BasePage;
import com.automation.utility.PropertyReader;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
public class CheckBoxPage extends BasePage {
    @Getter
    private final Locator mainHeader;
    @Getter
    private final Locator textedRows;
    @Getter
    private final Locator checkBoxes;
    @Getter
    private final Locator checkedResult;
    private final Locator toggleTitles;
    private final Locator openAll;
    private final Locator closeAll;

    public CheckBoxPage(Page page) {
        super(page);
        this.mainHeader = page.locator("h1.text-center");
        this.textedRows = page.locator(".rct-text");
        this.toggleTitles = page.locator(".rct-title");
        this.openAll = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Expand all"));
        this.closeAll = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Collapse all"));
        this.checkBoxes = page.locator(".rct-checkbox");
        this.checkedResult = page.locator(".text-success");
    }

    public CheckBoxPage navigateToPage(){
        String fullUrl = baseUrl + PropertyReader.getProperty("url.checkbox");
        page.navigate(fullUrl);
        return this;
    }

    public CheckBoxPage openAllToggle(){
        openAll.click();
        return this;
    }

    public Locator checkBox(String checkboxName){
        return page.locator("label").filter(new Locator.FilterOptions().setHasText(checkboxName))
                .locator(".rct-checkbox");
    }

    public CheckBoxPage verifyToggleTitlesVisibility(String[] expectedList){
        for (String name : expectedList){
            assertThat(toggleTitles.filter(new Locator.FilterOptions().setHasText(name))).isVisible();
        }
        return this;
    }

    public void closeAllToggle(){
        closeAll.click();
    }

    public void clickOnToogle(String name){
        Locator button = page.locator(".rct-title", new Page.LocatorOptions().setHasText(name))
                .locator("../..").getByRole(AriaRole.BUTTON);
        if (button.isVisible()){
            button.click();
        }
    }
}
