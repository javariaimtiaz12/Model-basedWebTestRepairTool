public static void reportIssueTest()  {
		driver.findElement(By.xpath("//a[@href='bug_report_page.php']")).click();
		Thread.sleep(2000);
		WebElement dropdown2 = driver.findElement(By.name("project_id"));
		Select dropdownEle2 = new Select(dropdown2);
		dropdownEle2.selectByVisibleText("Project001 New");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@value='Select Project']")).click();
		Thread.sleep(2000);
		AssertJUnit.assertEquals(driver.findElement(By.xpath("//td[@class='form-title']")).getText(),
					"Enter Report Details");
}