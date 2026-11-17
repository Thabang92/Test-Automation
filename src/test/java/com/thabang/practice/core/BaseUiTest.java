package com.thabang.practice.core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.thabang.practice.config.Config;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * One browser per test class, a fresh isolated context + page per test.
 * On failure you get a full-page screenshot and a Playwright trace in target/artifacts.
 * Open a trace with:  mvn exec:java -e -D"exec.mainClass=com.microsoft.playwright.CLI" -D"exec.args=show-trace target/artifacts/<name>.zip"
 */
public abstract class BaseUiTest {

    private static Playwright playwright;
    private static Browser browser;

    protected BrowserContext context;
    protected Page page;

    private boolean failed;

    @RegisterExtension
    final AfterTestExecutionCallback failureTracker =
            ctx -> failed = ctx.getExecutionException().isPresent();

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        BrowserType type = switch (Config.BROWSER.toLowerCase()) {
            case "firefox" -> playwright.firefox();
            case "webkit" -> playwright.webkit();
            default -> playwright.chromium();
        };
        browser = type.launch(new BrowserType.LaunchOptions()
                .setHeadless(Config.HEADLESS)
                .setSlowMo(Config.SLOW_MO));
    }

    @BeforeEach
    void createContext() {
        failed = false;
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1280, 720));
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        page = context.newPage();
        page.setDefaultTimeout(10_000);
    }

    @AfterEach
    void closeContext(TestInfo info) throws IOException {
        String name = info.getTestClass().map(Class::getSimpleName).orElse("Test")
                + "-" + info.getTestMethod().map(m -> m.getName()).orElse("method");
        if (failed) {
            Path dir = Paths.get("target", "artifacts");
            Files.createDirectories(dir);
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(dir.resolve(name + ".png"))
                    .setFullPage(true));
            context.tracing().stop(new Tracing.StopOptions().setPath(dir.resolve(name + ".zip")));
        } else {
            context.tracing().stop();
        }
        context.close();
    }

    @AfterAll
    static void closeBrowser() {
        browser.close();
        playwright.close();
    }
}
