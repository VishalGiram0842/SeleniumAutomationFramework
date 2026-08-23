package SeleniumLogics;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Total_number_of_links_in_web_page {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "D:/aaa/Chrome driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.google.co.in/");
		driver.manage().window().maximize();
		Thread.sleep(2000);

		List<WebElement> abc = driver.findElements(By.tagName("a"));
		int num = abc.size();
		System.out.println(num);

		for (int i = 0; i <= num - 1; i++) {
			String c = abc.get(i).getText();
			Thread.sleep(1000);
			System.out.println(c);
		}
	}
}
