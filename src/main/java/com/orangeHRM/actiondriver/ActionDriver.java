package com.orangeHRM.actiondriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.orangeHRM.exceptions.BrowserException;
import com.orangeHRM.exceptions.FrameworkException;
import com.orangeHRM.utils.BrowserUtil;

public class ActionDriver {

	private BrowserUtil browserUtil;
	private Properties prop;
	private static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>();
	private static final Logger log = LogManager.getLogger(ActionDriver.class);
	//warn, info, error, fatal
	public static String isHighLight;

//	mvn clean install -Denv="qa"
	public Properties initProperties() {
		
		String envName = System.getProperty("env");
		if (envName == null) {
			log.info("====== Running the tests in QA environment since no environment is passed ======");
			envName = "qa";
		}

		String filePath;
		log.info("====== Running the tests in " + envName + " environment ======");
		switch (envName.toLowerCase().trim()) {
		case "dev": {
			filePath = "./src/test/resources/config/dev.config.properties";
			break;
		}
		case "qa": {
			filePath = "./src/test/resources/config/qa.config.properties";
			break;
		}
		case "uat": {
			filePath = "./src/test/resources/config/uat.config.properties";
			break;
		}
		case "prod": {
			filePath = "./src/test/resources/config/config.properties";
			break;
		}
		default: {
			log.error("====== Please pass a valid environment name ======");
			throw new FrameworkException("======= Invalid Environment Name is passed ======");
			}
		}

		try {
			FileInputStream fi = new FileInputStream(filePath);
			prop = new Properties();
			prop.load(fi);
		} catch (IOException e) {
			log.error("The configuration file is not found or can not read the file");
		}
		return prop;
	}

	public WebDriver initWebDriver(String browserName) throws BrowserException {
		isHighLight = prop.getProperty("highlight");
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait", "0"));

		OptionsManager opManager = new OptionsManager(prop);

		switch (browserName.trim().toLowerCase()) {
		case "chrome": {
			tldriver.set(new ChromeDriver(opManager.getChromeOptions()));
			break;
		}
		case "edge": {
			tldriver.set(new EdgeDriver(opManager.getEdgeOptions()));
			break;
		}
		case "firefox": {
			tldriver.set(new FirefoxDriver(opManager.getFirefoxOptions()));
			break;
		}
		case "safari": {
			tldriver.set(new SafariDriver());
			break;
		}
		default: {
			log.error("====== INVALID BROWSER =======");
			throw new BrowserException("====== INVALID BROWSER ======");
		}
		}
		getLocalDriver().manage().window().maximize();
		getLocalDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
		getLocalDriver().manage().deleteAllCookies();
		return getLocalDriver();
	}
	
	public static WebDriver getLocalDriver() {
		return tldriver.get();
	}

	public void launchApplicationURL() {
		browserUtil = new BrowserUtil(getLocalDriver());
		String Url = prop.getProperty("url");
		try {
			browserUtil.launchURL(Url);
		} catch (BrowserException e) {
			log.error("====== Please provide correct Application URL ======");
		}
	}

	public void closeBrowser() {
		if (getLocalDriver() != null) {
			browserUtil.quitBrowser();
		}
	}
	
	public static File getScreenshotAsFile() {
		File srcFile = ((TakesScreenshot)getLocalDriver()).getScreenshotAs(OutputType.FILE);
		return srcFile;
	}

	public static byte[] getScreenshotAsBytes() {
		return ((TakesScreenshot)getLocalDriver()).getScreenshotAs(OutputType.BYTES);
	}
	
	public static String getScreenshotAsBase64() {
		String screenshot = ((TakesScreenshot)getLocalDriver()).getScreenshotAs(OutputType.BASE64);
		return screenshot;
	}
}
