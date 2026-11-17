package com.thabang.practice.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

public class InventoryPage {
    private final Page page;

    public InventoryPage(Page page) {
        this.page = page;
    }

    public Locator title() {
        return page.locator("[data-test='title']");
    }

    public Locator items() {
        return page.locator("[data-test='inventory-item']");
    }

    public List<String> itemNames() {
        return page.locator("[data-test='inventory-item-name']").allTextContents();
    }

    public List<Double> itemPrices() {
        return page.locator("[data-test='inventory-item-price']").allTextContents().stream()
                .map(text -> Double.parseDouble(text.replace("$", "")))
                .toList();
    }

    /** slug example: "sauce-labs-backpack" */
    public InventoryPage addToCart(String slug) {
        page.locator("[data-test='add-to-cart-" + slug + "']").click();
        return this;
    }

    public InventoryPage removeFromCart(String slug) {
        page.locator("[data-test='remove-" + slug + "']").click();
        return this;
    }

    /** values: az, za, lohi, hilo */
    public InventoryPage sortBy(String value) {
        page.locator("[data-test='product-sort-container']").selectOption(value);
        return this;
    }

    public Locator cartBadge() {
        return page.locator("[data-test='shopping-cart-badge']");
    }

    public CartPage openCart() {
        page.locator("[data-test='shopping-cart-link']").click();
        return new CartPage(page);
    }
}
