package com.kroger.qa.automation.appium.framework.interfaces;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.net.MalformedURLException;
import java.util.Map;

public interface MobileAppDriver {
    AppiumDriver<MobileElement> getDriver(Map<String, String> xmlParameters) throws MalformedURLException;
}
