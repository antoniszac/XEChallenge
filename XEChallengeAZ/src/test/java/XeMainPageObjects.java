import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class XeMainPageObjects {

	WebDriver driver;  


	// Required locators for XE home web page

	//Search button
	By searchButton = By.cssSelector("input[data-testid='submit-input']");
	//Field for areas selection
	By areasSelect = By.cssSelector("div[role='button']");
	//All auto complete options
	By autoCompleteOptions = By.cssSelector("label[class*='grid-x align-justify align-middle']");

	//Constructor
	public XeMainPageObjects(WebDriver driver) {
		this.driver= driver;
	}

	
	
	// Create actions for this web page

	//method to press the search button
	public void pressSearch() {  
		driver.findElement(searchButton).click();  
	}  
	
	//method to search for an area and related options
	public void searchAreaAllOptions(String text) {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		driver.findElement(areasSelect).click();
		driver.findElement(areasSelect).sendKeys(text);
		List<WebElement> optionsToSelect = driver.findElements(autoCompleteOptions);
		driver.findElement(areasSelect).sendKeys(Keys.RETURN);
		int numberOfOptions = optionsToSelect.size();
		for (int i=1; i<numberOfOptions; i++) {
			driver.findElement(areasSelect).click();
			driver.findElement(areasSelect).sendKeys(text);
			List<WebElement> optionsToSelect1 = driver.findElements(autoCompleteOptions); 
			executor.executeScript("arguments[0].click();", optionsToSelect1.get(i));
		}
	}
}
