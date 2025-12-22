package com.orangeHRM.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.orangeHRM.base.BaseTest;
import com.orangeHRM.constants.DashboardPageConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("Dashboard Page testing")
@Feature("Meta data and data inside Dashborad Page testing")
@Story("Meta data and data inside Dashborad Page testing")
public class DashboardPageTest extends BaseTest{
	
	@Description("Set up for Dashboard Page")
	@BeforeClass(description = "Set up for Dashboard Page", alwaysRun = true)
	public void doDashboardPageSetup() {
		dashboardPage = loginPage.doLogin(prop.getProperty("userName"),prop.getProperty("password"));
	}
	
	@Description("Testing Dashboard header text")
	@Test(description = "Testing Dashboard header text")
	public void dashboardPageHeaderTest() {
		String actualHeader = dashboardPage.getDahsboardHeader();
		Assert.assertEquals(actualHeader.toLowerCase(), DashboardPageConstants.DASHBOARD_PAGE_HEADER.toLowerCase());
	}
	
	@Description("Test user Name in the drop down menu")
	@Test(description = "Test user Name in the drop down menu")
	public void userNameInMenuDropDownTest()
	{
		String actualUserName = dashboardPage.getUserNameFromMenu().toLowerCase();
		String expectedUserName = prop.getProperty("userName").toLowerCase();
		Assert.assertTrue(actualUserName.contains(expectedUserName));
	}
	
	@Description("Test user Name in the drop down menu")
	@Test(priority = Short.MAX_VALUE-2, groups = {"sanity", "regression"})
	public void menuDropDownList()
	{
		List<String> dropDownList = dashboardPage.getDahsboardMenuItems();
		Assert.assertEquals(dropDownList, DashboardPageConstants.DASHBORAD_MENU_ITEMS);
	}
	
	@Description("Validate About option is displayed in the menu")
	@Test(priority = Short.MAX_VALUE-1)
	public void aboutMenuItemIsDisplayedTest()
	{
		String actualHeader = dashboardPage.clickOnAboutMenuItem();
		Assert.assertEquals(actualHeader.trim(), DashboardPageConstants.ABOUT_MENU_ITEM_HEADER);
	}
	
	@Description("Validate the Data dispalyed in the About menu item")
	@Test(priority = Short.MAX_VALUE, dependsOnMethods = {"aboutMenuItemIsDisplayedTest","menuDropDownList"})
	public void aboutMenuItemDataTest()
	{
		Map<String,String> actualAboutMenuItemData = dashboardPage.getDataFromAbutMenu();
		
		List<String> dataInAboutMenuList = DashboardPageConstants.DATA_IN_ABOUT_MENU;
		Map<String,String> expectedAboutMenuItemData = new HashMap<>();
		for (int i = 0; i < dataInAboutMenuList.size() - 1; i += 2) {
			expectedAboutMenuItemData.put(dataInAboutMenuList.get(i).trim(), dataInAboutMenuList.get(i + 1).trim());
		}
		System.out.println("expected Data in the About menu item: " +  expectedAboutMenuItemData);							
		
		actualAboutMenuItemData.remove("Active Employees:");
		expectedAboutMenuItemData.remove("Active Employees:");
		
		for (String key : actualAboutMenuItemData.keySet()) {
			softAssert.assertEquals(
		    		actualAboutMenuItemData.get(key),
		    		expectedAboutMenuItemData.get(key),
		            "Mismatch found → key: " + key
		    );
		}
		softAssert.assertAll();
	}
	
	@Test(enabled = false, description = "=== this test should not run ===")
	public void verifyDisabledTestCase() {
		
	}

}
