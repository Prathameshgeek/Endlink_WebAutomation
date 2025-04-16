package Geekyants.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

public class driverManager extends baseClass{

    public static void setupDriver(String browser) {

        switch (browser.toLowerCase()) {

            case "chrome":

                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                //options.addArguments("headless");

                // to enable - 0 - Default (Ask), 1 - Allow,  2 - Block
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("profile.default_content_setting_values.notifications", 1); // Sets the default to "Allow"
                options.setExperimentalOption("prefs", prefs);

                driver = new ChromeDriver(options);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firoptions = new FirefoxOptions();
                firoptions.addArguments("--headless");
                driver = new FirefoxDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeoptions=new EdgeOptions();
                edgeoptions.addArguments("--headless");
                driver = new EdgeDriver();
                break;

            default:
                throw new IllegalArgumentException("Invalid browser specified: " + browser);

        }
    }
    public static void quitDriver() {

        if (driver != null) {
            driver.quit();
            driver = null;
        }

    }
}
