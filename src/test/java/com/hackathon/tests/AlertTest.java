package com.hackathon.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.hackathon.base.TestBase;
import com.hackathon.pages.AlertPage;
import com.hackathon.utils.TestUtils;

public class AlertTest extends TestBase {
	
	AlertPage alertPage;
	
	@Test (retryAnalyzer = com.hackathon.utils.RetryAnalyzer.class)
	public void verifyAlerts() {
        logger.info("Starting Alert Test: verifyAlerts");
		getDriver().get(prop.getProperty("alertUrl"));  
		alertPage = new AlertPage();
        logger.debug("JS Alert Result: " + alertPage.JSAlertResult());
		Assert.assertEquals(alertPage.JSAlertResult(),TestUtils.jsAlertResultMsg,"JS Alert failed!");
        logger.debug("JS Confirm Result: " + alertPage.JSConfirmResult());
		Assert.assertEquals(alertPage.JSConfirmResult(), TestUtils.jsConfirmResultMsg, "JS Confirm failed!");
        logger.debug("JS Prompt Result: " + alertPage.JSPromptResult());
		Assert.assertEquals(alertPage.JSPromptResult(), TestUtils.jsPromptResultMsg, "JS Prompt failed!");
        logger.info("Alert Test completed successfully.");
	}

}
