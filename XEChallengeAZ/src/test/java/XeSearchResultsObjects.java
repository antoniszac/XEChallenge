import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class XeSearchResultsObjects {

	WebDriver driver;  

	// Required locators for XE search results web page

	//PriceRange button
	By priceRange = By.id("price_range_container");
	//Price From input
	By priceFrom = By.id("priceFrom");
	//Price To input
	By priceTo = By.id("priceTo");
	//Search button
	By completeSearchButton = By.id("submit_search");
	//Square footage button
	By squareFootage = By.id("area_range_container");
	//Square footage from input
	By squareFootageFrom = By.id("areaFrom");
	//Square footage to input
	By squareFootageTo = By.id("areaTo");
	//Search results
	//By searchResults = By.cssSelector("article[class*='resultItem']");
	By searchResults = By.cssSelector("div[class='articleInfo']");
	//Next page button
	By nextPage = By.cssSelector("a[class='page_btn'][title='Επόμενη σελίδα αποτελεσμάτων']");
	//Previous page button
	By totalResults = By.cssSelector("p[class='count_classifieds']");
	//Image of Ad
	By adImage = By.cssSelector("div[class='slick-track']>div[class*='slide-image']");
	//Sorting link
	By sortingLink = By.id("r_sorting_link");
	//Descending price sort option link
	By priceDescending = By.cssSelector("div[class='close_on_mouseup sortDropdown']>ul>li:nth-child(2)");


	//Constructor
	public XeSearchResultsObjects(WebDriver driver) {
		this.driver= driver;
	}


	// Create actions for this web page

	//Method to set the price range
	public void setPriceRange(String lowPrice, String highPrice) {  
		driver.findElement(priceRange).click();
		driver.findElement(priceFrom).click();
		driver.findElement(priceFrom).sendKeys(lowPrice);
		driver.findElement(priceTo).click();
		driver.findElement(priceTo).sendKeys(highPrice);
	}

	//Method to set the square footage range	
	public void setsquareFootage(String areaFrom, String areaTo) {  
		driver.findElement(squareFootage).click();
		driver.findElement(squareFootageFrom).click();
		driver.findElement(squareFootageFrom).sendKeys(areaFrom);
		driver.findElement(squareFootageTo).click();
		driver.findElement(squareFootageTo).sendKeys(areaTo);
	}  

	public void completeSearch() {  
		driver.findElement(completeSearchButton).click();  
	} 

	//Method to verify results are within the price range
	public void verifyPriceResults(String lowPrice, String highPrice) {

		//Get the number of results on page
		List<WebElement> pageResults = driver.findElements(searchResults);
		for(int i = 0; i<pageResults.size();i++) {
			//Get the price value of each property and convert it to integer
			String temp = pageResults.get(i).getText();
			String price = temp.substring(temp.indexOf('|') + 2, temp.indexOf('€') - 1);
			int priceValue= Integer.parseInt(price);
			int lowPriceValue= Integer.parseInt(lowPrice);
			int highPriceValue= Integer.parseInt(highPrice);
			System.out.println("the price is " + priceValue);
			assertTrue("Verification failed: Price out of range ",lowPriceValue<= priceValue && priceValue <= highPriceValue);		
		}
	}

	//Method to verify results are within the square footage range
	public void verifySqFootageResults(String sqFootageFrom, String sqFootageTo) {

		//Get the number of results on page
		List<WebElement> pageResults = driver.findElements(searchResults);
		for(int i = 0; i<pageResults.size();i++) {
			//Get the square footage value of each property and convert it to integer
			String temp = pageResults.get(i).getText();
			String[] sentences = temp.split("\\r?\\n");
			String sqFootage = StringUtils.substringBefore(sentences[1], " τ");
			int sqFootageValue= Integer.parseInt(sqFootage);
			int sqFootageFromValue= Integer.parseInt(sqFootageFrom);
			int sqFootageToValue= Integer.parseInt(sqFootageTo);
			System.out.println("the square footage is " + sqFootageValue);
			assertTrue("Verification failed: Square Footage is out of range ",
					sqFootageFromValue<= sqFootageValue && sqFootageValue <= sqFootageToValue);
		}
	}

	//Method to go to the next page
	public void pressNextPage() {

		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", driver.findElement(nextPage));
	}

	//Method to calculate the total number of pages by
	//dividing the total number of results with the max number of results per page, which is 24 
	public int calcNoPages() {

		String totalResultsFound = driver.findElement(totalResults).getText();
		String totalResultsString = StringUtils.substringBefore(totalResultsFound , " ακίνητ");
		int totalResultsNumber= Integer.parseInt(totalResultsString);
		System.out.println("Total number of results is " + totalResultsNumber);
		int totalPages = (totalResultsNumber/24);
		System.out.println("Total number of pages is " + totalPages);
		return totalPages;		
	}

	//Method to validate the number of pictures per ad does not exceeds a predefined limit
	public void countPictures(int maxNumberOfPictures) {

		System.out.println("Counting number of images per ad");
		List<WebElement> pageResults = driver.findElements(searchResults);
		for(int i = 0; i<pageResults.size();i++) {
			List<WebElement> numberOfPictures = pageResults.get(i).findElements(adImage);
			assertTrue("Verification failed: Ad has exceeded maximum number of pictures allowed ",
					numberOfPictures.size()<=maxNumberOfPictures);
		}		
	}

	//Method for sorting results by descending price
	public void sortSearchResults() {
		driver.findElement(sortingLink).click();
		driver.findElement(priceDescending).click();
	}

	//Method to verify search results are correctly sorted	
	public void verifySortResults() {
		//take the values for price from webelement and parse it to new list
		//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		ArrayList<Integer> obtainedValues = new ArrayList<Integer>();	
		List<WebElement> pageResults = driver.findElements(searchResults);
		for(int i = 0; i<pageResults.size();i++) {
			String temp = pageResults.get(i).getText();
			String price = temp.substring(temp.indexOf('|') + 2, temp.indexOf('€') - 1);
			int priceValue= Integer.parseInt(price);
			obtainedValues.add(priceValue);
		}
		for(int i=0;i<obtainedValues.size();i++){
			System.out.println(obtainedValues.get(i));
		}
		//verify new list obtainedValues is sorted
		boolean sorting = true;
		for (int i=0;i<obtainedValues.size()-1;i++) {
			if (obtainedValues.get(i) < obtainedValues.get(i+1)) {
				sorting = false;
				break;
			}
		}
		System.out.println("Search results sorted correctly  " + sorting);
		assertTrue("The search results are not correctly sorted", sorting);
	}


}
