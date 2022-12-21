package com.kroger.qa.automation.appium.framework.drivers;

import com.kroger.qa.automation.appium.framework.utils.Log;
import com.kroger.qa.automation.appium.framework.utils.SystemUtils;

import java.util.HashMap;

public class BaseMobileDriver {

    protected void validateAppExists(String app){
        if (!SystemUtils.Common.fileOrFolderExists(app)){
            String message = String.format("The app '%s' does not exist", app);
            Log.error(message);
            throw new IllegalArgumentException(message);
        }

        Log.info(String.format("The app '%s' exists", app));
    }

    protected void validateRequiredParams(HashMap<String, String> requiredParams){
        for (String key : requiredParams.keySet()) {
            if (requiredParams.get(key) == null){
                String message = String.format("Required parameter '%s' is missing from the XML file", key);
                Log.error(message);
                throw new IllegalArgumentException(message);
            }
        }
    }
}
