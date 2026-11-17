package com.thabang.practice.practice;

import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.thabang.practice.core.BaseUiTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * YOUR SCRATCHPAD. Each day's exercise (see DAILY_PLAN.md) starts here.
 * Run just this class:   mvn test -Dtest=DailyPracticeTest -Dheadless=false -DslowMo=400
 * When a test feels solid, move it into the ui/ or api/ package, then clear this file for tomorrow.
 */
@Tag("practice")
class DailyPracticeTest extends BaseUiTest {

    @Test
    void todays_exercise() {
        page.navigate("https://playwright.dev/");

        // TODO (Day 1): assert the page title contains "Playwright"
        PlaywrightAssertions.assertThat(page).hasTitle(java.util.regex.Pattern.compile("Playwright"));
    }
}
