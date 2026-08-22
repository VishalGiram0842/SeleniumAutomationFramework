package utilities;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import io.qameta.allure.Allure;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.charset.StandardCharsets;

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

		String testName = result.getMethod().getMethodName();

		if (test != null) {

			test.pass("TEST PASSED");

			test.pass("Test completed successfully: " + testName);

			captureExtentScreenshot(result, "PASS");
		}

		Allure.step("TEST PASSED: " + testName);

		attachAllureScreenshot("PASS Screenshot");

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

				test.fail("Stack Trace:<pre>" + getStackTrace(throwable) + "</pre>");
			}

			captureExtentScreenshot(result, "FAIL");
		}

		// Allure
		Allure.step("TEST FAILED: " + testName);

		if (throwable != null) {

			Allure.addAttachment("Exception", "text/plain", throwable.toString());

			Allure.addAttachment("Error Message", "text/plain",
					throwable.getMessage() == null ? "" : throwable.getMessage());

			Allure.addAttachment("Stack Trace", "text/plain", getStackTrace(throwable));
		}

		attachAllureScreenshot("FAIL Screenshot");

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
	// FAILED WITHIN SUCCESS PERCENTAGE
	// =========================================================

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

		ExtentTest test = getExtentTest();

		if (test != null) {

			test.warning("Test failed but is within " + "success percentage.");
		}

		Allure.step("Test failed but is within " + "success percentage.");
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
	// GET EXTENT TEST
	// =========================================================

	public static ExtentTest getExtentTest() {

		return extentTest.get();
	}

	// =========================================================
	// INFO LOG
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
	// PASS LOG
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
	// WARNING LOG
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
	// ERROR LOG
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
	// EXTENT SCREENSHOT
	// =========================================================

	private void captureExtentScreenshot(ITestResult result, String status) {

		WebDriver driver = getDriverSafely();

		if (driver == null) {

			return;
		}

		try {

			String testName = result.getMethod().getMethodName();

			String screenshotPath = ScreenshotUtil.captureScreenshot(driver, testName + "_" + status);

			if (screenshotPath != null) {

				ExtentTest test = getExtentTest();

				if (test != null) {

					Status reportStatus = status.equalsIgnoreCase("FAIL") ? Status.FAIL : Status.PASS;

					test.log(reportStatus, "Screenshot - " + status,
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

	private void attachAllureScreenshot(String attachmentName) {

		WebDriver driver = getDriverSafely();

		if (driver == null) {

			return;
		}

		try {

			byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

			Allure.addAttachment(attachmentName, "image/png", new java.io.ByteArrayInputStream(screenshot), ".png");

		} catch (Exception e) {

			Allure.addAttachment("Screenshot Error", "text/plain", e.getMessage() == null ? "" : e.getMessage());
		}
	}

	// =========================================================
	// GET DRIVER SAFELY
	// =========================================================

	private WebDriver getDriverSafely() {

		try {

			if (DriverFactory.isDriverInitialized()) {

				return DriverFactory.getDriver();
			}

		} catch (Exception ignored) {
		}

		return null;
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

		extentTest.remove();
	}
}