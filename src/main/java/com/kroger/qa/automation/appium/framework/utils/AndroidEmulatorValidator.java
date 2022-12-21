package com.kroger.qa.automation.appium.framework.utils;

import java.util.List;



public class AndroidEmulatorValidator {

    public static boolean isEmulatorRunning() {
        List<String> devices = SystemUtils.Android.getADBDevices();
        return !devices.isEmpty();
    }

    public static void validateEmulatorIsRunning() {
        if (!isEmulatorRunning()) {
            String message = "No Android emulator is running. Please start an emulator before running the tests.";
            Log.error(message);
            throw new IllegalArgumentException(message);
        } else {
            Log.info("Android emulator is running.");
        }
    }

}
