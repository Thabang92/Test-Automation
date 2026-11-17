package com.thabang.practice.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CartPage {
    private final Page page;

    public CartPage(Page page) {
        this.page = page;
    }

    public Locator items() {
        return page.locator("[data-test='inventory-item']");
    }

    public CheckoutPage checkout() {
        page.locator("[data-test='checkout']").click();
        return new CheckoutPage(page);
    }
}
