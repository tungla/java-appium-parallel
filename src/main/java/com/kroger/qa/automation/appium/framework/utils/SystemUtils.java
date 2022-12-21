package com.kroger.qa.automation.appium.framework.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SystemUtils {

    public static class Common {
        public static boolean fileOrFolderExists(String pathname) {
            File file = new File(pathname);
            return file.exists();
        }

        public static List<String> runSystemCommand(String[] command) {
            try {
                Process process = Runtime.getRuntime().exec(command);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                List<String> output = new ArrayList<>();
                String line;

                while ((line = reader.readLine()) != null)
                    output.add(line);

                return output;
            } catch (IOException e) { // Something went wrong while executing the command
                return null;
            }
        }
    }

    public static class Android {

        /** Prerequisites:
         * Install ADB on the machine
         * <a href="https://developer.android.com/studio/command-line/adb">...</a>
         * */
        public static List<String> getADBDevices() {
            List<String> devices = new ArrayList<>();
            String[] command = {"adb", "devices"};
            List<String> output = Common.runSystemCommand(command);

            if(output != null) {
                for (String line : output) {
                    String[] parts = line.split("\\s+");

                    if (parts.length >= 2) {
                        String deviceId = parts[0];
                        String deviceStatus = parts[1];

                        if (deviceStatus.equals("device"))
                            devices.add(deviceId);
                    }
                }
            }

            return devices;
        }
    }
}
