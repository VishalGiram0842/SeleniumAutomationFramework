package utilities;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.MediaEntityBuilder;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

import org.openqa.selenium.WebDriver;

import org.testng.*;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Listeners implements ITestListener {

	private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	// =========================================================
	// TEST START
	// =========================================================

	@Override
	public void onTestStart(ITestResult result) {

		String testName = result.getMethod().getMethodName();

		String className = result.getTestClass().getName();

		ExtentTest test = ExtentManager.getExtentReports().createTest(testName);

		extentTest.set(test);

		test.assignCategory(className);

		test.info("Test Started: " + testName);

		test.info("Test Class: " + className);

		test.info("Thread ID: " + Thread.currentThread().getId());

		test.info("Execution Started");

		// Allure
		Allure.label("testClass", className);

		Allure.label("testMethod", testName);

		Allure.label("framework", "Selenium + TestNG");

		Allure.step("Test Started: " + testName);
	}

	// =========================================================
	// TEST SUCCESS
	// =========================================================

	@Override
	public void onTestSuccess(ITestResult result) {

		ExtentTest test = getExtentTest();

		if (test == null) {
			return;
		}

		String testName = result.getMethod().getMethodName();

		test.pass("TEST PASSED");

		test.pass("Test completed successfully: " + testName);

		captureAndAttachScreenshot(result, "PASS");

		Allure.step("TEST PASSED: " + testName);

		cleanup();
	}

	// =========================================================
	// TEST FAILURE
	// =========================================================

	@Override
	public void onTestFailure(ITestResult result) {

		ExtentTest test = getExtentTest();

		Throwable throwable = result.getThrowable();

		String testName = result.getMethod().getMethodName();

		if (test != null) {

			test.fail("TEST FAILED");

			test.fail("Failed Test: " + testName);

			if (throwable != null) {

				test.fail("Exception: " + throwable);

				test.fail("Error Message: " + throwable.getMessage());

				test.fail("Stack Trace: " + getStackTrace(throwable));
			}

			captureAndAttachScreenshot(result, "FAIL");
		}

		// Allure
		Allure.step("TEST FAILED: " + testName);

		if (throwable != null) {

			Allure.addAttachment("Exception", "text/plain", throwable.toString());

			Allure.addAttachment("Stack Trace", "text/plain", getStackTrace(throwable));
		}

		attachAllureScreenshot();

		cleanup();
	}

	// =========================================================
	// TEST SKIPPED
	// =========================================================

	@Override
	public void onTestSkipped(ITestResult result) {

		ExtentTest test = getExtentTest();

		String testName = result.getMethod().getMethodName();

		if (test != null) {

			test.skip("TEST SKIPPED");

			if (result.getThrowable() != null) {

				test.skip("Skip Reason: " + result.getThrowable().getMessage());
			}
		}

		Allure.step("TEST SKIPPED: " + testName);

		if (result.getThrowable() != null) {

			Allure.addAttachment("Skip Reason", "text/plain", result.getThrowable().toString());
		}

		cleanup();
	}

	// =========================================================
	// TEST FAILED BUT WITHIN SUCCESS PERCENTAGE
	// =========================================================

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

		ExtentTest test = getExtentTest();

		if (test != null) {

			test.warning("Test failed but is within " + "success percentage");
		}

		Allure.step("Test failed but is within " + "success percentage");
	}

	// =========================================================
	// TEST FAILED WITH TIMEOUT
	// =========================================================

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {

		onTestFailure(result);
	}

	// =========================================================
	// SUITE START
	// =========================================================

	@Override
	public void onStart(ITestContext context) {

		ExtentManager.getExtentReports();

		String suiteName = context.getSuite().getName();

		ExtentTest suite = ExtentManager.getExtentReports().createTest("Suite: " + suiteName);

		suite.info("Test Suite Started");

		suite.info("Suite Name: " + suiteName);

		Allure.label("suite", suiteName);
	}

	// =========================================================
	// SUITE FINISH
	// =========================================================

	@Override
	public void onFinish(ITestContext context) {

		ExtentManager.flush();

		extentTest.remove();
	}

	// =========================================================
	// HELPER - GET EXTENT TEST
	// =========================================================

	public static ExtentTest getExtentTest() {

		return extentTest.get();
	}

	// =========================================================
	// CENTRALIZED INFO LOG
	// =========================================================

	public static void logInfo(String message) {

		ExtentTest test = getExtentTest();

		if (test != null) {
			test.info(message);
		}

		Allure.step(message);

		System.out.println("[INFO] " + message);
	}

	// =========================================================
	// CENTRALIZED PASS LOG
	// =========================================================

	public static void logPass(String message) {

		ExtentTest test = getExtentTest();

		if (test != null) {
			test.pass(message);
		}

		Allure.step("PASS: " + message);

		System.out.println("[PASS] " + message);
	}

	// =========================================================
	// CENTRALIZED WARNING LOG
	// =========================================================

	public static void logWarning(String message) {

		ExtentTest test = getExtentTest();

		if (test != null) {
			test.warning(message);
		}

		Allure.step("WARNING: " + message);

		System.out.println("[WARNING] " + message);
	}

	// =========================================================
	// CENTRALIZED ERROR LOG
	// =========================================================

	public static void logError(String message) {

		ExtentTest test = getExtentTest();

		if (test != null) {
			test.fail(message);
		}

		Allure.step("ERROR: " + message);

		System.err.println("[ERROR] " + message);
	}

	// =========================================================
	// SCREENSHOT - EXTENT + ALLURE
	// =========================================================

	private void captureAndAttachScreenshot(ITestResult result, String status) {

		WebDriver driver = DriverFactory.getDriver();

		if (driver == null) {
			return;
		}

		try {

			String testName = result.getMethod().getMethodName();

			String screenshotPath = ScreenshotUtil.captureScreenshot(driver, testName + "_" + status);

			if (screenshotPath != null) {

				ExtentTest test = getExtentTest();

				if (test != null) {

					test.log(status.equals("FAIL") ? Status.FAIL : Status.PASS, "Screenshot - " + status,
							MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
				}
			}

		} catch (Exception e) {

			logError("Unable to attach screenshot: " + e.getMessage());
		}
	}

	// =========================================================
	// ALLURE SCREENSHOT
	// =========================================================

	@Attachment(value = "Screenshot", type = "image/png")
	private byte[] attachAllureScreenshot() {

		WebDriver driver = DriverFactory.getDriver();

		if (driver == null) {
			return new byte[0];
		}

		try {

			return ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.BYTES);

		} catch (Exception e) {

			return new byte[0];
		}
	}

	// =========================================================
	// STACK TRACE
	// =========================================================

	private String getStackTrace(Throwable throwable) {

		StringBuilder stackTrace = new StringBuilder();

		for (StackTraceElement element : throwable.getStackTrace()) {

			stackTrace.append(element).append(System.lineSeparator());
		}

		return stackTrace.toString();
	}

	// =========================================================
	// CLEANUP
	// =========================================================

	private void cleanup() {

		// Do NOT quit WebDriver here if
		// your @AfterMethod handles driver cleanup.

		extentTest.remove();
	}
}