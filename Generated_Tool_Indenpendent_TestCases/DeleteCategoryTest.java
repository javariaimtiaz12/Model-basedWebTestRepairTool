public static void deletCategoryTest() throws Exception {
		driver.findElement(By.xpath("//a[@href='manage_user_page.php']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[@href='manage_proj_page.php']")).click();
		driver.findElement(By.xpath("//a[text()='Project001']")).click();
		Thread.sleep(1000);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@value='Delete']")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//input[@value='Delete']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@value='Delete Category']")).click();
		Thread.sleep(2000);
		AssertJUnit.assertEquals(driver.findElement(By.xpath("//td[@class='form-title']")).getText(),
					"Edit Project");
		} 