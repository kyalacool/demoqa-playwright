package com.automation.utility;

import com.microsoft.playwright.*;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.automation.utility.PropertyReader.getProperty;

@Slf4j
public class WebDriverManager {
    private final ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<BrowserContext> contextThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    public Page getPage() {
        return pageThreadLocal.get();
    }

    public void setupDriver() {
        PropertyReader.getInstance();
        boolean headless = Boolean.parseBoolean(getProperty("headless"));
        String browserProperty = getProperty("browser");
        Path downloadPath = Paths.get("src/main/java/com/automation/downloads");
        Playwright playwright = Playwright.create();
        playwrightThreadLocal.set(playwright);
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(headless).setDownloadsPath(downloadPath);
        Browser browser =
                switch (browserProperty) {
                    case "firefox" -> playwright.firefox().launch(options);
                    case "webkit" -> playwright.webkit().launch(options);
                    default -> playwright.chromium().launch(options);
                };
        browserThreadLocal.set(browser);
        log.info("SETUP - Browser : {}", browserProperty);
        BrowserContext context = browser.newContext();
        contextThreadLocal.set(context);
        Page page = context.newPage();
        pageThreadLocal.set(page);
    }

    public void tearDownDriver() {
        if (pageThreadLocal.get() != null) {
            pageThreadLocal.get().close();
            log.info("CLOSE - Page is closed.");
        }
        if (contextThreadLocal.get() != null) {
            contextThreadLocal.get().close();
        }
        if (browserThreadLocal.get() != null) {
            browserThreadLocal.get().close();
        }
        if (playwrightThreadLocal.get() != null) {
            playwrightThreadLocal.get().close();
            log.info("CLOSE - Driver is closed.");
            log.info(" --- --- ---");
        }
        pageThreadLocal.remove();
        contextThreadLocal.remove();
        browserThreadLocal.remove();
        playwrightThreadLocal.remove();
    }

    public static String getHomeUrl() {
        String env = getProperty("env").toLowerCase();
        switch (env) {
            case "local" -> {
                log.info("SETUP - Environment : {}", getProperty("env"));
                return urlType.LOCAL.url;
            }
        }
        return null;
    }

    public enum urlType {
        LOCAL("https://demoqa.com");

        public final String url;

        urlType(String url) {
            this.url = url;
        }
    }
}
