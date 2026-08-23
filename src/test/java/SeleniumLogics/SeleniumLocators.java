package SeleniumLogics;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumLocators {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "E:/Soft/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.google.com/");
		Thread.sleep(2000);
		driver.manage().window().maximize();
		Thread.sleep(2000);

		/*
		 * 1)tagName() 2)id() 3)name() 4)className() 5)linkText() 6)partialLinkText
		 * 7)xpath
		 */
		driver.findElement(By.tagName("a")).click();
		driver.findElement(By.id("email")).sendKeys("836543");
		driver.findElement(By.name("email")).sendKeys("836543");
		driver.findElement(By.className("gb_f")).click(); 
		driver.findElement(By.linkText("Images")).click(); 
		driver.findElement(By.partialLinkText("Im")).click(); 
		driver.findElement(By.xpath("//tagname[@attributename='attributevalue']"));
	}

}
