package com.kroger.qa.automation.appium.framework.drivers;

import com.kroger.qa.automation.appium.framework.Constants;
import com.kroger.qa.automation.appium.framework.interfaces.MobileAppDriver;
import com.kroger.qa.automation.appium.framework.utils.Log;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MobileIOSDriver extends BaseMobileDriver implements MobileAppDriver {
    private static AppiumDriver<MobileElement> driver;

    private String appiumPort;
    private String platformName;
    private String platformVersion;
    private String deviceName;
    private String fullReset;
    private String app;
    private String bundleId;
    private String udid;
    private String clearSystemFiles;
    Map<String, String> xmlParameters;

    private void initialize() {
        // Required parameters
        this.platformName = xmlParameters.get(platformName);
        this.platformVersion = xmlParameters.get(platformVersion);
        this.deviceName = xmlParameters.get(deviceName);
        this.app = xmlParameters.get(app);
        this.bundleId = xmlParameters.get(bundleId);
        this.udid = xmlParameters.get(udid);
        // Optional params
        this.appiumPort = xmlParameters.get(appiumPort) == null ? Constants.APPIUM.DEFAULT_PARAMS.APPIUM_PORT : xmlParameters.get(appiumPort);
        this.fullReset = xmlParameters.get(fullReset) == null ? Constants.APPIUM.OPTIONAL_PARAMS.IOS.GENERAL.FULL_RESET : xmlParameters.get(fullReset);
        this.clearSystemFiles = xmlParameters.get(clearSystemFiles) == null ? Constants.APPIUM.OPTIONAL_PARAMS.IOS.GENERAL.CLEAR_SYSTEM_FILES: xmlParameters.get(clearSystemFiles);
    }

    private HashMap<String, String> getRequiredParams(){
        return new HashMap<String, String>() {{
            put("platformName", platformName);
            put("platformVersion", platformVersion);
            put("deviceName", deviceName);
            put("app", app);
            put("bundleId", bundleId);
            put("udid", udid);
        }};
    }

    /**
     * <a href="https://appium.io/docs/en/writing-running-appium/caps/index.html">...</a>
     * <a href="https://github.com/appium/appium-xcuitest-driver">...</a>
     * */
    private  DesiredCapabilities getDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        // Required params
        capabilities.setCapability("platformName", this.platformName);
        capabilities.setCapability("platformVersion", this.platformVersion);
        capabilities.setCapability("deviceName", this.deviceName);
        capabilities.setCapability("app", this.app);
        capabilities.setCapability("bundleId", this.bundleId);
        capabilities.setCapability("udid", this.udid);
        // Default params
        capabilities.setCapability(
                "automationName", Constants.APPIUM.DEFAULT_PARAMS.IOS.GENERAL.AUTOMATION_NAME);
        capabilities.setCapability(
                "resetOnSessionStartOnly", Constants.APPIUM.DEFAULT_PARAMS.IOS.GENERAL.RESET_ON_SESSION_START_ONLY);
        // Optional params
        capabilities.setCapability("fullReset", this.fullReset);
        capabilities.setCapability("clearSystemFiles", this.clearSystemFiles);
        return capabilities;
    }

    private void LogMobileDriverCapabilities(){
        Log.info("----- iOS Driver Capabilities -----");
        Log.info("platformName: " + this.platformName);
        Log.info("platformVersion: " + this.platformVersion);
        Log.info("deviceName: " + this.deviceName);
        Log.info("app: " + this.app);
        Log.info("bundleId: " + this.bundleId);
        Log.info("udid: " + this.udid);
        Log.info("appiumPort: " + this.appiumPort);
        Log.info("fullReset: " + this.fullReset);
        Log.info("clearSystemFiles: " + this.clearSystemFiles);
    }

    /** Singleton
     * The same instance of AppiumDriver will be returned no matter how many times it is called.
     * This ensures that only one instance of the driver exists at any time.
     * */
    @Override
    public AppiumDriver<MobileElement> getDriver(Map<String, String> xmlParameters) throws MalformedURLException {
        if(driver == null){
            this.xmlParameters = xmlParameters;
            initialize();
            validateRequiredParams(getRequiredParams());
            validateAppExists(this.app);
            DesiredCapabilities capabilities = getDesiredCapabilities();
            LogMobileDriverCapabilities();
            String url = String.format("http://localhost:%s/wd/hub", this.appiumPort);
            driver = new AppiumDriver<>(new URL(url), capabilities);
        }

        return driver;
    }
}