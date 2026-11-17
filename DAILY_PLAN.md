# 30-Day Plan

Rule: each day = one small task, about 30 to 40 minutes. Start in `DailyPracticeTest`, then move the finished test to its proper home. Commit daily.

## Week 1: Foundations (get fluent with the basics)
- **Day 1**: Run the whole suite. Run it headed with `-DslowMo=400`. Read `BaseUiTest` line by line and write in your log what each annotation does.
- **Day 2**: Locators. On saucedemo, find the same button 4 ways: CSS, `getByRole`, `getByText`, `getByTestId`-style attribute. Write which you prefer and why.
- **Day 3**: Add a login test for `problem_user` (password `secret_sauce`). Open the inventory and observe what is wrong. Log it as a defect write-up.
- **Day 4**: Assertions. Rewrite 3 existing assertions using both Playwright assertions and AssertJ. When is each better? (Playwright's auto-retry vs plain AssertJ.)
- **Day 5**: Add a test: logout via the burger menu returns to the login page.
- **Day 6**: Add a test for cart persistence: add items, log out, log in again, check the cart.
- **Day 7**: Review. Delete nothing, refactor one duplicated step into a Page Object method. Run `mvn test surefire-report:report-only` and read the report.

## Week 2: Framework skills
- **Day 8**: Create `ProductDetailPage`. Add a test that opens a product and checks name, price and description.
- **Day 9**: Create a `LoginSteps`-style helper or a `loggedInPage` fixture so tests stop repeating login.
- **Day 10**: Bypass the UI login: log in once, save `storageState`, reuse it in new contexts. Measure the time saved.
- **Day 11**: Test data. Move test users into a small JSON or CSV file and drive a parameterized test with `@MethodSource`.
- **Day 12**: Waiting. Read about auto-waiting, then write one test that uses `page.waitForResponse` or `waitForURL` correctly. Never use `Thread.sleep`.
- **Day 13**: Make a test fail on purpose. Open the screenshot and the trace. Find the failing step in the trace viewer.
- **Day 14**: Review week. Add `@DisplayName` or clean names, remove duplication, make sure `-Dgroups=smoke` runs in under a minute.

## Week 3: API, network and hybrid testing
- **Day 15**: Add API tests for `GET /booking` (list) and filter by `firstname`.
- **Day 16**: Add negative API tests: missing fields on create, bad token on update, unknown ID.
- **Day 17**: Validate response structure: check all expected fields exist and have correct types (try Gson into a POJO or record).
- **Day 18**: Build a `BookingClient` class so tests call `client.create(...)` instead of raw requests.
- **Day 19**: Network mocking in UI: use `page.route` to fake a failing response and check how the page behaves.
- **Day 20**: Listen to `page.onConsoleMessage` and `onResponse`. Fail a test if there is a console error or a 4xx/5xx during a flow.
- **Day 21**: Review: combine API + UI in one test (create data by API, verify in UI where possible; on these sites, verify by a second API call).

## Week 4: Reporting, CI/CD and polish
- **Day 22**: Retry and flakiness: read how Surefire `rerunFailingTestsCount` works. Add it and note when retries hide real bugs.
- **Day 23**: Run across browsers: `-Dbrowser=firefox`, `-Dbrowser=webkit`. Note any difference.
- **Day 24**: Visual checks: add a screenshot comparison with `assertThat(page).hasScreenshot` or compare locator screenshots; understand baseline updates.
- **Day 25**: Accessibility smoke: check key elements by role and label. Note what fails.
- **Day 26**: Add Allure reporting (adapter + aspectj agent). Generate and read the report.
- **Day 27**: Add a GitHub Actions workflow: checkout, setup-java 17, `mvn test`, upload `target/site` and `target/artifacts`.
- **Day 28**: Parallel execution: enable JUnit parallelism safely (each test already has its own context). Measure the speed-up.
- **Day 29**: Write a one-page test strategy for this app: scope, risks, what is automated, what is not.
- **Day 30**: Retrospective in `LEARNING_LOG.md`. Pick the next project: a banking-style flow (login, transfer, statement) with your own mock or a public demo app.

## After day 30 (keep it daily)
Pick one: add a new site to test, convert a slow test to API setup, learn `@Nested` and custom JUnit extensions, or practise explaining your framework out loud as if in an interview.
