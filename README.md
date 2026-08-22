# Selenium Automation Framework

A scalable Java Selenium automation framework built with **Selenium WebDriver, TestNG, Maven, WebDriverManager, Extent Reports, and Allure Reports**.

The framework is designed for maintainable UI automation, reusable Page Objects, centralized WebDriver management, detailed execution logs, screenshots, reporting, and CI execution through GitHub Actions.

---

## 🚀 Tech Stack

| Technology | Purpose |
|---|---|
| Java | Programming language |
| Selenium WebDriver | Browser automation |
| TestNG | Test execution and assertions |
| Maven | Build and dependency management |
| WebDriverManager | WebDriver binary management |
| Extent Reports | HTML execution report |
| Allure Reports | Interactive test reporting |
| Apache POI | Excel test-data handling |
| Commons IO | File and utility operations |
| GitHub Actions | CI/CD execution |

---

## 📁 Project Structure

```text
SeleniumAutomationFramework
│
├── pom.xml
├── testng.xml
├── config.properties
├── README.md
│
├── Reports
│   ├── ExtentReport.html
│   └── allure-results
│
├── src
│   ├── main
│   │   └── java
│   │       ├── pages
│   │       │   └── Homepage.java
│   │       │
│   │       └── utilities
│   │           ├── BaseClass.java
│   │           ├── DriverFactory.java
│   │           ├── ExtentManager.java
│   │           ├── TestNGListener.java
│   │           └── ...
│   │
│   └── test
│       ├── java
│       │   └── tests
│       │       ├── LoginTest.java
│       │       └── ...
│       │
│       └── resources
│           └── allure.properties
│
└── .github
    └── workflows
        └── maven.yml
```

> Adjust the package structure if your local project differs.

---

# 🛠️ Prerequisites

Install the following before running the project:

- Java JDK 17 or later
- Maven 3.8+
- Git
- Chrome or Microsoft Edge
- Allure Commandline
- IDE such as Eclipse or IntelliJ IDEA

Verify Java:

```bash
java -version
```

Verify Maven:

```bash
mvn -version
```

Verify Git:

```bash
git --version
```

Verify Allure:

```bash
allure --version
```

---

# ⚙️ Configuration

The framework uses `config.properties` for environment configuration.

Example:

```properties
Browser=chrome
URL=https://your-application-url.com
Headless=false
```

## Configuration Override

Browser and headless mode can also be supplied through Maven system properties.

Run Chrome:

```bash
mvn clean test -Dbrowser=chrome
```

Run headless Chrome:

```bash
mvn clean test -Dbrowser=chrome -Dheadless=true
```

This is especially useful for GitHub Actions.

---

# 🌐 WebDriver Management

`DriverFactory` is responsible for creating and managing WebDriver instances.

The framework uses `ThreadLocal<WebDriver>` so that the framework can be extended for parallel TestNG execution.

Conceptually:

```text
Test
  ↓
BaseClass
  ↓
DriverFactory
  ↓
ThreadLocal<WebDriver>
  ↓
Page Object
  ↓
Selenium WebDriver
```

The test classes should not directly create browser instances.

Avoid:

```java
driver = new ChromeDriver();
```

Instead use:

```java
DriverFactory.initializeDriver(browser, headless);
driver = DriverFactory.getDriver();
```

---

# 🧱 Page Object Model

The framework follows the Page Object Model (POM).

Example:

```java
public class Homepage {

    private WebDriver driver;

    public Homepage(WebDriver driver) {
        this.driver = driver;
    }

    public void verifyHomePage() {
        Assert.assertTrue(
            driver.getTitle().contains("Home")
        );
    }
}
```

Tests should interact with page objects rather than placing Selenium locators and actions directly inside test methods.

Example:

```java
@Test
public void verifyHomePage() {

    Homepage homepage = new Homepage(driver);

    homepage.verifyHomePage();
}
```

---

# 🧪 TestNG Execution

Tests are configured through `testng.xml`.

Example:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">

<suite name="Selenium Automation Suite">

    <listeners>
        <listener class-name="utilities.TestNGListener"/>
    </listeners>

    <test name="Regression Tests">

        <classes>
            <class name="tests.LoginTest"/>
            <class name="tests.HomeTest"/>
        </classes>

    </test>

</suite>
```

---

# 📊 Extent Reports

Extent Reports are generated in:

```text
Reports/ExtentReport.html
```

The framework cleans the previous `Reports` directory and creates a fresh report for each execution.

Execution flow:

```text
TestNG starts
    ↓
ExtentManager initializes
    ↓
Old Reports directory is cleaned
    ↓
New Reports directory is created
    ↓
ExtentReport.html is created
    ↓
Tests execute
    ↓
Results are logged
    ↓
Screenshots are attached
    ↓
Extent report is flushed
```

Open the report after execution:

```text
Reports/ExtentReport.html
```

---

# 📈 Allure Reports

Allure results are generated under:

```text
Reports/allure-results
```

The Allure TestNG adapter collects test execution information automatically.

Run the tests:

```bash
mvn clean test
```

Then verify:

```bash
find Reports/allure-results -type f
```

On Windows Git Bash, you can use:

```bash
ls -la Reports/allure-results
```

Generate and open the Allure report:

```bash
allure serve Reports/allure-results
```

Allure starts a local web server and opens the report in the browser.

---

# 📸 Screenshots

Screenshots should be captured automatically by the listener when a test fails.

Recommended flow:

```text
Test Failure
    ↓
TestNGListener
    ↓
DriverFactory.getDriver()
    ↓
Take Screenshot
    ↓
Attach Screenshot
    ├── Extent Report
    └── Allure Report
```

Screenshots should be stored under the `Reports` directory or another execution-artifact directory.

---

# 📝 Logging

The listener is responsible for centralized test lifecycle logging.

Recommended events:

```text
Test Started
Test Passed
Test Failed
Test Skipped
Test Failed With Timeout
Suite Started
Suite Finished
```

For failures, the framework should capture:

- Test name
- Class name
- Exception message
- Stack trace
- Screenshot
- Browser information
- Current URL
- Execution timestamp

---

# ▶️ Running the Tests

## Run all tests

```bash
mvn clean test
```

## Run using Chrome

```bash
mvn clean test -Dbrowser=chrome
```

## Run headless

```bash
mvn clean test -Dbrowser=chrome -Dheadless=true
```

## Run the TestNG suite

```bash
mvn clean test
```

The Maven Surefire plugin should be configured to execute:

```text
testng.xml
```

---

# 🧹 Clean Build

To remove previous Maven build artifacts:

```bash
mvn clean
```

To clean and execute tests:

```bash
mvn clean test
```

---

# 🔄 Git Workflow

Recommended workflow:

```text
Create / Clone Repository
        ↓
Create Feature Branch
        ↓
Implement Automation
        ↓
Run Tests Locally
        ↓
Review Extent + Allure Reports
        ↓
git add .
        ↓
git commit
        ↓
git push
        ↓
Create Pull Request
        ↓
GitHub Actions
        ↓
Automated Test Execution
        ↓
Review CI Results
        ↓
Approve & Merge
```

Example:

```bash
git checkout -b feature/login-automation

git add .

git commit -m "Add login automation tests"

git push -u origin feature/login-automation
```

---

# 🤖 GitHub Actions

The framework can be executed automatically using GitHub Actions.

Recommended CI flow:

```text
Push / Pull Request
        ↓
GitHub Actions
        ↓
Checkout Repository
        ↓
Setup Java
        ↓
Setup Maven
        ↓
mvn clean test
        ↓
Generate Reports
        ↓
Upload Extent Report
        ↓
Upload Allure Results
```

Example workflow:

```yaml
name: Selenium Automation Tests

on:
  push:
    branches:
      - main
      - develop

  pull_request:
    branches:
      - main

jobs:

  test:

    runs-on: ubuntu-latest

    steps:

      - name: Checkout Repository
        uses: actions/checkout@v4

      - name: Setup Java
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '17'
          cache: maven

      - name: Run Tests
        run: mvn clean test -Dheadless=true

      - name: Upload Extent Report
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: extent-report
          path: Reports/ExtentReport.html

      - name: Upload Allure Results
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: allure-results
          path: Reports/allure-results
```

> Update the workflow according to your actual repository structure and branch strategy.

---

# 🧪 TDD-Oriented Automation Approach

For test-driven development, use the following cycle:

```text
RED
 ↓
Write a failing test
 ↓
GREEN
 ↓
Implement the minimum required functionality
 ↓
REFACTOR
 ↓
Improve the implementation
```

For automation development:

```text
Requirement
    ↓
Test Scenario
    ↓
Test Case
    ↓
Write Test
    ↓
Implement Page Object / Utility
    ↓
Execute
    ↓
Analyze Report
    ↓
Refactor
```

Keep tests focused on behavior and keep reusable Selenium logic inside Page Objects and utilities.

---

# 📦 Maven Dependencies

The project currently uses the following major dependencies:

```text
Selenium Java       4.37.0
TestNG              7.12.0
WebDriverManager    6.3.3
ExtentReports       5.1.2
Allure TestNG       2.29.1
Apache POI          5.5.1
Commons IO          2.21.0
```

Dependencies are managed through `pom.xml`.

---

# 🔐 Best Practices

- Do not hard-code credentials in test classes.
- Do not commit passwords, API keys, tokens, or secrets.
- Use environment variables or GitHub Secrets for sensitive values.
- Keep locators inside Page Objects.
- Avoid `Thread.sleep()`; prefer explicit waits.
- Keep test methods small and behavior-focused.
- Use reusable utility methods.
- Use `DriverFactory` instead of directly creating WebDriver instances.
- Use listeners for centralized reporting and screenshots.
- Keep reports out of source-controlled production code when appropriate.
- Run tests in headless mode in CI.
- Keep test data separate from test logic.
- Use meaningful test and commit names.

---

# 🐛 Troubleshooting

## Allure says `Reports/allure-results does not exist`

Verify that the directory exists:

```bash
ls -la Reports
```

Then:

```bash
ls -la Reports/allure-results
```

If it does not exist, verify that:

```text
src/test/resources/allure.properties
```

contains:

```properties
allure.results.directory=Reports/allure-results
```

Then run:

```bash
mvn clean test
```

---

## TestNG classes cannot be found

If classes under `src/main/java` use TestNG, make sure TestNG is available during compile.

For the current project structure:

```xml
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.12.0</version>
</dependency>
```

Do not use:

```xml
<scope>test</scope>
```

while TestNG-dependent classes remain under `src/main/java`.

---

## `Listeners` annotation conflict

Avoid naming your custom listener:

```text
Listeners.java
```

because TestNG has:

```java
org.testng.annotations.Listeners
```

Prefer:

```text
TestNGListener.java
```

and register it in `testng.xml`:

```xml
<listeners>
    <listener class-name="utilities.TestNGListener"/>
</listeners>
```

---

# 📌 Future Enhancements

Potential improvements for the framework:

- Parallel cross-browser execution
- Selenium Grid integration
- Docker execution
- GitHub Actions matrix testing
- Environment-specific configuration
- Data-driven testing
- Excel/CSV/JSON test data utilities
- API automation with REST Assured
- Retry analyzer for transient failures
- Automatic Allure report publishing
- Slack/Teams test notifications
- SonarQube integration
- Centralized logging with Log4j2
- Dockerized test execution
- Cloud execution using BrowserStack/Sauce Labs

---

# 👨‍💻 Author

**Vishal Giram**

QA Engineer | Automation Test Engineer

---

## 📄 License

This project is intended for learning, practice, and automation framework development.
