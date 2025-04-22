package Geekyants.core;

import Geekyants.pageObjects.loginPage;
import Geekyants.utils.configReader;
import Geekyants.utils.utilityMethods;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class baseClass {

    public static WebDriver driver;
    public static Logger log;
    configReader config = new configReader();


    @Parameters({"browser"}) // We are passing browser name from testng.xml as parameter
    @BeforeSuite
    public void beforeSuiteMethod(@Optional String browser) {

        utilityMethods.cleanupScreenshots();
        
        log = LogManager.getLogger(this.getClass());
        //This line initializes a Logger instance for current class - Takes class dynamically
        // We can use log.info or log.dubug.  Choose log levels strategically - for any line needs logging

        log.info("Before driver setup");

        driverManager.setupDriver(browser);

        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);




    }

    @BeforeMethod
    public void loginTest() throws InterruptedException {

        String baseUrl = config.getProperty("baseUrl");
        driver.get(baseUrl);

        driver.manage().deleteAllCookies();

        loginPage lgn = new loginPage(driver);

        String expectedUrl = config.getProperty("dashboardURL");
        String expectedTitle = config.getProperty("organizationTitle");

        String url = lgn.login();
        Assert.assertEquals(url, expectedUrl); //Validate the page url upon navigation to the new page.

        String organizationTitle = lgn.selectOrg();
        Assert.assertEquals(organizationTitle, expectedTitle); //Validate the organization title

    }

    @AfterMethod
    public void logout() throws InterruptedException {

        loginPage lgn = new loginPage(driver);
        String verifyUrl = lgn.logout();

        String baseUrl = config.getProperty("baseUrl");
        Assert.assertEquals(verifyUrl, baseUrl); //Validate the page url upon logout comes to base URL


    }

    @AfterTest
    public void afterTestMethod() {
         driverManager.quitDriver();
    }

}
