package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AdminPage {
	WebDriver driver;
	WebDriverWait wait;

	@FindBy(xpath = "//span[text()='Admin']")
	WebElement adminTab;

	@FindBy(xpath = "//button[contains(., 'Add')]")
	WebElement addButton;

	@FindBy(xpath = "//input[@placeholder='Search']")
	WebElement searchBox;

	@FindBy(xpath = "//button[contains(text(),'Search')]")
	WebElement searchButton;

	@FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[2]/div/span")
	WebElement userCount;

	public AdminPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Waits up to 10 seconds
		PageFactory.initElements(driver, this);
	}

	public void openAdminPage() {

		wait.until(ExpectedConditions.visibilityOf(adminTab));
		wait.until(ExpectedConditions.elementToBeClickable(adminTab)).click();
		System.out.println("xxxxx admin tab clicked xxxxx");

		wait.until(ExpectedConditions.visibilityOf(addButton));
	}

	public int getUserCount() {
		try {

			driver.navigate().refresh();

			WebElement userCountElement = wait.until(ExpectedConditions.visibilityOf(userCount));

			String countText = userCountElement.getText();
			System.out.println("Raw user count text: " + countText);

			// Extract the numeric value using regex to get only the number inside
			String numericCount = countText.replaceAll("[^0-9]", "").trim();

			if (numericCount.isEmpty()) {
				System.out.println("❌ ERROR: Could not extract user count.");
				return -1;
			}

			int count = Integer.parseInt(numericCount);
			System.out.println("User count extracted: " + count);
			return count;

		} catch (TimeoutException e) {
			System.out.println("❌ ERROR: User count element not found within timeout.");
			e.printStackTrace();
			return -1;
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to retrieve user count.");
			e.printStackTrace();
			return -1;
		}
	}

	public void clickAddUser() {
		wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
		System.out.println("xxxxx add user clicked xxxxx");
	}
}
