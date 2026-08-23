package SeleniumLogics;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumWebDrivermethods {

	public void allMethodsTogether() throws InterruptedException {

		/*
		 * System is class , setProperty is static method,”
		 * webdriver.chrome.driver”-this is name of chrome driver,”
		 * E:/Soft/chrome_driver2/chromedriver.exe”-path of chrome driver WebDriver-is a
		 * Interface, driver object, ChromeDriver-Interface ,we just up casted
		 * ChromeDriver in WebDriver
		 */

		System.setProperty("webdriver.chrome.driver", "E:/Soft/chrome_driver2/chromedriver.exe");
		WebDriver driver = new ChromeDriver();

//		Open the specific link 
		driver.get("https://www.google.com/");

//		Maximize the browser window
		driver.manage().window().maximize();

//		Thread-Class available in lang package,sleep-Static method of Thread class,(value)-time in millisecond;
		Thread.sleep(1000);

//		Navigate selenium control on another link. 
		driver.navigate().to("");

//		Back to one time
		driver.navigate().back();

//		Refresh the web page
		driver.navigate().refresh();

//		For click on forward arrow 
		driver.navigate().forward();

//		For getting the title of page 
		driver.getTitle();

//		For getting the url of current page 
		driver.getCurrentUrl();

		/*
		 * Purpose: Get the source of the currently loaded page. If the page has been
		 * modified after loading (for example, by JavaScript) there is no guarantee
		 * that the returned text is that of the modified page. Returns: The source of
		 * the current page
		 */
		driver.getPageSource();

		/*
		 * Purpose: Close the current window, if there are multiple windows, it will
		 * close the current window which is active and quits the browser if it's the
		 * last window opened currently.
		 */
		driver.close();

//		Purpose: Quits this driver instance, closing every associated window which is opened. 
		driver.quit();

	}
}
