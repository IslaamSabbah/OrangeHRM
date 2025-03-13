package stepdefinitions;

import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utils.DriverManager;
import io.cucumber.java.en.*;

public class LoginSteps {
    WebDriver driver = DriverManager.getDriver();
    LoginPage loginPage = new LoginPage(driver);

    @Given("I navigate to the OrangeHRM login page")
    public void i_navigate_to_login_page() {
        driver.get("https://opensource-demo.orangehrmlive.com/");
    }

    @When("I enter {string} as username and {string} as password")
    public void i_enter_credentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        loginPage.clickLogin();
    }

    @Then("I should be redirected to the dashboard")
    public void i_should_be_redirected_to_dashboard() {
        String expectedURL = "dashboard";
        boolean isDashboard = driver.getCurrentUrl().contains(expectedURL);
        assert isDashboard : "Not redirected to dashboard!";
    }
}
