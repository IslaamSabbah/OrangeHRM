package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import utils.DriverManager; // Updated import

public class Hooks {
	WebDriver driver;

	@Before
	public void setup() {
		driver = DriverManager.getDriver();
		driver.get("https://opensource-demo.orangehrmlive.com/");
	}

	@After
	public void teardown() {
		DriverManager.closeDriver();
	}
}
