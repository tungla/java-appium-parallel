package com.kroger.qa.automation.appium.framework.drivers;

import com.kroger.qa.automation.appium.framework.Constants;
import com.kroger.qa.automation.appium.framework.interfaces.MobileAppDriver;
import com.kroger.qa.automation.appium.framework.utils.Log;
import com.kroger.qa.automation.appium.framework.utils.SystemUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MobileAndroidDriver extends BaseMobileDriver implements MobileAppDriver {

    private static AndroidDriver<MobileElement> driver;
    private String appiumPort;
    private String platformName;
    private String platformVersion;
    private String deviceName;
    private String fullReset;
    private String app;
    private String appPackage;
    private String appActivity;
    private String udid;
    private String noReset;
    private String printPageSourceOnFindFailure;
    private String avdLaunchTimeout;
    private String avdReadyTimeout;
    Map<String, String> xmlParameters;

    private void initialize(){
        // Required parameters
        this.platformName = xmlParameters.get("platformName");
        this.platformVersion = xmlParameters.get("platformVersion");
        this.deviceName = xmlParameters.get("deviceName");
        this.app = xmlParameters.get("app");
        this.appPackage = xmlParameters.get("appPackage");
        this.appActivity = xmlParameters.get("appActivity");
        this.udid = xmlParameters.get("udid");
        // Optional params
        this.appiumPort = xmlParameters.get("appiumPort") == null ? Constants.APPIUM.DEFAULT_PARAMS.APPIUM_PORT : xmlParameters.get("appiumPort");
        this.noReset = xmlParameters.get("noReset") == null ?  Constants.APPIUM.OPTIONAL_PARAMS.ANDROID.GENERAL.NO_RESET : xmlParameters.get("noReset");
        this.fullReset = xmlParameters.get("fullReset") == null ?  Constants.APPIUM.OPTIONAL_PARAMS.ANDROID.GENERAL.FULL_RESET : xmlParameters.get("fullReset");
        this.printPageSourceOnFindFailure = xmlParameters.get("printPageSourceOnFindFailure") == null ? Constants.APPIUM.OPTIONAL_PARAMS.ANDROID.GENERAL.PRINT_PAGE_SOURCE_ON_FIND_FAILURE : xmlParameters.get("printPageSourceOnFindFailure");
        this.avdLaunchTimeout = xmlParameters.get("avdLaunchTimeout") == null ? Constants.APPIUM.OPTIONAL_PARAMS.ANDROID.AVD.LAUNCH_TIMEOUT : xmlParameters.get("avdLaunchTimeout");
        this.avdReadyTimeout = xmlParameters.get("avdReadyTimeout") == null ? Constants.APPIUM.OPTIONAL_PARAMS.ANDROID.AVD.READY_TIMEOUT : xmlParameters.get("avdReadyTimeout");
    }

    private HashMap<String, String> getRequiredParams() {
        return new HashMap<String, String>() {{
            put("platformName", platformName);
            put("platformVersion", platformVersion);
            put("deviceName", deviceName);
            put("app", app);
            put("appPackage", appPackage);
            put("appActivity", appActivity);
        }};
    }

    /** Prerequisites:
     * Install ADB on the machine
     * <a href="https://developer.android.com/studio/command-line/adb">...</a>
     * */

    private void validateDeviceNameExists(){
        List<String> devices = SystemUtils.Android.getADBDevices();
        boolean deviceExists = false;
        for (String device : devices) {
            String[] command = {"adb", "-s", device, "shell", "getprop", "ro.boot.qemu.avd_name"};
            List<String> output = SystemUtils.Common.runSystemCommand(command);
            if (output != null && output.contains(this.deviceName)){
                deviceExists = true;
                break;
            }
        }

        if (!deviceExists){
            String message = String.format("The deviceName '%s' does not exist", this.deviceName);
            Log.error(message);
            throw new IllegalArgumentException(message);
        }

        Log.debug(String.format("The deviceName '%s' exists", this.deviceName));
    }

    /**
     * <a href="https://github.com/appium/appium-uiautomator2-driver#capabilities">...</a>
     * <a href="https://github.com/appium/appium-xcuitest-driver">...</a>
     * */
    private  DesiredCapabilities getDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        // Required params
        capabilities.setCapability("platformName", this.platformName);
        capabilities.setCapability("platformVersion", this.platformVersion);
        capabilities.setCapability("deviceName", this.deviceName);
        capabilities.setCapability("app", this.app);
        capabilities.setCapability("appPackage", this.appPackage);
        capabilities.setCapability("appActivity", this.appActivity);
        capabilities.setCapability("udid", this.udid);
        // Default params
        capabilities.setCapability("automationName", Constants.APPIUM.DEFAULT_PARAMS.ANDROID.GENERAL.AUTOMATION_NAME);
        // Optional params
        capabilities.setCapability("noReset", this.noReset);
        capabilities.setCapability("fullReset", this.fullReset);
        capabilities.setCapability("printPageSourceOnFindFailure", this.printPageSourceOnFindFailure);
        capabilities.setCapability("avdLaunchTimeout", this.avdLaunchTimeout);
        capabilities.setCapability("avdReadyTimeout", this.avdReadyTimeout);
        return capabilities;
    }

    private void LogMobileDriverCapabilities(){
        Log.info("----- Android Driver Capabilities -----");
        Log.info("appiumPort: " + this.appiumPort);
        Log.info("platformName: " + this.platformName);
        Log.info("platformVersion: " + this.platformVersion);
        Log.info("deviceName: " + this.deviceName);
        Log.info("app: " + this.app);
        Log.info("appPackage: " + this.appPackage);
        Log.info("appActivity: " + this.appActivity);
        Log.info("udid: " + this.udid);
        Log.info("noReset: " + this.noReset);
        Log.info("fullReset:  " + this.fullReset);
        Log.info("printPageSourceOnFindFailure: " + this.printPageSourceOnFindFailure);
        Log.info("avdLaunchTimeout: " + this.avdLaunchTimeout);
        Log.info("avdLaunchTimeout: " + this.avdReadyTimeout);
        Log.info("---------------------------------------");
    }

    /** Singleton
     * The same instance of AppiumDriver will be returned no matter how many times it is called.
     * This ensures that only one instance of the driver exists at any time.
     * */
    @Override
    public AndroidDriver<MobileElement> getDriver(Map<String, String> xmlParameters) throws MalformedURLException {
        if(driver == null){
            Log.debug("Creating a new AndroidDriver instance");
            this.xmlParameters = xmlParameters;
            initialize();
            validateRequiredParams(getRequiredParams());
            validateAppExists(this.app);
            validateDeviceNameExists();
            DesiredCapabilities capabilities = getDesiredCapabilities();
            LogMobileDriverCapabilities();
            String url = String.format("http://localhost:%s/wd/hub", this.appiumPort);
            driver = new AndroidDriver<>(new URL(url), capabilities);
        }

        return driver;
    }
}
