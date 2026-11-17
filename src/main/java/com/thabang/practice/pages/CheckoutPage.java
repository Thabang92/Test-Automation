package com.thabang.practice.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CheckoutPage {
    private final Page page;

    public CheckoutPage(Page page) {
        this.page = page;
    }

    public CheckoutPage fillCustomerInfo(String firstName, String lastName, String postalCode) {
        page.locator("[data-test='firstName']").fill(firstName);
        page.locator("[data-test='lastName']").fill(lastName);
        page.locator("[data-test='postalCode']").fill(postalCode);
        return this;
    }

    public CheckoutPage continueToOverview() {
        page.locator("[data-test='continue']").click();
        return this;
    }

    public CheckoutPage finish() {
        page.locator("[data-test='finish']").click();
        return this;
    }

    public Locator confirmationHeader() {
        return page.locator("[data-test='complete-header']");
    }

    public Locator errorMessage() {
        return page.locator("[data-test='error']");
    }
}
