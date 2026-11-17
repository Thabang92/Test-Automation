package com.thabang.practice.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.thabang.practice.config.Config;

public class LoginPage {
    private final Page page;
    private final Locator username;
    private final Locator password;
    private final Locator loginButton;
    private final Locator error;

    public LoginPage(Page page) {
        this.page = page;
        this.username = page.getByPlaceholder("Username");
        this.password = page.getByPlaceholder("Password");
        this.loginButton = page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Login"));
        this.error = page.locator("[data-test='error']");
    }

    public LoginPage open() {
        page.navigate(Config.UI_BASE_URL);
        return this;
    }

    public void loginAs(String user, String pass) {
        username.fill(user);
        password.fill(pass);
        loginButton.click();
    }

    public InventoryPage loginAsDefaultUser() {
        loginAs(Config.UI_USER, Config.UI_PASSWORD);
        return new InventoryPage(page);
    }

    public Locator errorMessage() {
        return error;
    }
}
