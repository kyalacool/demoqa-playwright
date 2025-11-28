package com.automation.utils;

import com.microsoft.playwright.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.automation.utils.PropertyReader.getProperty;

@Slf4j
public class BrowserManager {
    private static final ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> browserContextThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    public static void setupBrowser(){
        playwrightThreadLocal.set(Playwright.create());
        Playwright playwright = playwrightThreadLocal.get();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(Boolean.parseBoolean(getProperty("headless")))
                .setDownloadsPath(Paths.get("target/downloads"));
        Browser browser = switch (getProperty("browser")) {
            case "firefox" -> playwright.firefox().launch(options);
            case "webkit" -> playwright.webkit().launch(options);
            default -> playwright.chromium().launch(options);
        };
        browserThreadLocal.set(browser);
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                .setViewportSize(Integer.parseInt(getProperty("browser.width")),
                        Integer.parseInt(getProperty("browser.length")));
        BrowserContext context = browser.newContext(contextOptions);
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        Page page = context.newPage();
        browserContextThreadLocal.set(context);
        pageThreadLocal.set(page);
    }

    public static void tearDownBrowser(ITestResult result){
        BrowserContext context = browserContextThreadLocal.get();
        Page page = pageThreadLocal.get();
        Playwright playwright = playwrightThreadLocal.get();
        Browser browser = browserThreadLocal.get();

        if (context != null) {
            if (result.getStatus() == ITestResult.FAILURE) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
                String timeStamp = LocalDateTime.now().format(formatter);
                String traceName = "target/traces/" + result.getName() + "_" + timeStamp + ".zip";
                context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get(traceName)));
                log.info("Trace saved to : {}", traceName);
            } else {
                context.tracing().stop();
            }
            context.close();
            browserContextThreadLocal.remove();
        }

        if (page != null) {
            page.close();
            pageThreadLocal.remove();
        }

        if (browser != null) {
            browser.close();
        }

        if (playwright != null) {
            playwright.close();
        }
    }

    public static Page getPage() {
        return pageThreadLocal.get();
    }

    public static String getHomeUrl() {
        String env = getProperty("env").toLowerCase();
        switch (env) {
            case "local" -> {
                return urlType.LOCAL.url;
            }
            default -> throw new IllegalArgumentException("Environment not found." + env);
        }
    }

    public enum urlType {
        LOCAL("https://demoqa.com");

        public final String url;

        urlType(String url) {
            this.url = url;
        }
    }
}
