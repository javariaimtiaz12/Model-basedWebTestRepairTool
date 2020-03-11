import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class TestEditGroup {
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
	public void testeditGroup() {

		driver.findElement(By.xpath("//A[@href='group.php'][text()='groups']/..")).click();
		try {
			driver.findElement(By.xpath("(//INPUT[@type='checkbox'])[2]")).click();
		} catch (Exception e) {
			driver.findElement(By.xpath("(//INPUT[@type='checkbox'])[1]")).click();
		}

		driver.findElement(By.xpath("(//INPUT[@type='submit'])[3]")).click();

		// Edit Group
		driver.findElement(By.xpath("//INPUT[@type='text']/self::INPUT"))
				.sendKeys("Suleman Group Demo Edit " + rand_number);

		driver.findElement(By.xpath("//TEXTAREA[@name='group_header']/self::TEXTAREA"))
				.sendKeys("Suleman Group Header Edit");

		driver.findElement(By.xpath("//TEXTAREA[@name='group_footer']/self::TEXTAREA"))
				.sendKeys("Suleman Group Footer Edit");

		driver.findElement(By.xpath("//INPUT[@type='submit']/self::INPUT")).click();

	}
}
