package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


public class TipCalculatorPage {
	WebDriver driver;
	
	//Constructor
	public TipCalculatorPage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	//Locators
	@FindBy(xpath="//*[@id=\"billAmount\"]")
	WebElement txt_billAmount;
	
	@FindBy(xpath="//*[@id=\"currency\"]")
	WebElement dropdown_currency;
	
	@FindBy(xpath="//*[@id=\"people\"]")
	WebElement txt_people;
	
    @FindBy(xpath="//button[contains(@class,'star')]")
    java.util.List<WebElement> rating_stars;
	
	@FindBy(xpath="//*[@id=\"serviceRating\"]")
	WebElement slider_rating;
	
	@FindBy(xpath="//*[@id=\"calcBtn\"]")
	WebElement submitBtn;
	
	@FindBy(xpath="//*[@id=\"confirmOk\"]")
	WebElement confirmSubmitBtn;
	
	@FindBy(xpath="//*[@id=\"resetBtn\"]")
	WebElement resetBtn;
	
	@FindBy(xpath="//label[@for='themeSwitch']")
	WebElement theme_toggle;
	
	//results after submitting
	@FindBy(xpath="//*[@id=\"tipAmount\"]")
	WebElement txt_tipAmount;
	
	@FindBy(xpath="//*[@id=\"totalAmount\"]")
	WebElement txt_totalAmount;
	
	@FindBy(xpath="//*[@id=\"perPerson\"]")
	WebElement txt_perPerson;
	
	
	
	//Action Methods
	
	//Enter bill amount
	public void setBillAmount(String bill) {
		txt_billAmount.clear();
		txt_billAmount.sendKeys(bill);
	}
	
	//Select currency
	public void selectCurrecny(String currency) {
		Select dropdown=new Select(dropdown_currency);
		dropdown.selectByValue(currency);
	}
	
	//Enter number of people
	public void setPeople(String people) {
		txt_people.clear();
		txt_people.sendKeys(people);
	}
	
	//Select star rating (1-10)
	public void selectRating(int rating) {
		rating_stars.get(rating - 1).click();
	}
	
	//Move slider rating
	public void selectSliderRating(int value) {
		JavascriptExecutor js=(JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='" + value + "';", slider_rating);
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", slider_rating);
	}
	
	//Click submit
	public void clickSubmit() {
		submitBtn.click();
	}
	
	//Confirm Submit
    public void confirmSubmit() {
    	if(confirmSubmitBtn.isDisplayed()) {
            confirmSubmitBtn.click();
        }
    }

	//Click reset
	public void clickReset() {
		resetBtn.click();
	}
	
	//Switch Theme
    public void toggleTheme() {
        theme_toggle.click();
    }
    
    //Results
    public String getTipAmount() {
        return txt_tipAmount.getText();
    }

    public String getTotalAmount() {
        return txt_totalAmount.getText();
    }

    public String getPerPersonAmount() {
        return txt_perPerson.getText();
    }

	
	
}
