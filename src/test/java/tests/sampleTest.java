package tests;

import org.testng.annotations.Test;

import pages.Homepage;
import utilities.BaseClass;

public class sampleTest extends BaseClass {

	@Test
	public void simpleTest() throws InterruptedException {
		Homepage hp = new Homepage(driver);
		hp.validateHomepageContent();
	}
}
