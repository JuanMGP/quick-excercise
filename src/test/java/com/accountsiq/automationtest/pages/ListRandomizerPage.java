package com.accountsiq.automationtest.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ListRandomizerPage {
	private WebDriver driver;

	@FindBy(xpath = "//h2")
	private WebElement listRandomizerTitle;

	@FindBy(xpath = "//textarea[@name='list']")
	private WebElement textArea;

	@FindBy(xpath = "//input[@value='Randomize']")
	private WebElement randomizeButton;

	@FindBy(xpath = "//input[@value='Reset Form']")
	private WebElement resetButton;

	@FindBy(xpath = "//input[@value='Switch to Advanced Mode']")
	private WebElement advModeButton;

	@FindBy(xpath = "//input[@value='plain']")
	private WebElement asTextRadioButton;

	@FindBy(xpath = "//ol/li")
	private List<WebElement> randomizedList;

	@FindBy(xpath = "//*[@id='invisible']/p[2]")
	private WebElement errorMessage;

	@FindBy(xpath = "//*[@id='invisible']//input[@type=\"button\"]")
	private WebElement goBackButton;

	@FindBy(xpath = "//pre")
	private WebElement randomizedListInPlaneText;

	public ListRandomizerPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void waitUntilListTitleIsVisible() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(listRandomizerTitle));
	}

	public void insertElementToTextArea(String element) {
		textArea.sendKeys(element);
		textArea.sendKeys(Keys.RETURN);
	}

	public void insertElements(List<String> elements) {
		String listString = String.join("\n", elements);
		insertElementToTextArea(listString);

	}

	public void clickOnRandomizeButton() {
		randomizeButton.click();
	}

	public void clickOnResetButton() {
		resetButton.click();
	}

	public List<String> addElementsToList(List<String> elements) {
		elements.add("Absolute");
		elements.add("Relative");
		elements.add("123");
		elements.add("Magnitude");
		elements.add("-123");
		elements.add("Magnitude");
		elements.add("Element");
		// elements.add("*!·$%&/");
		// Note: Special characters are not allowed when randomizing in advanced mode
		// with plane text output
		return elements;
	}

	public List<String> getElementsfromRandomizedList() {
		Iterator<WebElement> itr = randomizedList.iterator();
		List<String> randomizedElements = new ArrayList<>();
		while (itr.hasNext()) {
			randomizedElements.add(itr.next().getText());
		}
		return randomizedElements;
	}

	public List<String> getElementsFromListInPlaneText() {
		String tex = randomizedListInPlaneText.getText();
		String[] items = tex.split("\n");
		List<String> randomizedElements = Arrays.asList(items);
		return randomizedElements;
	}

	public boolean verifyNoMissingElements(List<String> formerElements) {
		List<String> randomizedElements = getElementsfromRandomizedList();
		if (formerElements.size() != randomizedElements.size()) {
			return false;
		} else {
			boolean missing = false;
			for (String element : formerElements) {
				if (Collections.frequency(formerElements, element) != Collections.frequency(randomizedElements,
						element)) {
					missing = true;
				}
				if (missing)
					return false;
			}
			return true;
		}
	}

	public boolean verifyRandomization(List<String> formerElements) {
		List<String> randomizedElements = getElementsfromRandomizedList();
		if (formerElements.size() != randomizedElements.size()) {
			return false;
		} else {
			if (formerElements.equals(randomizedElements)) {
				return false;
			} else {
				return true;
			}
		}
	}

	public String lessThanTwoElementsMessage() {
		return errorMessage.getText();
	}

	public void clickOnGoBackButton() {
		goBackButton.click();
	}

	public void clickOnAdvModeButton() {
		advModeButton.click();
	}

	public void clickOnAsTextRadioButton() {
		asTextRadioButton.click();
	}

	public List<String> insertAThousandOneElements(List<String> elements) {
		for (int i = 1; i < 10002; i++) {
			elements.add("E" + i);
		}
		return elements;
	}

	public String moreThanOneThousandElementsMessage() {
		return errorMessage.getText();
	}

	public void deleteLastElement(List<String> elements) {
		// Send a number of Keys.BACK_SPACE equals to the length of the last item of the
		// list
		for (int i = 0; i < elements.get(elements.size() - 1).length() + 1; i++) {
			textArea.sendKeys(Keys.BACK_SPACE);
		}
	}

	public boolean verifyNoMissingElementsAM(List<String> formerElements) {
		List<String> randomizedElements = getElementsFromListInPlaneText();
		if (formerElements.size() != randomizedElements.size()) {
			return false;
		} else {
			boolean missing = false;
			for (String element : formerElements) {
				if (Collections.frequency(formerElements, element) != Collections.frequency(randomizedElements,
						element)) {
					missing = true;
				}
				if (missing)
					return false;
			}
			return true;
		}
	}

	public boolean verifyRandomizationAM(List<String> formerElements) {
		List<String> randomizedElements = getElementsFromListInPlaneText();
		if (formerElements.size() != randomizedElements.size()) {
			return false;
		} else {
			if (formerElements.equals(randomizedElements)) {
				return false;
			} else {
				return true;
			}
		}
	}
}
