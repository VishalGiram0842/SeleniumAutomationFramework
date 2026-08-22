package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class Homepage {

	WebDriver driver;

	@FindBy(xpath = "(//a[@href=\"/open-source\"])[1]")
	private WebElement catagoryLnk;
	
	@FindBy(xpath = "//input[@value=\"Search\"]")
	private WebElement searchBtn;
	
	@FindBy(id = "query")
	private WebElement typeTextBox;

	public Homepage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}
	
	public void validateHomepageContent() {
		ExpectedConditions.elementToBeClickable(catagoryLnk);
		String text = catagoryLnk.getText();
		System.out.println(text);
		Assert.assertEquals("Categories", text);
		typeTextBox.sendKeys("Selenium");
		searchBtn.click();
	}
}
