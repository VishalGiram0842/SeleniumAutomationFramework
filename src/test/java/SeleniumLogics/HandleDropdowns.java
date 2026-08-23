package SeleniumLogics;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class HandleDropdowns {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "D:/aaa/Chrome driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.google.co.in/");
		driver.manage().window().maximize();
		Thread.sleep(2000);

		WebElement a = driver.findElement(By.xpath("xpathexp"));
		Select s = new Select(a);
		s.selectByIndex(1);
		s.selectByVisibleText("text");
		s.selectByValue("value");

		/*
		 * if we want to deselect the selected options then we use following methods of
		 * select class s.deselectByIndex(); s.deselectByVisibleText();
		 * s.deselectByValue(); s.deselectAll();
		 */

//		 getFirstSelectedOption() 
		WebElement ab = driver.findElement(By.xpath("xpathexp"));
		Select sb = new Select(ab);
		sb.selectByIndex(0);
		WebElement abv = sb.getFirstSelectedOption();
		System.out.println(abv.getText());

//		isMultiple()
		WebElement as = driver.findElement(By.name("States"));
		Select ss = new Select(as);
		ss.selectByIndex(0);
		ss.selectByIndex(1);
		ss.selectByIndex(2);
		Boolean sm = ss.isMultiple();
		System.out.println(sm);

//		getOptions()
		WebElement al = driver.findElement(By.name("States"));
		Select sl = new Select(al);
		List<WebElement> b = sl.getOptions();
		int c = b.size();
		System.out.println(c);

		for (int i = 0; i <= c - 1; i++) {
			String d = b.get(i).getText();
			System.out.println(d);
			Thread.sleep(2000);
		}
	}
}
