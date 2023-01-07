package com.kroger.qa.automation.appium.framework;

import com.kroger.qa.automation.appium.framework.tests.BaseTest;
import org.testng.annotations.Test;

public class TestRunner2 extends BaseTest {
    @Test(groups={ "testExample" }, priority=1)
    public void test2() {
        System.out.println("Test Example2");

    }
    
    @Test(groups={ "testExample" }, priority=1)
    public void test3() {
        System.out.println("Test Example3");

    }
}
