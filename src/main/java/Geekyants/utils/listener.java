package Geekyants.utils;

import Geekyants.core.baseClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class listener implements ITestListener, IAnnotationTransformer {

    public ExtentSparkReporter sparkReporter;
    public static ExtentReports extent;
    public ExtentTest logger;

    public String reportName;
    public Method testMethod;


    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        // Configure test retries using the RetryAnalyzer
        annotation.setRetryAnalyzer(retryAnalyzer.class);
    }


    @Override
    public void onStart(ITestContext context) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(0));
        reportName = "Test-Report - " + timeStamp + ".html";

        // Creates an ExtentSparkReporter instance, specifying the path to the HTML report file.
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/Reports/ExtentReport.html");
        sparkReporter.config().setDocumentTitle("Automation Endlink Webapp");
        sparkReporter.config().setReportName("Automation tests results by Endlink QA Team");
        sparkReporter.config().setTheme(Theme.DARK);


        // Creates an ExtentReports instance and attaches the previously configured ExtentSparkReporter.
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "Endlink webapp ");
        extent.setSystemInfo("Environment", "QA Environment");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));

        // Getting the os & browser from the TestNG.xml file
        String os = context.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("OS", os);

        String browser = context.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);

        // Make sure screenshots directory exists
        new File(System.getProperty("user.dir") + "/Reports/screenshots").mkdirs();
    }


    @Override
    public void onTestFailure(ITestResult result) {
        // Create ExtentTest for the current test case with name and status markup
        logger = extent.createTest("Test case method : " + result.getName());

        // Log failure details if the test case failed in Red colour in reports
        logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test case failed ", ExtentColor.RED));
        logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test case failed ", ExtentColor.RED));

        // Captures a screenshot and adds it to the extent report
        try {
            String imgPath = new utilityMethods().captureScreen(result.getName());
            // Fix the path to use "./screenshots/" format
            String relativePath = "./screenshots/" + new File(imgPath).getName();
            logger.addScreenCaptureFromPath(relativePath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger = extent.createTest("Test case method : " + result.getName());
        logger.log(Status.SKIP,
                MarkupHelper.createLabel(result.getName() + " - Test case skipped ", ExtentColor.ORANGE));
        logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test case failed ", ExtentColor.ORANGE));

        try {
            String imgPath = new utilityMethods().captureScreen(result.getName());
            // Fix the path to use "./screenshots/" format
            String relativePath = "./screenshots/" + new File(imgPath).getName();
            logger.addScreenCaptureFromPath(relativePath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    // This method will execute on test success.
    @Override
    public void onTestSuccess(ITestResult result) {
        logger = extent.createTest("Test case method : " + result.getName());
        logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test case passed ", ExtentColor.GREEN));

        try {
            String imgPath = new utilityMethods().captureScreen(result.getName());
            // Fix the path to use "./screenshots/" format
            String relativePath = "./screenshots/" + new File(imgPath).getName();
            logger.addScreenCaptureFromPath(relativePath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    // This method will run after all tests complete.
    @Override
    public void onFinish(ITestContext context) {
        // Completes the test report generation process.
        extent.flush();

        // Fix the paths in the generated HTML
        try {
            String reportPath = System.getProperty("user.dir") + "/Reports/ExtentReport.html";
            String content = new String(Files.readAllBytes(Paths.get(reportPath)));

            // Replace any potential problematic paths
            content = content.replaceAll("../screenshots/", "./screenshots/");
            content = content.replaceAll("../../screenshots/", "./screenshots/");
            content = content.replaceAll("\"screenshots/", "\"./screenshots/");

            // Write the fixed content back
            Files.write(Paths.get(reportPath), content.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}