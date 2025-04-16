package Geekyants.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Properties;

public class configReader {

    private Properties properties;

    public configReader( ) {

        properties = new Properties();

        try (FileInputStream inputStream = new FileInputStream("/Users/prathameshingale/Desktop/Endlink Automation/Endlink_WebAutomation/src/test/resources/config.properties")) {
            properties.load(inputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void main(String[] args) {
        configReader config = new configReader();
    }

}
