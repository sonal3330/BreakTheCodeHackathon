package com.hackathon.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.hackathon.base.TestBase;

public class AlertPage extends TestBase {
	
	@FindBy(xpath="//button[text()='Click for JS Alert']")
	WebElement jsAlertBtn;
	
	@FindBy(xpath="//button[text()='Click for JS Confirm']")
	WebElement jsConfirmBtn;
	
	@FindBy(xpath="//button[text()='Click for JS Prompt']")
	WebElement jsPromptBtn;
	
	@FindBy(id="result")
	WebElement result;
	
	public AlertPage() {
		PageFactory.initElements(getDriver(),this);
   }
	
    public String JSAlertResult() {
    	logger.info("Clicking JS Alert...");
    	click(jsAlertBtn);
    	getDriver().switchTo().alert().accept();
    	logger.debug("Result text: "+result.getText());
        return result.getText();
    }
    
    public String JSConfirmResult() {
    	logger.info("Clicking JS Confirm...");
    	click(jsConfirmBtn);
    	getDriver().switchTo().alert().accept();
    	logger.debug("Result text: "+result.getText());
    	return result.getText();
    }
    
    public String JSPromptResult() {
    	logger.info("Clicking JS Prompt...");
    	click(jsPromptBtn);
    	getDriver().switchTo().alert().sendKeys("testing");
    	getDriver().switchTo().alert().accept();
    	logger.debug("Result text: "+result.getText());
    	return result.getText();
    }

}
