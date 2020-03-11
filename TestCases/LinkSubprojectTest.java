import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class LinkSubprojectTest {
	private static WebDriver driver;

	@BeforeSuite
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Quest\\eclipse-workspace\\FlexTesting\\Driver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(Constants.BASE_URL);

		// Login User Administrator
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys(Constants.ADMIN_USER_NAME);
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(Constants.ADMIN_PASSWORD);
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		Thread.sleep(2000);
	}

	@Test(priority = 0)
	public static void linkSubProjectTest() throws Exception {

		// Delete Project 1
		driver.findElement(By.xpath("//a[@href='manage_user_page.php']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[@href='manage_proj_page.php']")).click();
		driver.findElement(By.xpath("//a[text()='Project001']")).click();

		Thread.sleep(3000);
		WebElement dropdown2 = driver.findElement(By.name("subproject_id"));
		Select dropdownEle2 = new Select(dropdown2);
		dropdownEle2.selectByVisibleText("SubProject");
		driver.findElement(By.xpath("//input[@value='Add as Subproject']")).click();
		Thread.sleep(3000);
		try {
			AssertJUnit.assertEquals(driver.findElement(By.xpath("//td[@class='form-title']")).getText(),
					"Edit Project");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterSuite
	public void close() {
		driver.close();
	}

	public static void jsClick(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

}
