package SeleniumLogics;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class ActionsClass {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "D:/aaa/Chrome driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.google.co.in/");
		driver.manage().window().maximize();
		Thread.sleep(2000);
		WebElement a = driver.findElement(By.linkText("Gmail"));
		Actions b = new Actions(driver);

//		1)moveToElement():
		b.moveToElement(a).perform();

//		2)click()
		b.click(a).build().perform();

//		3)doubleClick()
		WebElement doubleClick = driver.findElement(By.name("dblClick"));
		b.doubleClick(doubleClick).build().perform();

//		4)contextClick()   ===>   right click 
		b.contextClick(a).build().perform();

//		5)perform() 
		b.contextClick(a).build().perform();

		/*
		 * 6)sendKeys() Syntax: Actions_class_object .
		 * sendKeys(Keys.ARROW_UP).build().perform();
		 */

//		Two way for using sendKeys() method
		b.sendKeys(Keys.ARROW_DOWN).build().perform();
		for (int i = 7; i >= 0; i--) {
			b.sendKeys(Keys.ARROW_DOWN).build().perform();
			Thread.sleep(2000);
		}
		Thread.sleep(2000);
		for (int i = 0; i <= 6; i++) {
			b.sendKeys(Keys.ARROW_UP).build().perform();
			Thread.sleep(2000);

			b.sendKeys(Keys.TAB).build().perform();
			b.sendKeys(Keys.TAB).build().perform();
			b.sendKeys(Keys.END);
			b.sendKeys(Keys.HOME);
			b.sendKeys(Keys.ENTER).build().perform();

			/*
			 * Methods of Keys Class
			 * 
			 * 1)ARROW_UP
			 * 2)ARROW_DOWN 
			 * 3)ARROW_LEFT 
			 * 4)ARROW_RIGHT 
			 * 5)ENTER 
			 * 6)TAB
			 */
			
//			7)dragAndDrop() 
			WebElement source = driver.findElement(By.id("draggable")); 
			WebElement destination = driver.findElement(By.id("droppable")); 
			b.dragAndDrop(source, destination).build().perform(); 
		}
	}
}
