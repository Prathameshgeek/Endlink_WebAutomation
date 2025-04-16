package Geekyants.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class retryAnalyzer implements IRetryAnalyzer {


    int count = 0 ;
    int retryCount = 1 ; // Retry attempts for failed tests

    @Override
    public boolean retry(ITestResult result) { // Retry the method a specified number of times

        while (count < retryCount) {
            count ++;
            return true ;
        }
        return false;
    }
}
