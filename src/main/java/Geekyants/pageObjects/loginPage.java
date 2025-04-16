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

public class loginPage {

    WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    configReader config = new configReader();

    public loginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//input[@type='email']")
    private WebElement Email;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement Password;

    @FindBy(xpath = "//span[text()='Sign In']")
    private WebElement SigninButton;

    @FindBy(xpath = "//div[@class='src-components-molecules-account-___account-m__padding-2___Eyd7i']")
    private List<WebElement> organizations;

    @FindBy(xpath = "//div[@class='src-components-molecules-account-___account-m__organization___2lYec']//div//span[text()='Word Cup 2011']")
    private WebElement testOrg;

    @FindBy(xpath = "//div[@class='src-components-organisms-homeheader-___homeheader-m__d-flex___2Xlth']//span//span")
    private WebElement organisationTitle;

    @FindBy(xpath = "//*[@class='icon logoutbutton']")
    private WebElement logoutButton;

    public String login() throws InterruptedException {

        wait.until(ExpectedConditions.visibilityOf(Email));

        String emailID = config.getProperty("email");
        String password = config.getProperty("password");

        Email.sendKeys(emailID);
        Password.sendKeys(password);
        SigninButton.click();

        Thread.sleep(3000);
        String url = driver.getCurrentUrl();
        return url;

    }

    public String selectOrg(){

        String organizationTitle = config.getProperty("organizationTitle");

        for(WebElement org : organizations){
            if(org.getText().equalsIgnoreCase(organizationTitle)) {
                wait.until(ExpectedConditions.elementToBeClickable(org));
                org.click();
                break;
            }
        }
        return organizationTitle;
    }

    public String logout() throws InterruptedException {

        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        logoutButton.click();

        Thread.sleep(3000);
        String afterLogoutUrl =  driver.getCurrentUrl();
        return afterLogoutUrl;
    }


}
