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
    protected Properties properties;


    // =========================================================
    // READ CONFIGURATION
    // =========================================================

    @BeforeSuite
    public void readProperties() throws IOException {

        properties = new Properties();

        String configPath =
                System.getProperty("user.dir")
                        + "/config.properties";

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

    @BeforeMethod
    public void startBrowser() {

        String browserName =
                properties.getProperty("Browser", "chrome").trim();

        String url =
                properties.getProperty("URL").trim();

        boolean headless =
                Boolean.parseBoolean(
                        properties.getProperty(
                                "Headless",
                                "false").trim());

        System.out.println(
                "Browser: [" + browserName + "]");

        System.out.println(
                "Headless: [" + headless + "]");

        System.out.println(
                "URL: [" + url + "]");


        // Initialize WebDriver through DriverFactory
        DriverFactory.initializeDriver(
                browserName,
                headless);


        // Get current thread's driver
        driver = DriverFactory.getDriver();


        // Browser configuration
        driver.manage()
                .window()
                .maximize();

        driver.manage()
                .timeouts()
                .implicitlyWait(
                        Duration.ofSeconds(30));


        // Open application
        driver.get(url);


        System.out.println(
                "Application launched successfully.");
    }


    // =========================================================
    // CLOSE BROWSER
    // =========================================================

    @AfterMethod
    public void closeBrowser() {

        System.out.println(
                "Closing browser...");

        DriverFactory.quitDriver();

        driver = null;
    }
}