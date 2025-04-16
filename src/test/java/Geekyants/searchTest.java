package Geekyants;

import Geekyants.core.baseClass;
import Geekyants.pageObjects.searchObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class searchTest extends baseClass {

    @Test( enabled = false)
    public void searchFunction() throws InterruptedException {

        searchObject search = new searchObject(driver);

        Assert.assertTrue(search.searchTextInGroup());
        Assert.assertTrue(search.searchTextInCoworker());
    }
}
