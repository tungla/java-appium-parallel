package com.kroger.qa.automation.appium.framework;

public class Constants {

    public static class APPIUM {
        public static class DEFAULT_PARAMS {
            public static final String APPIUM_PORT = "4723";
            public static class IOS {
                public static class GENERAL {
                    public static final String AUTOMATION_NAME = "XCUITest";
                    public static final String RESET_ON_SESSION_START_ONLY = "true";
                }
            }
            public static class ANDROID {
                public static class GENERAL {
                    public static final String AUTOMATION_NAME = "uiautomator2";
                }
            }
        }
        public static class OPTIONAL_PARAMS {
            public static class IOS {
                public static class GENERAL {
                    public static final String FULL_RESET = "false";
                    public static final String CLEAR_SYSTEM_FILES = "true";
                }
            }
            public static class ANDROID {
                public static class GENERAL {
                    public static final String NO_RESET = "false";
                    public static final String FULL_RESET = "false";
                    public static final String PRINT_PAGE_SOURCE_ON_FIND_FAILURE = "false";
                    public static final String SYSTEM_PORT = "8201";
                }
                public static class AVD {
                    public static final String LAUNCH_TIMEOUT = "60000";
                    public static final String READY_TIMEOUT = "60000";
                }
            }
        }
    }
}
