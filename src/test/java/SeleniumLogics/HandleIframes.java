package SeleniumLogics;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HandleIframes {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "E:/Soft/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(
				"https://www.selenium.dev/selenium/docs/api/java/index.html?org/openqa/selenium/package-summary.html");
		Thread.sleep(2000);
		driver.switchTo().frame(0);
		Thread.sleep(2000);
		driver.findElement(By.linkText("org.openqa.selenium.cli")).click();
		Thread.sleep(2000);
		driver.switchTo().parentFrame();
		// driver.switchTo().defaultContent();
		Thread.sleep(1000);
		driver.switchTo().frame(2);
		driver.findElement(By.linkText("By.Remotable")).click();
	}
}
