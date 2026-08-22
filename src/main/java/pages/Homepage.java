package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import utilities.Listeners;

public class Homepage {

	private final WebDriver driver;

	// =========================================================
	// LOCATORS
	// =========================================================

	@FindBy(xpath = "//a[@href='/open-source' and text()='Categories']")
	private WebElement categoryLink;

	@FindBy(xpath = "//input[@value='Search']")
	private WebElement searchButton;

	@FindBy(id = "query")
	private WebElement searchTextBox;

	// =========================================================
	// CONSTRUCTOR
	// =========================================================

	public Homepage(WebDriver driver) {

		this.driver = driver;

		PageFactory.initElements(driver, this);
	}

	// =========================================================
	// VALIDATE HOMEPAGE
	// =========================================================

	public void validateHomepageContent() {

		String currentUrl = driver.getCurrentUrl();

		Listeners.logInfo("Current URL: " + currentUrl);

		Assert.assertTrue(currentUrl.contains("mvnrepository.com"),
				"Homepage URL validation failed. " + "Actual URL: " + currentUrl);

		Listeners.logPass("Homepage URL validated successfully.");

		// =====================================================
		// SEARCH
		// =====================================================

		Listeners.logInfo("Entering 'Selenium' into search box.");

		Assert.assertTrue(searchTextBox.isDisplayed(), "Search text box is not displayed.");

		searchTextBox.sendKeys("Selenium");

		Listeners.logPass("Search text entered successfully.");

		Listeners.logInfo("Clicking Search button.");

		Assert.assertTrue(searchButton.isDisplayed(), "Search button is not displayed.");

		searchButton.click();

		Listeners.logPass("Search button clicked successfully.");
	}
}