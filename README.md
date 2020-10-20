# XEChallenge

XEChallengAZ
A test script that verifies an end user can search for a property rental ad and receive the correct results according to preset critiria

Technology Used : Java, TestNG, Selenium, Maven
Framework Used: Page Object Model

Features:
The project performs a search for property rental ads based to preset critiria like price and square footage.
The test critiria can be set before the test execution on the SearchAreaTest.java 
When searching for an area all the related option that are offered from autocomplete are selected.
For example when searching for area "Νίκαια" 15 options are offered by autocomplete. The script selects all of them

Tests:

Run the SearchAreaTest.java as TestNG test to execute the test with a chrome browser

Run the SearchAreaTestFirefox.java as TestNG test to execute the test with a firefox browser

Run the SearchAreaTestEdge.java as TestNG test to execute the test with the new Microsoft Edge browser




How to use:
Before running the test select the search area you desire. 
Set the price value range and the square footage range

To verify the functioanlity of price results set a value in the xeSearchObjects.verifyPriceResults out of the price range 
e.g xeSearchObjects.verifyPriceResults(690,700)

To verify the functioanlity of square footage results set a value in the xeSearchObjects.verifySqFootageResults out of the square footage range 
e.g xeSearchObjects.verifySqFootageResults("140", SquareFootTo)

To verify the functionality of the maximum pictures per ad set a value smaller then 10 to xeSearchObjects.countPictures(maxNumber)
e.g xeSearchObjects.countPictures(3)

To verify the functionality of the sort results change the locator for priceDescending in the XeSearchResultsProjectsObjects.java to 
By priceDescending = By.cssSelector("div[class='close_on_mouseup sortDropdown']>ul>li:nth-child(3)");
