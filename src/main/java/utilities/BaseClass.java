package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

@Listeners(utilities.Listeners.class)
public class BaseClass {

    protected WebDriver driver;
    protected static Properties properties;


    // =========================================================
    // READ CONFIGURATION
    // =========================================================

    @BeforeSuite(alwaysRun = true)
    public void readProperties() throws IOException {

        properties = new Properties();

        String configPath =
                System.getProperty("user.dir")
                        + "/config.properties";

        System.out.println(
                "Loading configuration from: "
                        + configPath);

        try (FileInputStream file =
                     new FileInputStream(configPath)) {

            properties.load(file);
        }

        System.out.println(
                "Configuration loaded successfully.");
    }


    // =========================================================
    // START BROWSER
    // =========================================================

    @BeforeMethod(alwaysRun = true)
    public void startBrowser() {

        /*
         * System properties passed from Maven take priority
         * over config.properties.
         *
         * Example:
         *
         * mvn clean test
         *
         * uses config.properties
         *
         * mvn clean test -Dbrowser=chrome -Dheadless=true
         *
         * uses Maven parameters.
         */

        String browserName =
                System.getProperty(
                        "browser",
                        properties.getProperty(
                                "Browser",
                                "chrome"))
                        .trim();


        String url =
                System.getProperty(
                        "url",
                        properties.getProperty(
                                "URL",
                                "https://mvnrepository.com/"))
                        .trim();


        boolean headless =
                Boolean.parseBoolean(
                        System.getProperty(
                                "headless",
                                properties.getProperty(
                                        "Headless",
                                        "false"))
                                .trim());


        System.out.println(
                "========================================");

        System.out.println(
                "Browser  : [" + browserName + "]");

        System.out.println(
                "Headless : [" + headless + "]");

        System.out.println(
                "URL      : [" + url + "]");

        System.out.println(
                "Thread   : ["
                        + Thread.currentThread().getId()
                        + "]");

        System.out.println(
                "========================================");


        // Initialize driver
        DriverFactory.initializeDriver(
                browserName,
                headless);


        // Get current thread's driver
        driver = DriverFactory.getDriver();


        // Browser timeout configuration
        driver.manage()
                .timeouts()
                .implicitlyWait(
                        Duration.ofSeconds(30));


        /*
         * Maximize only for normal GUI execution.
         *
         * For headless execution, window-size is configured
         * in DriverFactory.
         */
        if (!headless) {

            driver.manage()
                    .window()
                    .maximize();
        }


        // Launch application
        driver.get(url);


        System.out.println(
                "Application launched successfully.");

        System.out.println(
                "Current URL: "
                        + driver.getCurrentUrl());
    }


    // =========================================================
    // CLOSE BROWSER
    // =========================================================

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {

        System.out.println(
                "Closing browser...");

        DriverFactory.quitDriver();

        driver = null;

        System.out.println(
                "Browser closed successfully.");
    }
}