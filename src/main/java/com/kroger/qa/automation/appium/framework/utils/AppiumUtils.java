package com.kroger.qa.automation.appium.framework.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class AppiumUtils {
    public static boolean serverIsRunning(String appiumPort) {
        try {
            URL url = new URL(String.format("http://localhost:%s/wd/hub/sessions", appiumPort));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            return responseCode == 200;
        } catch (IOException e) {
            return false;
        }
    }

    public static void validateServerIsRunning(String appiumPort) {
        if(!AppiumUtils.serverIsRunning(appiumPort)){
            String message = "Appium server is NOT running";
            Log.error(message);
            throw new RuntimeException(message);
        } else { Log.info("Appium server is running"); }
    }
}
