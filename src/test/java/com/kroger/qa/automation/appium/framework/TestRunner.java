package com.kroger.qa.automation.appium.framework;

import com.kroger.qa.automation.appium.framework.tests.BaseTest;
import org.testng.annotations.Test;

public class TestRunner extends BaseTest {
    @Test(groups={ "testExample" }, priority=1)
    public void test() {
        System.out.println("Test Example");
    }
}
