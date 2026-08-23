package SeleniumLogics;

import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Windows_Handling {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "D:/aaa/Chrome driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://demo.guru99.com/popup.php ");
		driver.manage().window().maximize();
		Thread.sleep(2000);

		driver.findElement(By.linkText("Click Here")).click();
		Thread.sleep(2000);

		Set<String> s = driver.getWindowHandles();
		Iterator<String> it = s.iterator();
		it.next();
		String w2nd = it.next();
		driver.switchTo().window(w2nd);
		Thread.sleep(2000);
		driver.findElement(By.name("emailid")).sendKeys("gaurav.3n@gmail.com");
		Thread.sleep(2000);
		driver.findElement(By.name("btnLogin")).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText("Click Here")).click();
	}
}
