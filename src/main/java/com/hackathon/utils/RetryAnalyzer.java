package com.hackathon.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
  private int count = 0;
  private static final int maxTry = 2;

  @Override
  public boolean retry(ITestResult result) {
    if (count < maxTry) {
      count++;
      System.out.println("Retrying test: " + result.getName() +
              " | Attempt: " + count +
              " of " + maxTry +
              " | Status: FAILED");
      return true;
    } else {
    	 System.out.println("Test failed after retries: " + result.getName());
    }
    return false;
  }
}
