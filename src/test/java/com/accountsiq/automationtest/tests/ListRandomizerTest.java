package com.accountsiq.automationtest.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.accountsiq.automationtest.pages.ListRandomizerPage;
import com.accountsiq.automationtest.pages.RandomOrgPage;

public class ListRandomizerTest extends BaseTest {

	@Test(description = "Happy path")
	public void successfulStringListRandomization() {

		/*
		 * Test case description: This Test will verify that the randomize function
		 * works on different types of data types, and if it doesn't add or miss
		 * elements from the former list, even if the list has duplicated elements or
		 * special characters
		 */

		RandomOrgPage randomListPage = new RandomOrgPage(driver);

		randomListPage.waitUntilListTitleIsVisible();

		ListRandomizerPage randomizerPage = randomListPage.clickOnRandomizerLink();

		randomizerPage.waitUntilListTitleIsVisible();

		List<String> elements = new ArrayList<>();

		elements = randomizerPage.addElementsToList(elements);

		randomizerPage.insertElements(elements);

		randomizerPage.clickOnRandomizeButton();

		// Set Basic Mode for verifications
		String mode = "HTTP";

		// Get rendomized list
		List<String> randomized = new ArrayList<>();
		randomized = randomizerPage.getElementsFromList(mode);

		// Verify if all elements are present in the randomized list
		assertTrue(randomizerPage.verifyNoMissingElements(elements, randomized));

		// Verify the new list is different from the former list
		assertTrue(randomizerPage.verifyRandomization(elements, randomized));
	}

	@Test(description = "Error message")
	public void lessThanTwoElementsRandomization() {

		/*
		 * Test case description: This test will verify that the randomize function
		 * displays an error message when user tries to randomize a list that contains
		 * less than two elements.
		 */

		RandomOrgPage randomListPage = new RandomOrgPage(driver);

		randomListPage.waitUntilListTitleIsVisible();

		ListRandomizerPage randomizerPage = randomListPage.clickOnRandomizerLink();

		randomizerPage.clickOnRandomizeButton();

		// Verify if error message is displayed when user attempts to randomize an empty
		// list
		assertEquals(randomizerPage.lessThanTwoElementsMessage(), "Error: Your list must contain at least two items");

		// Go back to Randomize function
		randomizerPage.clickOnGoBackButton();

		// An element is added to the list

		randomizerPage.waitUntilListTitleIsVisible();

		randomizerPage.insertElementToTextArea("SingleElement");

		randomizerPage.clickOnRandomizeButton();

		// Verify if error message is displayed when user attempts to randomize a list
		// that contains only one element
		assertEquals(randomizerPage.lessThanTwoElementsMessage(), "Error: Your list must contain at least two items");
	}

	@Test(description = "Boundary Value Analysis")
	public void tenThousanElementsRandomization() {
		/*
		 * Test case description: This Test will verify that the Randomize function will
		 * reject a try with 10.001 elements, and will accept a list with 10.000.
		 * WARNING: this test case takes more than an hour to finish
		 */

		RandomOrgPage randomListPage = new RandomOrgPage(driver);

		randomListPage.waitUntilListTitleIsVisible();

		ListRandomizerPage randomizerPage = randomListPage.clickOnRandomizerLink();

		randomizerPage.waitUntilListTitleIsVisible();

		List<String> elements = new ArrayList<>();

		randomizerPage.insertAThousandOneElements(elements);

		randomizerPage.insertElements(elements);

		randomizerPage.clickOnRandomizeButton();

		// Verify if error message is displayed when user attempts to randomize a
		// list with 10.001 elements
		assertEquals(randomizerPage.moreThanOneThousandElementsMessage(),
				"Error: Your list contained 10001 items, but unfortunately the maximum number is 10000");

		// Go back to Randomize function
		randomizerPage.clickOnGoBackButton();

		// An element is suppressed from the list
		elements.remove(10000);

		randomizerPage.waitUntilListTitleIsVisible();

		// Get the list ready for randomization
		// randomizerPage.clickOnResetButton();
		// Changing strategy because it takes too long!
		randomizerPage.deleteLastElement(elements);

		randomizerPage.clickOnRandomizeButton();

		// Set Basic Mode for verifications
		String mode = "HTTP";

		// Get rendomized list
		List<String> randomized = new ArrayList<>();
		randomized = randomizerPage.getElementsFromList(mode);

		// Verify if all elements are present in the randomized list
		assertTrue(randomizerPage.verifyNoMissingElements(elements, randomized));

		// Verify the new list is different from the former list
		assertTrue(randomizerPage.verifyRandomization(elements, randomized));
	}

	@Test(description = "Output in plain text")
	public void successfulRandomizationInPlainTextMode() {

		/*
		 * Test case description: This Test will verify that the randomize function
		 * works on Advanced Mode. Note: this test fails with special characters when
		 * using plane text option (bug?)
		 */

		RandomOrgPage randomListPage = new RandomOrgPage(driver);

		randomListPage.waitUntilListTitleIsVisible();

		ListRandomizerPage randomizerPage = randomListPage.clickOnRandomizerLink();

		randomizerPage.waitUntilListTitleIsVisible();

		List<String> elements = new ArrayList<>();

		elements = randomizerPage.addElementsToList(elements);

		randomizerPage.clickOnAdvModeButton();

		randomizerPage.clickOnAsTextRadioButton();

		randomizerPage.insertElements(elements);

		randomizerPage.clickOnRandomizeButton();

		String mode = "Plain";
		List<String> randomized = new ArrayList<>();
		randomized = randomizerPage.getElementsFromList(mode);

		// Verify if all elements are present in the randomized list
		assertTrue(randomizerPage.verifyNoMissingElements(elements, randomized));

		// Verify the new list is different from the former list
		assertTrue(randomizerPage.verifyRandomization(elements, randomized));
	}
}
