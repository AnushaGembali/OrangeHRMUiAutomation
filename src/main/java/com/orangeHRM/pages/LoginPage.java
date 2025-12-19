package com.orangeHRM.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.orangeHRM.constants.LoginPageConstants;
import com.orangeHRM.constants.TimeOutConstants;
import com.orangeHRM.utils.BrowserUtil;
import com.orangeHRM.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	private BrowserUtil browserUtil;
	
	private By userNameLocator = By.name("username");
	private By pswrdLocator = By.name("password");
	private By loginBtnLocator = By.xpath("//button[@type='submit']");
	private By forgotPswrdLinkLocator = By.cssSelector(".orangehrm-login-forgot-header");
	
	public LoginPage(WebDriver driver) 
	{
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
		browserUtil = new BrowserUtil(driver);
	}
	
	@Step("Logging in with userName:{0} and password:{1}")
	public DashboardPage doLogin(String userName, String password) 
	{
		StringBuilder nameOfUser = new StringBuilder(userName);	
		eleUtil.doSendKeys(userNameLocator, 2, nameOfUser);
		eleUtil.doSendKeys(pswrdLocator, password);
		eleUtil.doClick(loginBtnLocator);
		return new DashboardPage(driver, browserUtil);
	}
	
	public String loginPageTitle() 
	{
		String title = browserUtil.getPageTitle();
		return title;
	}
	
	public String loginPageUrl() 
	{
		String currentURL = browserUtil.getPageURL();
		return currentURL;
	}
	
	public boolean isForgotPswrdLinkExist() 
	{
		return eleUtil.doIsElementDisplayed(forgotPswrdLinkLocator);
	}

}
