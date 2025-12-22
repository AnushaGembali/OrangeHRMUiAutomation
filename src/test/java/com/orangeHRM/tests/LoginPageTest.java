package com.orangeHRM.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.orangeHRM.base.BaseTest;
import com.orangeHRM.constants.DashboardPageConstants;
import com.orangeHRM.constants.LoginPageConstants;
import com.orangeHRM.pages.DashboardPage;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("1 - Login Page")
@Feature("1.1 - Login Page Test")
@Story("1.1.1 - Login Page sanity test")
public class LoginPageTest extends BaseTest{
	
	@Description("Logging with valid credentials")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Anusha Bellala")
	@Test(priority=Short.MAX_VALUE, groups = "sanity")
	public void doLoginTest() {
		String userName = prop.getProperty("userName");
		String password = prop.getProperty("password");
		
		DashboardPage dashboardPage = loginPage.doLogin(userName, password);
		String actualHeader = dashboardPage.getDahsboardHeader();
		Assert.assertEquals(actualHeader.toLowerCase(), DashboardPageConstants.DASHBOARD_PAGE_HEADER.toLowerCase());
	}
	
	@Description("Verifying Login Page title")
	@Severity(SeverityLevel.MINOR)
	@Owner("Anusha Bellala")
	@Test
	public void loginPageTitleTest() {
		String actualTitle = loginPage.loginPageTitle();
		Assert.assertEquals(actualTitle, LoginPageConstants.LOGIN_PAGE_TITLE_IS);
	}
	
	@Description("Verifying Login Page URL")
	@Severity(SeverityLevel.MINOR)
	@Owner("Anusha Bellala")
	@Test
	public void loginPageURLTest() {
		String actualURL = loginPage.loginPageUrl();
		Assert.assertTrue(actualURL.contains(LoginPageConstants.LOGIN_PAGE_URL_HAS));
	}	
	
	@Description("Verifying Forgot Password exist in Login Page")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Anusha Bellala")
	@Test
	public void doesForgotPswrdLinkExist()
	{
		boolean actualResult = loginPage.isForgotPswrdLinkExist();
		Assert.assertTrue(actualResult);
	}

}
