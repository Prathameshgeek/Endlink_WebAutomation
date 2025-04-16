package Geekyants.utils;

import Geekyants.core.baseClass;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class utilityMethods extends baseClass {

   // Path to your screenshot directory
    private static String screenshotDirectory = System.getProperty("user.dir") + "/test-output/screenshots/";

    // This method is to store screenshots in the screenshots folder in the project directory and get path for reports
    public String captureScreen (String tname) throws IOException {


        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // Timestamp to differentiate the each screenshot
        String screenshotName = tname + "_" + timeStamp + ".png";
        String screenshotPath = System.getProperty("user.dir") + "/test-output/screenshots/" + screenshotName;

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(screenshotPath);

        String path = destinationFile.getAbsolutePath();

        FileUtils.copyFile(screenshot, destinationFile);
        return path; //Returns the path of the screenshot
    }


    public static void cleanupScreenshots() {

        File directory = new File(screenshotDirectory);
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }

    public static void dragAndDrop(WebElement sourceElement  , WebElement targetElement) {

        Actions actions = new Actions(driver);
        actions.dragAndDrop(sourceElement, targetElement).perform();

    }

    public static void dragAndDropUsingAxis(WebElement sourceElement  , int xOffset, int yOffset) {

        Actions actions = new Actions(driver);
        actions.dragAndDropBy( sourceElement,  xOffset,  yOffset).perform();

    }

}
