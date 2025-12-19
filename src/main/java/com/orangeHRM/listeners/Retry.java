package com.orangeHRM.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer
{
	private int count = 0;
	private int maxTry = 3;
	@Override
	public boolean retry(ITestResult result) 
	{
		if(!result.isSuccess()) //check if test is pass or fail
		{
				if(count < maxTry) //check number of tries is less than maximum tries
				{
					count++; //Increase the Count by 1
					result.setStatus(ITestResult.FAILURE);//Mark the test as fail
					return true; //inform testng to re run the test
				}
				else {
					result.setStatus(ITestResult.FAILURE); //if max count has reached, mark test as fail
				}		
		}
		else {
			result.setStatus(ITestResult.SUCCESS); //if test passes, mark the test as pass
		}
		return false;//inform testng not to re run the test
	}

}
