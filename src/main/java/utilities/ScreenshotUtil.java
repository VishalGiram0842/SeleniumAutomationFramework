package utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class ScreenshotUtil {

    private ScreenshotUtil() {
    }

    public static String captureScreenshot(
            WebDriver driver,
            String testName) {

        if (driver == null) {
            return null;
        }

        try {

            String directory =
                    System.getProperty("user.dir")
                            + File.separator
                            + "test-output"
                            + File.separator
                            + "screenshots";

            Files.createDirectories(Path.of(directory));

            String fileName =
                    testName
                            + "_"
                            + System.currentTimeMillis()
                            + ".png";

            Path destination =
                    Path.of(directory, fileName);

            File source =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

            Files.copy(
                    source.toPath(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return destination.toString();

        } catch (Exception e) {

            System.err.println(
                    "Unable to capture screenshot: "
                            + e.getMessage());

            return null;
        }
    }
}