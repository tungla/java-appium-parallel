package com.kroger.qa.automation.appium.framework.tests;

import com.kroger.qa.automation.appium.framework.drivers.MobileAndroidDriver;
import com.kroger.qa.automation.appium.framework.drivers.MobileIOSDriver;
import com.kroger.qa.automation.appium.framework.enums.Platform;
import com.kroger.qa.automation.appium.framework.testng.TestNGXMLParameters;
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

        if (!validMobilePlatforms.contains(platformName))
            throw new IllegalArgumentException(String.format("Invalid platformName: %s. Valid values are: %s",
                    xmlParameters.get("platformName"), validMobilePlatforms));

        System.out.printf("xmlParameters.get(\"app\") = %s", xmlParameters.get("app"));
        this.platform = (platformName.equalsIgnoreCase("android")) ? Platform.ANDROID : Platform.IOS;
    }

    @BeforeSuite(alwaysRun = true)
    public void setupMobileDriver() throws MalformedURLException {
        System.out.print("****BeforeSuite 2****\n");
        validateMobilePlatform();
//        mobileDriver = platform.equals(Platform.ANDROID) ? new MobileAndroidDriver().getDriver(xmlParameters) : new MobileIOSDriver().getDriver(xmlParameters);
                mobileDriver = new MobileAndroidDriver().getDriver(xmlParameters);
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        System.out.print("****After Suite****\n");
        if(mobileDriver != null)
            mobileDriver.quit();
    }
}
