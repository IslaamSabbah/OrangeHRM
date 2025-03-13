package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.util.List;

public class AddUserPage {
	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;

	@FindBy(xpath = "//input[@placeholder='Type for hints...']")
	private WebElement employeeName;

	@FindBy(xpath = "(//div[@class='oxd-grid-item oxd-grid-item--gutters'])[1]//div[contains(@class, 'oxd-select-text')]")
	private WebElement firstDropdown;

	@FindBy(xpath = "(//div[@class='oxd-grid-item oxd-grid-item--gutters'])[3]//div[contains(@class, 'oxd-select-text')]")
	private WebElement secondDropdown;

	@FindBy(xpath = "//input[contains(@class, 'oxd-input oxd-input--active')]")
	private WebElement usernameField;

	@FindBy(xpath = "(//input[@type='password'])[1]")
	private WebElement passwordField;

	@FindBy(xpath = "(//input[@type='password'])[2]")
	private WebElement confirmPasswordField;

	@FindBy(xpath = "//button[normalize-space()='Save']")
	private WebElement saveButton;

	public AddUserPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Increased timeout
		this.js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}

	public void addUser(String username, String password) {
		try {

			System.out.println("Selecting User Role: Admin");
			selectDropdownOption(firstDropdown, "Admin");

			enterEmployeeName("a");
			System.out.println("Employee name entered");

			System.out.println("Selecting Status: Enabled");
			selectDropdownOption(secondDropdown, "Enabled");

			wait.until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(username);
			enterUsername(username);
			System.out.println("Username entered: " + username);

			wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
			System.out.println("Password entered");

			wait.until(ExpectedConditions.visibilityOf(confirmPasswordField)).sendKeys(password);
			System.out.println("Password confirmation entered");

			wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
			System.out.println("Save button clicked");

			wait.until(ExpectedConditions.invisibilityOf(saveButton));
			System.out.println("User added successfully");

		} catch (Exception e) {
			System.out.println("ERROR: Failed to add user");
			e.printStackTrace();
		}
	}

	public void selectDropdownOption(WebElement dropdownElement, String optionText) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			wait.until(ExpectedConditions.elementToBeClickable(dropdownElement)).click();
			System.out.println("Clicked dropdown for: " + optionText);

			WebElement dropdownContainer = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
					"//div[contains(@class, 'oxd-select-dropdown') and not(contains(@style, 'display: none'))]")));
			System.out.println("Dropdown container found.");

			List<WebElement> options = dropdownContainer
					.findElements(By.xpath(".//div[contains(@class, 'oxd-select-option')]"));
			wait.until(ExpectedConditions.visibilityOfAllElements(options));

			System.out.println("Available Options:");
			for (WebElement option : options) {
				System.out.println("  - " + option.getText());
			}

			boolean optionSelected = false;
			for (WebElement option : options) {
				if (option.getText().trim().equalsIgnoreCase(optionText)) {
					wait.until(ExpectedConditions.elementToBeClickable(option)).click();
					System.out.println("Selected: " + optionText);
					optionSelected = true;
					break;
				}
			}

			if (!optionSelected) {
				System.out.println("ERROR: '" + optionText + "' not found in dropdown.");
			}

			Thread.sleep(500);

		} catch (Exception e) {
			System.out.println("ERROR: Failed to select option: " + optionText);
			e.printStackTrace();
		}
	}

	public void enterUsername(String username) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
					usernameField, username);
			System.out.println("Username entered using JavaScript: " + username);
		} catch (Exception e) {
			System.out.println("ERROR: Could not enter username");
			e.printStackTrace();
		}
	}

	public void enterEmployeeName(String name) {
		try {
			employeeName.click();
			employeeName.clear();
			employeeName.sendKeys(name);
			System.out.println("Employee name typed: " + name);

			Thread.sleep(1000);

			employeeName.sendKeys(Keys.ARROW_DOWN);
			Thread.sleep(500);
			employeeName.sendKeys(Keys.ARROW_DOWN);
			Thread.sleep(500);
			employeeName.sendKeys(Keys.ENTER);
			System.out.println("Selected employee using keyboard: " + name);

		} catch (Exception e) {
			System.out.println("ERROR: Could not select employee name");
			e.printStackTrace();
		}
	}

}
