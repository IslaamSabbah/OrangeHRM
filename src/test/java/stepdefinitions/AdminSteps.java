package stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.junit.Assert;
import pages.AdminPage;
import pages.AddUserPage;
import pages.DeleteUserPage;
import utils.DriverManager;
import io.cucumber.java.en.*;

public class AdminSteps {
	WebDriver driver = DriverManager.getDriver();
	AdminPage adminPage = new AdminPage(driver);
	AddUserPage addUserPage = new AddUserPage(driver);
	DeleteUserPage deleteUserPage = new DeleteUserPage(driver);

	int initialUserCount;
	String newUsername = "TestUser_" + System.currentTimeMillis(); // Dynamic username

	@When("I navigate to Admin section")
	public void i_navigate_to_admin_section() {
		adminPage.openAdminPage();
	}

	@And("I get the current number of users")
	public void i_get_current_user_count() {
		initialUserCount = adminPage.getUserCount();
	}

	@And("I click on the add button")
	public void i_click_on_add_button() {
		adminPage.clickAddUser();
	}

	@And("I fill in the new user details")
	public void i_fill_in_new_user_details() {
		addUserPage.addUser(newUsername, "Password@123");
	}

	@And("I click save")
	public void i_click_save() {
		System.out.println("User Added: " + newUsername);
	}

	@Then("I verify the number of users increased by 1")
	public void i_verify_user_count_increased() {
		int updatedUserCount = adminPage.getUserCount();
		Assert.assertEquals(initialUserCount + 1, updatedUserCount);
	}

	@When("I search for the new user")
	public void i_search_for_new_user() {
		deleteUserPage.searchUser(newUsername);
	}

	@And("I delete the new user")
	public void i_delete_new_user() {
		deleteUserPage.deleteUser(newUsername);
	}

	@Then("I verify the number of users decreased by 1")
	public void i_verify_user_count_decreased() {
		int finalUserCount = adminPage.getUserCount();
		Assert.assertEquals(initialUserCount, finalUserCount);
	}
}
