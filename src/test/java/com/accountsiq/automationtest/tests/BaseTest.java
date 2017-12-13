package com.accountsiq.automationtest.tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class BaseTest {
	protected WebDriver driver;

	@BeforeMethod
	public void setup() {
		ChromeDriverManager.getInstance().setup();
		driver = new ChromeDriver();
		driver.get("https://www.random.org/");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void tearDown() {
		if (null != driver) {
			driver.quit();
		}
	}
}
