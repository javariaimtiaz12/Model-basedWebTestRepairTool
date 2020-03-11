import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class TestDeleteItem {
	WebDriver driver;
	Random random = new Random();
	int rand_number = random.nextInt(1520000);
	static int expected_row_count = 0;
	private static String downloadPath = "C:\\Users\\A653\\Downloads";

	@BeforeSuite
	public void before() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\A653\\Documents\\Eclipse Workspace Jav\\AddressBookTestSuite\\driver2\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.get("http://localhost:8080/addressbookv6.1/");
		expected_row_count = driver
				.findElements(By.cssSelector("table#maintable.sortcompletecallback-applyZebra>tbody>tr")).size();
		System.out.println(expected_row_count);
	}

	@Test(priority = 0)
	public void testdeleteItem() {
		try {
			driver.findElement(By.xpath("//*[@id=\"nav\"]/ul/li[1]/a")).click();
			Thread.sleep(100);
			driver.findElement(By.cssSelector("[title*='Suleman']")).click(); // select certain value from table and
																				// click
			driver.findElement(By.xpath("//*[@id=\"content\"]/form[2]/div[2]/input")).click();
			driver.switchTo().alert().accept();
			Thread.sleep(1000);
			String message = driver.findElement(By.className("msgbox")).getText();
			System.out.println(message);
			Assert.assertEquals("Record successful deleted", message);
			driver.findElement(By.xpath("//*[@id=\"nav\"]/ul/li[1]/a")).click();
			int afterDelete_row_count = driver
					.findElements(By.cssSelector("table#maintable.sortcompletecallback-applyZebra>tbody>tr")).size(); // Check
																														// the
																														// size
																														// of
																														// the
																														// table
			System.out.println("now after delete=" + afterDelete_row_count);
			Assert.assertTrue(afterDelete_row_count == expected_row_count);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
