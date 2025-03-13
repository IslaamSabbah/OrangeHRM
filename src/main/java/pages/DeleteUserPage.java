package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class DeleteUserPage {
	WebDriver driver;
	WebDriverWait wait;

	@FindBy(xpath = "//input[contains(@class, 'oxd-input--active')]")
	WebElement searchBox;

	@FindBy(xpath = "//button[@type='submit' and contains(@class, 'oxd-button--secondary')]")
	WebElement searchButton;

	@FindBy(xpath = "//i[contains(@class, 'bi-trash')]")
	WebElement deleteButton;

	// @FindBy(xpath = "//i[contains(@class, 'bi-trash')]")
	@FindBy(xpath = "//*[@id=\"app\"]/div[3]/div/div/div/div[3]/button[2]")
	WebElement yesDeleteButton;

	public DeleteUserPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		PageFactory.initElements(driver, this);
	}

	public void searchUser(String username) {
		wait.until(ExpectedConditions.visibilityOf(searchBox));
		searchBox.clear();
		searchBox.click();
		searchBox.sendKeys(username);
		wait.until(ExpectedConditions.elementToBeClickable(searchButton));
		searchButton.click();
	}

	public void deleteUser(String username) {
		wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
		deleteButton.click();

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions
					.invisibilityOfElementLocated(By.xpath("//div[contains(@class, 'oxd-dialog-container-default')]")));

			yesDeleteButton.click();
			System.out.println("Trash icon clicked.");

		} catch (Exception e) {
			System.out.println("❌ ERROR: Could not click the trash icon.");
			e.printStackTrace();
		}

	}
}
