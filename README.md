# Playwright + Java Practice Project

A small but real automation framework you run and extend **every day**.
Stack: Java 17, Maven, Playwright for Java, JUnit 5, AssertJ, Surefire HTML reports.

Practice targets (public, safe to hammer):
- UI: https://www.saucedemo.com (shop: login, sort, cart, checkout)
- API: https://restful-booker.herokuapp.com (booking CRUD)

## Setup (once)
1. Install JDK 17+ and Maven (`java -version`, `mvn -version`).
2. Open the folder in IntelliJ IDEA (File > Open > pom.xml > Open as Project).
3. First run downloads browsers automatically (needs internet, ~300 MB):
   `mvn test -Dtest=DailyPracticeTest`

## Daily commands
| Goal | Command |
|---|---|
| Run everything | `mvn test` |
| Smoke only | `mvn test -Dgroups=smoke` |
| UI only / API only | `mvn test -Dgroups=ui` / `mvn test -Dgroups=api` |
| One class | `mvn test -Dtest=LoginTests` |
| One method | `mvn test -Dtest=LoginTests#locked_out_user_sees_error` |
| Watch the browser | `mvn test -Dheadless=false -DslowMo=400` |
| Other browser | `mvn test -Dbrowser=firefox` (or `webkit`) |
| HTML report | `mvn test surefire-report:report-only` then open `target/site/surefire-report.html` |

## When a test fails
`target/artifacts/` gets a full-page screenshot and a Playwright trace (`.zip`) per failed test.
Open a trace (timeline, DOM snapshots, network, console):
`mvn exec:java -e -D"exec.mainClass=com.microsoft.playwright.CLI" -D"exec.args=show-trace target/artifacts/<name>.zip"`
(If `exec:java` is not available offline, run `mvn dependency:resolve` once with internet.)

## Layout
```
src/main/java/.../config/Config.java      settings (URLs, browser, headless, slowMo)
src/main/java/.../pages/                  Page Objects (Login, Inventory, Cart, Checkout)
src/test/java/.../core/                   BaseUiTest (browser/context/trace) + BaseApiTest
src/test/java/.../ui/                     UI tests
src/test/java/.../api/                    API tests
src/test/java/.../practice/               YOUR daily scratchpad
DAILY_PLAN.md                             30-day plan, one small task per day
LEARNING_LOG.md                           your notes, one line per day
```

## The 40-minute daily routine
1. **5 min**: `mvn test`. Fix anything red (or read why it's red).
2. **25 min**: do today's task from `DAILY_PLAN.md` in `DailyPracticeTest`.
3. **5 min**: move the finished test into `ui/` or `api/`, commit.
4. **5 min**: add one line to `LEARNING_LOG.md`: what you learned, what confused you.
