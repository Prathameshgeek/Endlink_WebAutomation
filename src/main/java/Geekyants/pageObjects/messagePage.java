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

public class messagePage {

    WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    configReader config = new configReader();

    public messagePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//div[@class='src-components-organisms-homeheader-___homeheader-m__wrapper___25IVO'])[2]")
    public WebElement newMessage;

    @FindBy(xpath = "(//div[@class='src-components-atoms-inputbox-___inputBox-m__inputWrapper___ktFA2 src-components-atoms-inputbox-___inputBox-m__topPlaceHolderDarkBackground___2_bn1'])[3]//input")
    public WebElement searchBar;

    @FindBy(xpath = "//div[@class='src-components-molecules-chattitle-___chattitle-m__chattitle___q9ESc']//div//span")
    public List<WebElement> cowokers;

    @FindBy(xpath = "//div[@class='src-components-molecules-searchrecipient-___searchrecipient-m__name___3Y21z']")
    public List<WebElement> usersList;

    @FindBy(xpath = "(//span[text()='Little Master Sachin Tendulkar'])[2]")
    public WebElement threadUser;

    @FindBy(xpath = "//textarea[@placeholder='Secure Message']")
    private WebElement textField;

    @FindBy(xpath = "(//div[@class='src-components-organisms-swipeToLeft-___swipeToLeft-m__swipeItem-content___2TRh0'])[1]//span//span")
    private WebElement lastMes;

    @FindBy(xpath = "(//div[@class='src-components-organisms-detailedMessage-___detailedmessage-m__messagestatus___scpR-'])[1]//span//span")
    private WebElement textStatus;

    @FindBy(css = ".sendbutton")
    private WebElement sendButton;

    @FindBy(xpath = "//*[@class='rightarrow']")
    private WebElement rightArrow;

    @FindBy(xpath = "//span[text()='Group']")
    private WebElement groupFilter;

    @FindBy(xpath = "//span[text()='Public Batsman Group']")
    private WebElement groupThread;

    @FindBy(xpath = "(//span[text()='Public Batsman Group'])[2]")
    public WebElement threadGroupName;

    @FindBy(xpath = "//div[@class='src-components-organisms-recipientFilters-___recipientFilters-m__width-100___kn_tE']")
    public WebElement cowrkerIcon;



    public void chooseCoworker() throws InterruptedException {

        wait.until(ExpectedConditions.visibilityOf(newMessage));
        newMessage.click();

        wait.until(ExpectedConditions.elementToBeClickable(searchBar));
        searchBar.sendKeys(config.getProperty("userName"));
        cowrkerIcon.click();
        Thread.sleep(4000);
        cowrkerIcon.click();
        Thread.sleep(4000);
        cowrkerIcon.click();


        for (WebElement user : usersList) {

            if (user.getText().equalsIgnoreCase(config.getProperty("userName"))) {
                wait.until(ExpectedConditions.elementToBeClickable(user));
                user.click();
                break;
            }
        }
    }

    public String sendMessageAndVerify(String message) throws InterruptedException {

        textField.sendKeys(message);
        wait.until(ExpectedConditions.elementToBeClickable(sendButton));
        Thread.sleep(4000);

        sendButton.click();
        Thread.sleep(10000);
        String lastMessage = lastMes.getText();
        return lastMessage ;
    }

    public boolean verifyStatus() throws InterruptedException {

        String status = textStatus.getText();
        boolean messageStatus = false;

        if (status.equals("Delivered") || status.equals("Read")) {
            messageStatus = true;
        }
        else {
            driver.navigate().refresh();
            WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOf(textStatus));
        }
        return messageStatus;
    }

    public void chooseGroup() {

        rightArrow.click();

        wait.until(ExpectedConditions.elementToBeClickable(groupFilter));
        groupFilter.click();

        for (WebElement cowoker : cowokers) {
            if (cowoker.getText().equalsIgnoreCase(config.getProperty("groupName"))) {
                // Wait for Web to load email field
                wait.until(ExpectedConditions.visibilityOf(cowoker));
                cowoker.click();
            }
        }
    }
    public boolean verifyGroupMessageThread(){

        boolean flag = false;
        if(threadGroupName.getText().equalsIgnoreCase(config.getProperty("groupName"))){
            flag = true ;
        }
        return flag;
    }

    public String sendMessageInGroupAndVerify(String message) throws InterruptedException {

        textField.sendKeys(message);
        wait.until(ExpectedConditions.elementToBeClickable(sendButton));
        Thread.sleep(4000);

        sendButton.click();
        Thread.sleep(10000);

        String lastMessage = lastMes.getText();
        return lastMessage ;
    }

    public boolean verifyMessageStatus() throws InterruptedException {

        String status = textStatus.getText();
        boolean messageStatus = false;
        if (status.equals("Delivered") || status.equals("Read")) {
            messageStatus = true;
        }
        else {
            driver.navigate().refresh();
            WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOf(textStatus));
        }
        return messageStatus;
    }
}


