package com.hackathon.base;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.io.FileHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import com.hackathon.utils.TestUtils;

public class TestBase {
  
  private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
  protected static Properties prop;
  protected Logger logger = LogManager.getLogger(this.getClass());
  
  public static WebDriver getDriver() {
	    return threadDriver.get();
  }

  @BeforeClass
  public static void initialization() {
    try {
      prop = new Properties();
      String projectPath = System.getProperty("user.dir");
      FileInputStream is = new FileInputStream(projectPath
          + "/src/main/java/com/hackathon/config/config.properties");
      prop.load(is);
      WebDriverManager.chromedriver().setup();
      WebDriver driver = new ChromeDriver();
      threadDriver.set(driver);
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
  
//Method to capture screenshot
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
  }
}
