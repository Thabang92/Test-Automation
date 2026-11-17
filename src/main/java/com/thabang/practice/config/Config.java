package com.thabang.practice.config;

/**
 * Central place for settings. Override any of them from the command line, e.g.
 *   mvn test -Dbrowser=firefox -Dheadless=false -DslowMo=300
 */
public final class Config {
    private Config() {}

    public static final String UI_BASE_URL  = prop("ui.baseUrl", "https://www.saucedemo.com");
    public static final String API_BASE_URL = prop("api.baseUrl", "https://restful-booker.herokuapp.com");
    public static final String UI_USER      = prop("ui.user", "standard_user");
    public static final String UI_PASSWORD  = prop("ui.password", "secret_sauce");

    public static final String BROWSER      = prop("browser", "chromium"); // chromium | firefox | webkit
    public static final boolean HEADLESS    = Boolean.parseBoolean(prop("headless", "true"));
    public static final double SLOW_MO      = Double.parseDouble(prop("slowMo", "0"));

    private static String prop(String key, String defaultValue) {
        return System.getProperty(key, defaultValue);
    }
}
