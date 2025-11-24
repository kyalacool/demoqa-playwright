package com.automation.base;

import com.microsoft.playwright.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.automation.utility.PropertyReader.getProperty;
import static com.automation.utility.WebDriverManager.getHomeUrl;

@Slf4j
public class BaseTest {
    protected static String baseUrl = getHomeUrl();
    private final ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<BrowserContext> browserContextThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    @BeforeTest
    public void openLog() {
        log.info("Browser : {}", getProperty("browser"));
        log.info("Environment : {}", getProperty("env"));
        Paths.get("target/traces").toFile().mkdirs();
    }

    @BeforeMethod
    public void methodSetup() {
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

    @AfterMethod
    public void methodTearDown(ITestResult result) {
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

    @AfterTest
    public void closeLog() {
        log.info("Pages, Drivers and Tests are closed.");
    }

    public Page getPage() {
        return pageThreadLocal.get();
    }
}
