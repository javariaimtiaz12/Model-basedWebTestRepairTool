	public void testAddNewGroup() {
		driver.findElement(By.xpath("//*[@id=\"nav\"]/ul/li[3]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"content\"]/form[1]/input")).click();
		driver.findElement(By.id("//INPUT[@type='text']/self::INPUT")).sendKeys("Suleman Group Demo");
		driver.findElement(By.xpath("//TEXTAREA[@name='group_header']/self::TEXTAREA")).sendKeys("Suleman Group Header ");
		driver.findElement(By.xpath("//TEXTAREA[@name='group_footer']/self::TEXTAREA")).sendKeys("Suleman Group Footer");
		driver.findElement(By.xpath("//INPUT[@type='submit']/self::INPUT")).click();
		String message= LocaleMsgReader.getString("group.message");
		Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"content\"]/h1")).getText(),message);
	}
