package com.kroger.qa.automation.appium.framework.testng;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.HashMap;
import java.util.Map;

public class TestNGXMLParameters {
    protected static Map<String, String> xmlParameters;

    @Parameters({
            "appiumPort", "platformName", "platformVersion", "deviceName", "fullReset", "app",
            "appPackage", "appActivity", "udid", "noReset", "printPageSourceOnFindFailure",
            "avdLaunchTimeout", "avdReadyTimeout", "bundleId", "clearSystemFiles"
    })
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(
            @Optional String appiumPort, @Optional String platformName, @Optional String platformVersion,
            @Optional String deviceName, @Optional String fullReset, @Optional String app,
            @Optional String appPackage, @Optional String appActivity,
            @Optional String udid, @Optional String noReset, @Optional String printPageSourceOnFindFailure,
            @Optional String avdLaunchTimeout, @Optional String avdReadyTimeout, @Optional String bundleId,
            @Optional String clearSystemFiles
    ) {
        xmlParameters = new HashMap<>();
        xmlParameters.put("platformName", platformName);
        xmlParameters.put("platformVersion", platformVersion);
        xmlParameters.put("deviceName", deviceName);
        xmlParameters.put("app", app);
        xmlParameters.put("appPackage", appPackage);
        xmlParameters.put("appActivity", appActivity);
        xmlParameters.put("udid", udid);
        xmlParameters.put("appiumPort", appiumPort);
        xmlParameters.put("noReset", noReset);
        xmlParameters.put("fullReset", fullReset);
        xmlParameters.put("printPageSourceOnFindFailure", printPageSourceOnFindFailure);
        xmlParameters.put("avdLaunchTimeout", avdLaunchTimeout);
        xmlParameters.put("avdReadyTimeout", avdReadyTimeout);
        xmlParameters.put("bundleId", bundleId);
        xmlParameters.put("clearSystemFiles", clearSystemFiles);
    }
}
