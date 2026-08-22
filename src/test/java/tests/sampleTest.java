package tests;

import org.testng.annotations.Test;

import pages.Homepage;
import utilities.BaseClass;

public class sampleTest extends BaseClass {

	@Test(description = "Validate Maven Repository homepage search")
	public void simpleTest() {
		Homepage homepage = new Homepage(driver);
		homepage.validateHomepageContent();
	}
}