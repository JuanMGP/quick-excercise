package com.accountsiq.automationtest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RandomOrgPage {
	private WebDriver driver;

	@FindBy(xpath = "//a[@name='lists']")
	private WebElement listRandomizerTitle;

	@FindBy(xpath = "//a[@href='https://www.random.org/lists/']")
	private WebElement listRandomizerLink;

	public RandomOrgPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void waitUntilListTitleIsVisible() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(listRandomizerTitle));
	}

	public ListRandomizerPage clickOnRandomizerLink() {
		listRandomizerLink.click();
		return new ListRandomizerPage(driver);
	}
}
