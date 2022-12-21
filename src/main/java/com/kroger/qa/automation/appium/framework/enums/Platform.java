package com.kroger.qa.automation.appium.framework.enums;

public enum Platform {
    ANDROID("android"),
    IOS("ios");

    private final String platformName;
    Platform(String platformName) { this.platformName = platformName; }

    public String getPlatformName() { return platformName; }
}
