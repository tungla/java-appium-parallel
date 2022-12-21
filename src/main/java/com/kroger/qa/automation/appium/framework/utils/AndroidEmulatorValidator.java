package com.kroger.qa.automation.appium.framework.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AndroidEmulatorValidator {

    public static boolean isEmulatorRunning() {
        try {
            // Execute the adb devices command and get the output in a string
            String[] command = {"adb", "devices"};
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.readLine();

            // Parse the output to get the list of devices
            List<String> devices = new ArrayList<>();

            while ((output = reader.readLine()) != null) {
                String[] parts = output.split("\\s+");

                if (parts.length >= 2) {
                    String deviceId = parts[0];
                    String deviceStatus = parts[1];

                    if (deviceStatus.equals("device"))
                        devices.add(deviceId);
                }
            }

            // If there is at least one device in the list, it means that there is an emulator running
            return !devices.isEmpty();
        } catch (IOException e) {
            // Something went wrong while executing the adb command
            return false;
        }
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
