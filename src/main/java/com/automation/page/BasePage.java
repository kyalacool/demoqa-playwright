package com.automation.page;

import com.microsoft.playwright.Page;

import static com.automation.utils.BrowserManager.getHomeUrl;

public class BasePage {
    protected final Page page;
    protected final String baseUrl;

    public BasePage(Page page) {
        this.page = page;
        this.baseUrl = getHomeUrl();
    }
}
