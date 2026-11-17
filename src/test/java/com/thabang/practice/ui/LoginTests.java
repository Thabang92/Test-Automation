package com.thabang.practice.ui;

import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.thabang.practice.config.Config;
import com.thabang.practice.core.BaseUiTest;
import com.thabang.practice.pages.InventoryPage;
import com.thabang.practice.pages.LoginPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.regex.Pattern;

@Tag("ui")
class LoginTests extends BaseUiTest {

    @Test
    @Tag("smoke")
    void valid_user_lands_on_products_page() {
        InventoryPage inventory = new LoginPage(page).open().loginAsDefaultUser();

        PlaywrightAssertions.assertThat(page).hasURL(Pattern.compile(".*inventory.html"));
        PlaywrightAssertions.assertThat(inventory.title()).hasText("Products");
    }

    @Test
    void locked_out_user_sees_error() {
        LoginPage login = new LoginPage(page).open();
        login.loginAs("locked_out_user", Config.UI_PASSWORD);

        PlaywrightAssertions.assertThat(login.errorMessage()).containsText("locked out");
    }

    @ParameterizedTest(name = "[{index}] user=''{0}'' pass=''{1}'' -> {2}")
    @CsvSource({
            "standard_user, wrong_password, do not match",
            "not_a_user,    secret_sauce,   do not match",
            "'',            secret_sauce,   Username is required",
            "standard_user, '',             Password is required"
    })
    void invalid_credentials_show_the_right_error(String user, String pass, String expectedText) {
        LoginPage login = new LoginPage(page).open();
        login.loginAs(user, pass);

        PlaywrightAssertions.assertThat(login.errorMessage()).containsText(expectedText);
    }
}
