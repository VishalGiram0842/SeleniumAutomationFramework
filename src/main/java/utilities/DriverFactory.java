package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public final class DriverFactory {

    private DriverFactory() {
        // Prevent object creation
    }

    private static final ThreadLocal<WebDriver> driver =
            new ThreadLocal<>();


    // =========================================================
    // INITIALIZE DRIVER
    // =========================================================

    public static void initializeDriver(
            String browser,
            boolean headless) {

        WebDriver webDriver;

        if (browser.equalsIgnoreCase("chrome")) {

            System.out.println(
                    "Initializing Chrome browser...");

            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();

            configureChromeOptions(options, headless);

            webDriver = new ChromeDriver(options);

        } else if (browser.equalsIgnoreCase("edge")) {

            System.out.println(
                    "Initializing Edge browser...");

            WebDriverManager.edgedriver().setup();

            EdgeOptions options = new EdgeOptions();

            configureEdgeOptions(options, headless);

            webDriver = new EdgeDriver(options);

        } else {

            throw new IllegalArgumentException(
                    "Unsupported browser: " + browser);
        }

        driver.set(webDriver);

        System.out.println(
                "WebDriver initialized successfully.");
    }


    // =========================================================
    // CHROME OPTIONS
    // =========================================================

    private static void configureChromeOptions(
            ChromeOptions options,
            boolean headless) {

        if (headless) {

            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }

        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
    }


    // =========================================================
    // EDGE OPTIONS
    // =========================================================

    private static void configureEdgeOptions(
            EdgeOptions options,
            boolean headless) {

        if (headless) {

            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }

        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
    }


    // =========================================================
    // GET DRIVER
    // =========================================================

    public static WebDriver getDriver() {

        WebDriver webDriver = driver.get();

        if (webDriver == null) {

            throw new IllegalStateException(
                    "WebDriver is not initialized for the current thread.");
        }

        return webDriver;
    }


    // =========================================================
    // CHECK DRIVER
    // =========================================================

    public static boolean isDriverInitialized() {

        return driver.get() != null;
    }


    // =========================================================
    // QUIT DRIVER
    // =========================================================

    public static void quitDriver() {

        WebDriver webDriver = driver.get();

        if (webDriver != null) {

            try {

                System.out.println(
                        "Quitting WebDriver...");

                webDriver.quit();

            } catch (Exception e) {

                System.err.println(
                        "Error while quitting WebDriver: "
                                + e.getMessage());

            } finally {

                driver.remove();
            }
        }
    }
}