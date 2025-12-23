package com.orangeHRM.base;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	
	private static final Logger log = LogManager.getLogger(BaseTest.class);

	@Step("Initializing the browser and launching the application")
	@Parameters({"browser"})
	@BeforeTest(alwaysRun = true)
	public void setUp(@Optional String browser) throws BrowserException 
	{
		log.info("Set up to initialize the WebDriver");
		driverFactory = new ActionDriver();
		prop = driverFactory.initProperties();
		String browserName = prop.getProperty("browser");
		if(browser !=null) {
			browserName = browser;
		}
		
		log.info("Launching " + browserName + "for running the tests ");
		driver = driverFactory.initWebDriver(browserName);
		softAssert = new SoftAssert();
		driverFactory.launchApplicationURL();
		loginPage = new LoginPage(driver);
	}

	@Step("Closing the browser")
	@AfterTest(alwaysRun = true)
	public void tearDown() {
		driverFactory.closeBrowser();
	}
	

	@AfterMethod(alwaysRun = true)
	public void attachScreenshot(ITestResult result)
	{
		if(!result.isSuccess())
		{
			ChainTestListener.embed(ActionDriver.getScreenshotAsFile(), "image/png");
		}
	}
}
