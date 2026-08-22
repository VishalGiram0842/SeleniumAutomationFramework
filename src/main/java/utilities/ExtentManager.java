package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public final class ExtentManager {

    private static ExtentReports extent;

    private static final String REPORTS_FOLDER =
            System.getProperty("user.dir")
                    + File.separator
                    + "Reports";

    private static final String REPORT_FILE =
            REPORTS_FOLDER
                    + File.separator
                    + "ExtentReport.html";


    private ExtentManager() {
        // Prevent object creation
    }


    // =========================================================
    // GET EXTENT REPORT
    // =========================================================

    public static synchronized ExtentReports getExtentReports() {

        if (extent == null) {

            initializeReport();
        }

        return extent;
    }


    // =========================================================
    // INITIALIZE REPORT
    // =========================================================

    private static void initializeReport() {

        try {

            cleanReportsFolder();

            createReportsFolder();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to prepare Reports folder.",
                    e);
        }


        ExtentSparkReporter sparkReporter =
                new ExtentSparkReporter(REPORT_FILE);


        // =====================================================
        // REPORT CONFIGURATION
        // =====================================================

        sparkReporter.config()
                .setDocumentTitle(
                        "Automation Test Report");

        sparkReporter.config()
                .setReportName(
                        "Selenium TestNG Automation Report");

        sparkReporter.config()
                .setTimeStampFormat(
                        "dd-MM-yyyy HH:mm:ss");


        // =====================================================
        // CREATE EXTENT
        // =====================================================

        extent = new ExtentReports();

        extent.attachReporter(sparkReporter);


        // =====================================================
        // SYSTEM INFORMATION
        // =====================================================

        extent.setSystemInfo(
                "Framework",
                "Selenium + TestNG");

        extent.setSystemInfo(
                "Language",
                "Java");

        extent.setSystemInfo(
                "Build Tool",
                "Maven");

        extent.setSystemInfo(
                "Operating System",
                System.getProperty("os.name"));

        extent.setSystemInfo(
                "OS Version",
                System.getProperty("os.version"));

        extent.setSystemInfo(
                "Architecture",
                System.getProperty("os.arch"));

        extent.setSystemInfo(
                "Java Version",
                System.getProperty("java.version"));

        extent.setSystemInfo(
                "Java Vendor",
                System.getProperty("java.vendor"));

        extent.setSystemInfo(
                "User",
                System.getProperty("user.name"));

        extent.setSystemInfo(
                "Browser",
                System.getProperty(
                        "browser",
                        BaseClass.properties.getProperty(
                                "Browser")));

        extent.setSystemInfo(
                "Headless",
                System.getProperty(
                        "headless",
                        BaseClass.properties.getProperty(
                                "Headless")));

        extent.setSystemInfo(
                "URL",
                System.getProperty(
                        "url",
                        BaseClass.properties.getProperty(
                                "URL")));


        System.out.println(
                "Extent Report initialized.");

        System.out.println(
                "Report location: "
                        + REPORT_FILE);
    }


    // =========================================================
    // CLEAN REPORTS FOLDER
    // =========================================================

    private static void cleanReportsFolder()
            throws IOException {

        Path reportsPath =
                Paths.get(REPORTS_FOLDER);


        if (Files.exists(reportsPath)) {

            System.out.println(
                    "Cleaning existing Reports folder...");


            Files.walk(reportsPath)
                    .sorted(
                            Comparator.reverseOrder())
                    .forEach(path -> {

                        try {

                            Files.delete(path);

                        } catch (IOException e) {

                            throw new RuntimeException(
                                    "Unable to delete: "
                                            + path,
                                    e);
                        }
                    });


            System.out.println(
                    "Existing Reports folder cleaned.");
        }
    }


    // =========================================================
    // CREATE REPORTS FOLDER
    // =========================================================

    private static void createReportsFolder()
            throws IOException {

        Path reportsPath =
                Paths.get(REPORTS_FOLDER);


        Files.createDirectories(
                reportsPath);


        System.out.println(
                "Reports folder created at: "
                        + reportsPath
                        .toAbsolutePath());
    }


    // =========================================================
    // FLUSH REPORT
    // =========================================================

    public static synchronized void flush() {

        if (extent != null) {

            extent.flush();

            System.out.println(
                    "========================================");

            System.out.println(
                    "Extent Report generated successfully.");

            System.out.println(
                    REPORT_FILE);

            System.out.println(
                    "========================================");
        }
    }


    // =========================================================
    // GET REPORT PATH
    // =========================================================

    public static String getReportPath() {

        return REPORT_FILE;
    }
}