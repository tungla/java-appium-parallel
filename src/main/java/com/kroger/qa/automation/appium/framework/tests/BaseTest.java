package com.kroger.qa.automation.appium.framework.tests;

import com.kroger.qa.automation.appium.framework.Constants;
import com.kroger.qa.automation.appium.framework.drivers.MobileAndroidDriver;
import com.kroger.qa.automation.appium.framework.drivers.MobileIOSDriver;
import com.kroger.qa.automation.appium.framework.enums.Platform;
import com.kroger.qa.automation.appium.framework.testng.TestNGXMLParameters;
import com.kroger.qa.automation.appium.framework.utils.AndroidEmulatorValidator;
import com.kroger.qa.automation.appium.framework.utils.AppiumUtils;
import com.kroger.qa.automation.appium.framework.utils.Log;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseTest extends TestNGXMLParameters {
    AppiumDriver<MobileElement> mobileDriver;
    Platform platform;

    private void validateMobilePlatform() {
        List<String> validMobilePlatforms = new ArrayList<>(Arrays.asList("android", "ios"));
        String platformName = xmlParameters.get("platformName").toLowerCase();

        if (!validMobilePlatforms.contains(platformName)){
            String message = String.format("Invalid platform name: %s. Valid mobile platforms are: %s",
                    platformName, validMobilePlatforms);
            Log.error(message);
            throw new IllegalArgumentException(message);
        }


        this.platform = (platformName.equalsIgnoreCase("android")) ? Platform.ANDROID : Platform.IOS;
    }

    @BeforeSuite(alwaysRun = true)
    public void setupMobileDriver() throws MalformedURLException {
        validateMobilePlatform();
        String appiumPort = xmlParameters.get("appiumPort") == null ? Constants.APPIUM.DEFAULT_PARAMS.APPIUM_PORT : xmlParameters.get("appiumPort");
        AppiumUtils.validateServerIsRunning(appiumPort);
        AndroidEmulatorValidator.validateEmulatorIsRunning();
        mobileDriver = platform.equals(Platform.ANDROID) ? new MobileAndroidDriver().getDriver(xmlParameters) : new MobileIOSDriver().getDriver(xmlParameters);
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        if(mobileDriver != null)
            mobileDriver.quit();
    }
}
