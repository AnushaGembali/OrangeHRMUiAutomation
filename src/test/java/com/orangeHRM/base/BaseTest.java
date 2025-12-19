package com.orangeHRM.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.orangeHRM.actiondriver.ActionDriver;
import com.orangeHRM.exceptions.BrowserException;
import com.orangeHRM.pages.DashboardPage;
import com.orangeHRM.pages.LoginPage;

import io.qameta.allure.Step;

@Listeners(ChainTestListener.class)
public class BaseTest {

	private WebDriver driver;
	protected ActionDriver driverFactory;
	protected Properties prop;
	protected LoginPage loginPage;
	protected DashboardPage dashboardPage;
	protected SoftAssert softAssert;

	@Step("Initializing the browser and launching the application")
	@Parameters({"browser"})
	@BeforeTest
	public void setUp(@Optional String browser) throws BrowserException 
	{
		driverFactory = new ActionDriver();
		prop = driverFactory.initProperties();
		String browserName = prop.getProperty("browser");
		if(browser !=null) {
			browserName = browser;
		}

		driver = driverFactory.initWebDriver(browserName);
		softAssert = new SoftAssert();
		driverFactory.launchApplicationURL();
		loginPage = new LoginPage(driver);
	}

	@Step("Closing the browser")
	@AfterTest
	public void tearDown() {
		driverFactory.closeBrowser();
	}
	

	@AfterMethod
	public void attachScreenshot(ITestResult result)
	{
		if(!result.isSuccess())
		{
			ChainTestListener.embed(ActionDriver.getScreenshotAsFile(), "image/png");
		}
	}
}
