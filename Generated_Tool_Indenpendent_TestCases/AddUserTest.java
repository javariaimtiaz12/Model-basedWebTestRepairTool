public static void addUserTest() throws Exception {
		driver.findElement(By.xpath("//a[@href='manage_user_page.php']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@value='Create New Account']")).click();
		Thread.sleep(1000);
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys("Demo User New");
		driver.findElement(By.name("realname")).clear();
		driver.findElement(By.name("realname")).sendKeys("Demo User Real");
		driver.findElement(By.name("email")).sendKeys("demo@gmail.com");
		driver.findElement(By.xpath("//input[@value='Create User']")).click();
		driver.findElement(By.xpath("//a[@href='manage_user_page.php']")).click();
		Thread.sleep(3000);
			AssertJUnit.assertEquals(driver.findElement(By.xpath("//a[text()='Demo User New']")).getText(),
					"Demo User New");
}