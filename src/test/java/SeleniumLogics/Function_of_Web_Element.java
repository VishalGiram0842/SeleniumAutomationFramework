package SeleniumLogics;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Function_of_Web_Element {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "D:/aaa/Chrome driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.google.co.in/");
		driver.manage().window().maximize();
		Thread.sleep(2000);
		
//		1)getText()
		String abc = driver.findElement(By.linkText("Gmail")).getText();
		System.out.println(abc);
		
//		2)isEnabled() 
		Boolean abcd = driver.findElement(By.linkText("Gmail")).isEnabled(); 
		System.out.println(abcd);
		
//		3)isSelected()
		Boolean abc1 = driver.findElement(By.id("isAgeSelected")).isSelected(); 
		System.out.println(abc1); 
		
//		4)isDisplayed 
		Boolean abc2 = driver.findElement(By.className("gb_f")).isDisplayed(); 
		System.out.println(abc2);
	}

}
