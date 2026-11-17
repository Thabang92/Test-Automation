package com.thabang.practice.ui;

import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.thabang.practice.core.BaseUiTest;
import com.thabang.practice.pages.InventoryPage;
import com.thabang.practice.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("ui")
class InventoryTests extends BaseUiTest {

    private InventoryPage inventory;

    @BeforeEach
    void login() {
        inventory = new LoginPage(page).open().loginAsDefaultUser();
    }

    @Test
    @Tag("smoke")
    void shows_six_products() {
        PlaywrightAssertions.assertThat(inventory.items()).hasCount(6);
    }

    @Test
    void sort_price_low_to_high() {
        inventory.sortBy("lohi");

        List<Double> prices = inventory.itemPrices();
        List<Double> sorted = new ArrayList<>(prices);
        Collections.sort(sorted);
        assertThat(prices).isEqualTo(sorted);
    }

    @Test
    void sort_name_z_to_a() {
        inventory.sortBy("za");

        List<String> names = inventory.itemNames();
        List<String> sorted = new ArrayList<>(names);
        sorted.sort(Collections.reverseOrder());
        assertThat(names).isEqualTo(sorted);
    }

    @Test
    void cart_badge_tracks_added_and_removed_items() {
        inventory.addToCart("sauce-labs-backpack").addToCart("sauce-labs-bike-light");
        PlaywrightAssertions.assertThat(inventory.cartBadge()).hasText("2");

        inventory.removeFromCart("sauce-labs-bike-light");
        PlaywrightAssertions.assertThat(inventory.cartBadge()).hasText("1");
    }
}
