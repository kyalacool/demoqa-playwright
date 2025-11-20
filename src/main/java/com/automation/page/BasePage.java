package com.automation.page;

import com.automation.utility.PropertyReader;
import com.microsoft.playwright.Page;

import static com.automation.utility.WebDriverManager.getHomeUrl;

public class BasePage {
    protected final Page page;
    protected final String homeUrl;

    public BasePage(Page page) {
        this.page = page;
        this.homeUrl = getHomeUrl();
    }
}
