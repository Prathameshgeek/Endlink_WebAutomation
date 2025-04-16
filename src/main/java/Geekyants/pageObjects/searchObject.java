package Geekyants.pageObjects;

import Geekyants.utils.configReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class searchObject {

    WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    configReader config = new configReader();

    public searchObject(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@class='src-components-molecules-homeicon-___homeicon-m__icon-title___1LaHO']//span[text()='All Messages']")
    public WebElement allmessageFilter;

    @FindBy(xpath = "//div[@class='src-components-molecules-homeicon-___homeicon-m__icon-title___1LaHO']//span//span[text()='Coworker']")
    public WebElement coworkerFilter;

    @FindBy(xpath = "//*[@class='search-icon']//following-sibling::div//input")
    public WebElement searchBar;

    @FindBy(xpath = "//div[@class='src-components-rows-search-___search-m__wrapper___1LUwm']//div//div//div//div//div//div//div//div//div//div//div//div")
    public List<WebElement> searchedList;



    public boolean searchTextInGroup() throws InterruptedException {

        boolean flag = false;
        allmessageFilter.click();

        wait.until(ExpectedConditions.elementToBeClickable(searchBar));
        searchBar.sendKeys(config.getProperty("searchText"));

        for(WebElement option : searchedList){
            wait.until(ExpectedConditions.elementToBeClickable(option));
            if(option.getText().equalsIgnoreCase(config.getProperty("groupName"))) {
            flag = true;
          }
        }
        Thread.sleep(5000);
        return flag;
    }

    public boolean searchTextInCoworker( ) throws InterruptedException {

        boolean flag = false;

        searchBar.clear();
        coworkerFilter.click();

        wait.until(ExpectedConditions.elementToBeClickable(searchBar));
        searchBar.sendKeys(config.getProperty("searchText"));

        for(WebElement option : searchedList){
            wait.until(ExpectedConditions.elementToBeClickable(option));
            if(option.getText().equalsIgnoreCase(config.getProperty("userName"))) {
                flag = true;
            }
        }
        Thread.sleep(5000);
        return flag;
    }

}
