package Geekyants.pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.List;

import static java.lang.Thread.sleep;

public class shareMediaAndFile {

    WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    public shareMediaAndFile(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//span[text()='Little Master Sachin Tendulkar'])[2]")
    public WebElement threadUser;

    @FindBy(xpath = "//div[@class='src-components-molecules-homeicon-___homeicon-m__icon-title___1LaHO']//div//span//span[text()='Coworker']")
    public WebElement coworkerFilter;

    @FindBy(xpath = "//div[@class='src-components-molecules-chattitle-___chattitle-m__chattitle___q9ESc']//span[text()='Little Master Sachin Tendulkar']")
    public WebElement sachin;

    @FindBy(css = ".plusicon")
    public WebElement plusIcon;

    @FindBy(id = "image-input")
    public WebElement uploadImage;

    @FindBy(id = "video-input")
    public WebElement uploadVideo;

    @FindBy(id = "file-input")
    public WebElement uploadFile;

    @FindBy(css = ".sendbutton")
    private WebElement sendButton;

    @FindBy(xpath = "(//div[@class='src-components-organisms-swipeToLeft-___swipeToLeft-m__swipeItem-content___2TRh0'])[1]//span")
    private WebElement lastMessageFile;

    public void selectCoworker(){

        wait.until(ExpectedConditions.elementToBeClickable(coworkerFilter));
        coworkerFilter.click();

        wait.until(ExpectedConditions.elementToBeClickable(sachin));
        sachin.click();


    }

    public void sendPDF() throws InterruptedException, AWTException {

        wait.until(ExpectedConditions.elementToBeClickable(plusIcon));
        plusIcon.click();
        Thread.sleep(3000);

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].style.display='block'; arguments[0].style.opacity='1';", uploadFile);
        uploadFile.sendKeys("/Users/prathameshingale/Desktop/Endlink Automation/Endlink_WebAutomation/src/test/resources/TestFile.pdf");
        Thread.sleep(3000);

        wait.until(ExpectedConditions.elementToBeClickable(sendButton));
        sendButton.click();
        Thread.sleep(10000);

    }

    public String fileName(String nameOfFile) throws InterruptedException {
        Thread.sleep(4000);
        String name =  lastMessageFile.getText();
        return name;
    }

    public void sendImage() throws InterruptedException, AWTException {

        wait.until(ExpectedConditions.elementToBeClickable(plusIcon));
        plusIcon.click();
        Thread.sleep(3000);

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].style.display='block'; arguments[0].style.opacity='1';", uploadImage);
        uploadImage.sendKeys("/Users/prathameshingale/Desktop/Endlink Automation/Endlink_WebAutomation/src/test/resources/TestFile.pdf");
        Thread.sleep(5000);

        wait.until(ExpectedConditions.elementToBeClickable(sendButton));
        sendButton.click();
        Thread.sleep(5000);

    }

    public void sendVideo() throws InterruptedException, AWTException {

        wait.until(ExpectedConditions.elementToBeClickable(plusIcon));
        plusIcon.click();
        Thread.sleep(3000);

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].style.display='block'; arguments[0].style.opacity='1';", uploadVideo);
        uploadVideo.sendKeys("/Users/prathameshingale/Desktop/Endlink Automation/Endlink_WebAutomation/src/test/resources/TestFile.pdf");
        Thread.sleep(3000);

        wait.until(ExpectedConditions.elementToBeClickable(sendButton));
        sendButton.click();
        Thread.sleep(8000);

    }
}
