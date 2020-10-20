import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;




public class SearchAreaTest {

	WebDriver driver;

	//Define the search criteria

	//Define the area to be searched
	private static final String SearchThisArea = "Παγκράτι";
	//Define the price range
	private static final String PriceFrom = "200";
	private static final String PriceTo = "700";
	//Define the square footage range
	private static final String SquareFootFrom = "75";
	private static final String SquareFootTo = "150";
	//Define the maximum number of pictures per ad
	private static final int maxNumber = 10;

	@BeforeTest
	public void setupDriver () {

		//Initialize driver. 

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@Test(priority=1, description="This method searches for rent ads for the given area and all related areas")
	public void propertySearch() {
		driver.get("https://xe.gr/");
		driver.manage().window().maximize();	 

		XeMainPageObjects xeObjects = new XeMainPageObjects(driver);

		xeObjects.searchAreaAllOptions(SearchThisArea);

		xeObjects.pressSearch();

		System.out.println("Completed search for area " + SearchThisArea + " and all related areas");
	}

	@Test(priority=2, description="This method sets the search criteria for price range and square footage range")
	public void setSearchCriteria() {

		XeSearchResultsObjects xeSearchObjects = new XeSearchResultsObjects(driver);

		xeSearchObjects.setPriceRange(PriceFrom,PriceTo);

		xeSearchObjects.setsquareFootage(SquareFootFrom, SquareFootTo);

		xeSearchObjects.completeSearch();

		System.out.println("Set all search criteria for price range " + PriceFrom + "€ to " + PriceTo  + "€" + 
				" and square footage range " + SquareFootFrom + "m2 to "+ SquareFootTo + "m2");
	}

	@Test(priority=3, description="This method validates the search results are within the price range, square footage range "
			+ "and no ad exceeds number of pictures")
	public void verifySearchResults() throws InterruptedException {
		XeSearchResultsObjects xeSearchObjects = new XeSearchResultsObjects(driver);
		Thread.sleep(1000);

		int pages = xeSearchObjects.calcNoPages();		

		if (pages == 0) {
			xeSearchObjects.verifyPriceResults(PriceFrom, PriceTo);

			xeSearchObjects.verifySqFootageResults(SquareFootFrom, SquareFootTo);

			xeSearchObjects.countPictures(maxNumber);
		}
		else {

			for(int i = 1; i<pages;i++) {
				
				Thread.sleep(5000);
				
				xeSearchObjects.verifyPriceResults(PriceFrom, PriceTo);

				xeSearchObjects.verifySqFootageResults(SquareFootFrom, SquareFootTo);

				xeSearchObjects.countPictures(maxNumber);

				xeSearchObjects.pressNextPage();

			}
		}

	}

	@Test(priority=4, description="This method sorts the search results by descending price "
			+ "and verifies the results are correctly sorted")

	public void verifyResultsSortedCorrectly() throws InterruptedException {

		XeSearchResultsObjects xeSearchObjects = new XeSearchResultsObjects(driver);

		xeSearchObjects.sortSearchResults();
		
		Thread.sleep(1000);

		int pages = xeSearchObjects.calcNoPages();

		if (pages == 0) {
			xeSearchObjects.verifySortResults();
		}
		else {

			for(int i = 1; i<pages;i++) {
				
				Thread.sleep(5000);

				xeSearchObjects.verifySortResults();

				xeSearchObjects.pressNextPage();

			}
		}
	}



	@AfterTest
	public void tearDown() throws InterruptedException {
		Thread.sleep(5000);
		driver.close();
		driver.quit();
	}	
}
