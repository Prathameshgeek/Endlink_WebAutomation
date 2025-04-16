package Geekyants;

import Geekyants.core.baseClass;
import Geekyants.pageObjects.messagePage;
import Geekyants.pageObjects.shareMediaAndFile;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;

import static Geekyants.core.baseClass.driver;

public class MediaAndFileTest extends baseClass {

    @Test(priority = 1 , enabled = false )
    public void sendPdfToCoworker() throws InterruptedException, AWTException {

        messagePage mp = new messagePage(driver);

        shareMediaAndFile shareObj = new shareMediaAndFile(driver);

        shareObj.selectCoworker();
        Thread.sleep(3000);
        shareObj.sendPDF();

        String filename = "TestFile.pdf";
        Assert.assertEquals(shareObj.fileName(filename), filename);

        Thread.sleep(3000);
        Assert.assertTrue(mp.verifyMessageStatus());

}

    @Test(priority = 2 , enabled = false)
    public void sendImageToCoworker() throws InterruptedException, AWTException {

        messagePage mp = new messagePage(driver);
        shareMediaAndFile shareObj = new shareMediaAndFile(driver);

        shareObj.selectCoworker();
        Thread.sleep(3000);
        shareObj.sendImage();

        Thread.sleep(10000);
        String filename = "";
        Assert.assertEquals(shareObj.fileName(filename), filename);

        Thread.sleep(10000);
        Assert.assertTrue(mp.verifyMessageStatus());

    }

    @Test(priority = 3 , enabled = false)
    public void sendVideoToCoworker() throws InterruptedException, AWTException {

        messagePage mp = new messagePage(driver);
        shareMediaAndFile shareObj = new shareMediaAndFile(driver);
        shareObj.selectCoworker();
        Thread.sleep(3000);
        shareObj.sendVideo();
        Thread.sleep(10000);

        String filename = "";
        Assert.assertEquals(shareObj.fileName(filename), filename);
        Thread.sleep(10000);

        Assert.assertTrue(mp.verifyMessageStatus());
    }
}
