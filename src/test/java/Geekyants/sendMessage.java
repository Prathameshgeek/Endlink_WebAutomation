package Geekyants;

import Geekyants.core.baseClass;
import Geekyants.pageObjects.loginPage;
import Geekyants.pageObjects.messagePage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class sendMessage extends baseClass {


    @Test(priority = 1  )
    public void sendMessageToCoworker() throws InterruptedException {

        messagePage mp = new messagePage(driver);


        mp.chooseCoworker();

        String message = "Hello! This is a test automation message....";
        String lastMessage = mp.sendMessageAndVerify(message);
         mp.sendMessageAndVerify(message);


        Assert.assertEquals(lastMessage, message);

        Assert.assertTrue(mp.verifyStatus());
    }

    @Test(priority = 2 , enabled = false)
    public void sendMessageInGroup() throws InterruptedException {

        messagePage mp = new messagePage(driver);
        mp.chooseGroup();

        Assert.assertTrue(mp.verifyGroupMessageThread());

        String message = "Hello! This is a test automation message in group.";
        String lastMessage = mp.sendMessageInGroupAndVerify(message);

        Assert.assertEquals(lastMessage, message);

        Thread.sleep(1000);
        Assert.assertTrue(mp.verifyMessageStatus());
    }
}
