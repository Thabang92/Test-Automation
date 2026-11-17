package com.thabang.practice.core;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import com.thabang.practice.config.Config;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.Map;

public abstract class BaseApiTest {

    private static Playwright playwright;
    protected static APIRequestContext api;

    @BeforeAll
    static void createApiContext() {
        playwright = Playwright.create();
        api = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(Config.API_BASE_URL)
                .setExtraHTTPHeaders(Map.of("Accept", "application/json")));
    }

    @AfterAll
    static void disposeApiContext() {
        api.dispose();
        playwright.close();
    }
}
