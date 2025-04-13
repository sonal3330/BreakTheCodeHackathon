package com.hackathon.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.hackathon.base.TestBase;

public class LoginPage extends TestBase {
	
  @FindBy(id="username")
  WebElement usernameField;
  
  @FindBy(id="password")
  WebElement passwordField;
  
  @FindBy(xpath="//button[@type='submit']")
  WebElement loginBtn;
  
  @FindBy(id="flash")
  WebElement errorMsg;
    
  public LoginPage() {
		PageFactory.initElements(getDriver(),this);
   }
  
  public LoginPage login(String username, String password) {
      logger.info("Navigating to login page");
	  getDriver().get(prop.getProperty("loginUrl"));
	  sendKeys(usernameField,username);
      logger.debug("Entered username: " + username);
	  sendKeys(passwordField,password);
	  click(loginBtn);
      logger.info("Clicked login button");
	  return this;
  }
  
  public boolean verifyErrorMsg() {
	 logger.info("Checking if the error message is visible.");
	 logger.debug("Is error message visible? " + isVisible(errorMsg));
	 return isVisible(errorMsg);
  }

}
