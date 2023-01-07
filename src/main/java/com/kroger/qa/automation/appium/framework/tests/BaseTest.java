package com.kroger.qa.automation.appium.framework.tests;

import com.kroger.qa.automation.appium.framework.Constants;
import com.kroger.qa.automation.appium.framework.drivers.MobileAndroidDriver;
import com.kroger.qa.automation.appium.framework.drivers.MobileIOSDriver;
import com.kroger.qa.automation.appium.framework.enums.Platform;
import com.kroger.qa.automation.appium.framework.testng.XMLParameters;
import com.kroger.qa.automation.appium.framework.utils.AndroidEmulatorValidator;
import com.kroger.qa.automation.appium.framework.utils.AppiumUtils;
import com.kroger.qa.automation.appium.framework.utils.Log;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The BaseTest class contains the common methods for all the test classes.
 * This class MUST be extended by all the test classes in order to initialize the mobile driver.
 * */
public class BaseTest extends XMLParameters {
	AndroidDriver<MobileElement> mobileDriver;
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

    @BeforeMethod(alwaysRun = true)
    public void setupMobileDriver() throws MalformedURLException {
        validateMobilePlatform();
        String appiumPort = xmlParameters.get("appiumPort") == null ? Constants.APPIUM.DEFAULT_PARAMS.APPIUM_PORT : xmlParameters.get("appiumPort");
//        AppiumUtils.validateServerIsRunning(appiumPort);
//        AndroidEmulatorValidator.validateEmulatorIsRunning();
        mobileDriver = platform.equals(Platform.ANDROID) ? new MobileAndroidDriver().getDriver(xmlParameters) : new MobileIOSDriver().getDriver(xmlParameters);
//        DesiredCapabilities caps = new DesiredCapabilities();
//        mobileDriver = new AndroidDriver<MobileElement>(new URL("http://localhost:4444/wd/hub"), caps);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws InterruptedException {
        if(mobileDriver != null){
            Log.debug("The suite execution is completed. Quitting the mobile driver.");
            mobileDriver.quit();
            TimeUnit.SECONDS.sleep(5);
        }
    }
}
