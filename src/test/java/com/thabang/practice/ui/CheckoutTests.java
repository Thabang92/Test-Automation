package com.thabang.practice.ui;

import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.thabang.practice.core.BaseUiTest;
import com.thabang.practice.pages.CartPage;
import com.thabang.practice.pages.CheckoutPage;
import com.thabang.practice.pages.InventoryPage;
import com.thabang.practice.pages.LoginPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("ui")
class CheckoutTests extends BaseUiTest {

    @Test
    @Tag("smoke")
    void user_can_buy_a_backpack_end_to_end() {
        InventoryPage inventory = new LoginPage(page).open().loginAsDefaultUser();
        inventory.addToCart("sauce-labs-backpack");

        CartPage cart = inventory.openCart();
        PlaywrightAssertions.assertThat(cart.items()).hasCount(1);

        CheckoutPage checkout = cart.checkout()
                .fillCustomerInfo("Thabang", "Motshoane", "2000")
                .continueToOverview()
                .finish();

        PlaywrightAssertions.assertThat(checkout.confirmationHeader())
                .hasText("Thank you for your order!");
    }

    @Test
    void checkout_requires_first_name() {
        InventoryPage inventory = new LoginPage(page).open().loginAsDefaultUser();
        inventory.addToCart("sauce-labs-backpack");

        CheckoutPage checkout = inventory.openCart().checkout()
                .fillCustomerInfo("", "Motshoane", "2000")
                .continueToOverview();

        PlaywrightAssertions.assertThat(checkout.errorMessage()).containsText("First Name is required");
    }
}
