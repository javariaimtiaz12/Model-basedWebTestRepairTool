public void testnextBirthdays() {
		driver.findElement(By.xpath("//*[@id='nav']/ul/li[7]/a")).click(); 
	driver.findElement(By.xpath("//*[@id=\"nav\"]/ul/li[4]/a")).click();
		 		Assert.assertEquals(true, driver.getPageSource().contains("13."));
	 		Assert.assertEquals(true, driver.getPageSource().contains("Suleman"));	 	 
}