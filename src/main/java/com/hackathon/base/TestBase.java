package com.hackathon.base;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;
import org.openqa.selenium.io.FileHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import com.hackathon.utils.TestUtils;

public class TestBase {
  
  private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
  private static ThreadLocal<String> sessionId = new ThreadLocal<>();
  protected static Properties prop;
  protected Logger logger = LogManager.getLogger(this.getClass());
  
  public static WebDriver getDriver() {
    return threadDriver.get();
  }
  
  public static String getSessionId() {
    return sessionId.get();
  }

  @BeforeClass
  public static void initialization() {
    try {
      prop = new Properties();
      String projectPath = System.getProperty("user.dir");
      FileInputStream is = new FileInputStream(projectPath
          + "/src/main/java/com/hackathon/config/config.properties");
      prop.load(is);
      
      boolean isRemote = Boolean.parseBoolean(System.getProperty("remote", "true"));
      
      if (isRemote) {
        // Setup for LambdaTest
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("user", prop.getProperty("lambdatest.username"));
        ltOptions.put("accessKey", prop.getProperty("lambdatest.accesskey"));
        ltOptions.put("build", "Hackathon Test Build");
        ltOptions.put("name", "Hack The Code Test");
        ltOptions.put("platformName", "Windows 10");
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");
        
        capabilities.setCapability("LT:Options", ltOptions);
        
        URL url = new URL(prop.getProperty("lambdatest.hub"));
        WebDriver driver = new RemoteWebDriver(url, capabilities);
        threadDriver.set(driver);
        
        // Store session ID for later use
        sessionId.set(((RemoteWebDriver) driver).getSessionId().toString());
        
        System.out.println("LambdaTest Session ID: " + getSessionId());
      } else {
        // Local WebDriver setup
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        threadDriver.set(driver);
      }
      
      getDriver().manage().window().maximize();
      getDriver().manage().deleteAllCookies();
      getDriver().manage().timeouts().implicitlyWait(
      Duration.ofSeconds(TestUtils.Implicit_Wait));
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(
          "Failed to initialize WebDriver: " + e.getMessage());
    }
  }
  
  @AfterMethod
  public void testStatus(ITestResult result) {
    if (getDriver() instanceof RemoteWebDriver) {
      JavascriptExecutor js = (JavascriptExecutor) getDriver();
      
      if (result.isSuccess()) {
        js.executeScript("lambda-status=passed");
        logger.info("Test passed on LambdaTest. Session ID: " + getSessionId());
      } else {
        js.executeScript("lambda-status=failed");
        logger.error("Test failed on LambdaTest. Session ID: " + getSessionId());
      }
    }
  }

  public void waitForVisibility(WebElement e) {
    WebDriverWait wait =
        new WebDriverWait(getDriver(), Duration.ofSeconds(TestUtils.Implicit_Wait));
    wait.until(ExpectedConditions.visibilityOf(e));
  }

  public void click(WebElement e) {
    waitForVisibility(e);
    e.click();
  }

  public void sendKeys(WebElement e, String text) {
    waitForVisibility(e);
    e.sendKeys(text);
  }

  public boolean isVisible(WebElement e) {
    try {
      waitForVisibility(e);
      if (e.isDisplayed()) {
        return true;
      }
    } catch (Exception exception) {
      return false;
    }
    return false;
  }
  
  // Method to capture screenshot
  public static void takeScreenshot(String fileName) {
    try {
      File screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
      File destination = new File("screenshots/" + fileName + ".png");
      FileHandler.copy(screenshot, destination);
      System.out.println("Screenshot saved to: " + destination.getAbsolutePath());
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Failed to capture screenshot");
    }
  }

  @AfterClass
  public static void quit() {
    getDriver().quit();
    threadDriver.remove();
    sessionId.remove();
  }
}