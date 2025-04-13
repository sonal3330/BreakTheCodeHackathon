package com.hackathon.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.ExtentReports;
import com.hackathon.base.TestBase;
import com.hackathon.utils.ExtentManager;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

public class TestListener extends TestBase implements ITestListener {

  private static final Logger log = LogManager.getLogger(TestListener.class);
  private static ExtentReports extent = ExtentManager.getInstance();
  private static ThreadLocal<ExtentTest> testReport = new ThreadLocal<>();

  @Override
  public void onTestStart(ITestResult result) {
    ExtentTest test = extent.createTest(result.getMethod().getMethodName());
    testReport.set(test);
    log.info("Started test: " + result.getMethod().getMethodName());
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    testReport.get().log(Status.PASS, "Test Passed");
    log.info("Passed: " + result.getMethod().getMethodName());
  }

  @Override
  public void onTestFailure(ITestResult result) {
    testReport.get().log(Status.FAIL, "Test Failed: " + result.getThrowable());

    // Take screenshot
    File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
    String path = "screenshots/" + result.getMethod().getMethodName() + ".png";
    try {
      FileHandler.copy(src, new File(path));
      testReport.get().addScreenCaptureFromPath(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
    log.error("Failed: " + result.getMethod().getMethodName());
  }

  @Override
  public void onFinish(ITestContext context) {
    extent.flush();
  }
}
