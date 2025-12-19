package com.orangeHRM.utils;

import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangeHRM.exceptions.BrowserException;

public class BrowserUtil {

	private WebDriver driver;
	
	public BrowserUtil(WebDriver driver) {
		this.driver = driver;
	}

	public void launchURL(String url) throws BrowserException {
		isURLValid(url);
		driver.get(url);
	}

	public void launchURL(URL url) throws BrowserException {
		String urlInStringFormat = String.valueOf(url);
		isURLValid(urlInStringFormat);
		driver.navigate().to(url);
	}

	private boolean isURLValid(String url) throws BrowserException {
		if (url.length() <= 0) {
			System.out.println("====== URL IS NULL or EMPTY ======");
			throw new BrowserException("====== URL IS NULL or EMPTY ======");
		}

		if (url.indexOf("http") != 0) {
			System.out.println("====== PROTOCOL(http OR https) IS MISSING FROM URL ======");
			throw new BrowserException("====== PROTOCOL(http OR https) IS MISSING FROM URL ======");
		}
		return true;
	}

	public String getPageTitle() {
		String pageTitle = driver.getTitle();
		System.out.println("Current Page Title is: " + pageTitle);
		return pageTitle;
	}
	
	public String getPageTitle(int waitTime, String expectedTitle) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
		boolean titlePresent = wait.until(ExpectedConditions.titleIs(expectedTitle));
		if(titlePresent) {
			String pageTitle = driver.getTitle();
			System.out.println("Current Page Title is: " + pageTitle);
			return pageTitle;
		}
		return "";
	}

	public String getPageURL() {
		String pageURL = driver.getCurrentUrl();
		System.out.println("Current Page URL is: " + pageURL);
		return pageURL;
	}
	
	
	public String getPageURL(int waitTime, String urlContains) {
		boolean pageURL = getPageURLHas(waitTime,urlContains);
		return pageURL == true ? getPageURL() : null;
	}
	
	private boolean getPageURLHas(int waitTime, String urlContains)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
		boolean result = wait.until(ExpectedConditions.urlContains(urlContains));
		return result;
	}

	public void closeBrowser() {
		if (driver != null) {
			driver.close();
		}
	}

	public void quitBrowser() {
		if(driver != null) {
		driver.quit();
		}
	}
	
	public void navigateBack() {
		driver.navigate().back();
	}
	
	public void navigateForward() {
		driver.navigate().forward();
	}

	public void doRefresh() {
		driver.navigate().refresh();
	}
	
	public WebDriver switchToNewWindow() {
		driver = driver.switchTo().newWindow(WindowType.WINDOW);
		return driver;
	}
	
	public WebDriver switchToNewTab() {
		driver = driver.switchTo().newWindow(WindowType.TAB);
		return driver;
	}
}
