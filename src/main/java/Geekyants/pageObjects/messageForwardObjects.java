package Geekyants.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class messageForwardObjects {


    WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    public messageForwardObjects(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//div[@class='src-components-organisms-swipeToLeft-___swipeToLeft-m__swipeItem-content___2TRh0'])[1]//span//span")
    private WebElement lastMes;

    @FindBy(xpath = "//div[@class='src-components-organisms-swipeToLeft-___swipeToLeft-m__buttonlabel___26TM3']//div")
    private WebElement forwardButton;

    @FindBy(xpath = "//div[@class='src-components-organisms-forwardMessagePopup-___forwardMessagePopup-m__header___10ulw']//span")
    private WebElement forwardPopup;

    @FindBy(xpath = "//div[@class='src-components-organisms-forwardMessagePopup-___forwardMessagePopup-m__placeholder___1U538']//following-sibling::div//input")
    private WebElement senderNameField;

    @FindBy(xpath = "//div[@class='src-components-molecules-searchrecipient-___searchrecipient-m__name___3Y21z']//span")
    private WebElement senderName;

    @FindBy(xpath = "//div[@class='src-components-atoms-button-___button-m__label___13JH9']//div//span)[2]")
    private WebElement confirmOption;

    public void sendMessage() throws InterruptedException {

        messagePage mp = new messagePage(driver);
        mp.chooseCoworker();

        String message = "Hello! This is a test automation message to verify forward feature.";
        String lastMessage = mp.sendMessageAndVerify(message);

        Assert.assertEquals(lastMessage, message);

        Thread.sleep(3000);
        Assert.assertTrue(mp.verifyStatus());

    }

    public String forwardMessage() {

        wait.until(ExpectedConditions.elementToBeClickable(forwardButton));
        forwardButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(forwardPopup));

        senderNameField.sendKeys("Sachin");

        wait.until(ExpectedConditions.elementToBeClickable(senderName));
        senderName.click();

        wait.until(ExpectedConditions.elementToBeClickable(confirmOption));
        confirmOption.click();


        return "yes";
    }




}
