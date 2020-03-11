public static void addProjectTest()  {
		driver.findElement(By.xpath("//a[@href='manage_user_page.php']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[@href='manage_proj_page.php']")).click();
		driver.findElement(By.xpath("//input[@value='Create New Project']")).click();
		Thread.sleep(1000);
		WebElement dropdown1 = driver.findElement(By.name("status"));
		Select dropdownEle1 = new Select(dropdown1);
		dropdownEle1.selectByVisibleText("release");
		Thread.sleep(1000);
		WebElement dropdown2 = driver.findElement(By.name("view_state"));
		Select dropdownEle2 = new Select(dropdown2);
		dropdownEle2.selectByVisibleText("public");
		Thread.sleep(1000);
		driver.findElement(By.name("name")).sendKeys("Project001 New");
		driver.findElement(By.name("description")).sendKeys("Description Description Description");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@value='Add Project']")).click();
		Thread.sleep(5000);
		AssertJUnit.assertEquals(driver.findElement(By.xpath("//a[text()='Project001 New']")).getText(),"Project001 New");
}
