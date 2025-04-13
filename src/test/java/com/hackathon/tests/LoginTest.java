package com.hackathon.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.hackathon.base.TestBase;
import com.hackathon.pages.LoginPage;

public class LoginTest extends TestBase {
	
	LoginPage loginPage;

  @Test(retryAnalyzer = com.hackathon.utils.RetryAnalyzer.class)
  public void validLoginTest() {
    logger.info("Starting validLoginTest");
    loginPage = new LoginPage();
    loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
    logger.info("Login attempted with valid credentials");
    Assert.assertTrue(getDriver().getCurrentUrl().contains("/secure"), "Login failed!");
    logger.info("validLoginTest completed");
  }

  @Test(retryAnalyzer = com.hackathon.utils.RetryAnalyzer.class)
  public void invalidLoginTest() {
	logger.info("Starting invalidLoginTest");
    loginPage = new LoginPage();
    loginPage.login("invalidUser", "invalidPass");
    logger.info("Login attempted with invalid credentials");
    Assert.assertTrue(loginPage.verifyErrorMsg(), "Error message not displayed!");
    logger.info("invalidLoginTest completed");
  }
}



