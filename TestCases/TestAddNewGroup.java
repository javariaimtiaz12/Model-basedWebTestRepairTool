import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class TestAddNewGroup {
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
	public void testAddNewGroup() {

		driver.findElement(By.xpath("//*[@id=\"content\"]/form[1]/input")).click();
		driver.findElement(By.xpath("//INPUT[@type='text']/self::INPUT")).sendKeys("Suleman Group Demo");
		driver.findElement(By.xpath("//TEXTAREA[@name='group_header']/self::TEXTAREA"))
				.sendKeys("Suleman Group Header ");
		driver.findElement(By.xpath("//TEXTAREA[@name='group_footer']/self::TEXTAREA"))
				.sendKeys("Suleman Group Footer");
		driver.findElement(By.xpath("//INPUT[@type='submit']/self::INPUT")).click();
		String message = driver.findElement(By.className("msgbox")).getText();
		assertTrue(message.contains("A new group has been entered into the address book."));
	}
}
