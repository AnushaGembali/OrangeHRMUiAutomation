package com.orangeHRM.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangeHRM.constants.DashboardPageConstants;
import com.orangeHRM.constants.TimeOutConstants;
import com.orangeHRM.utils.BrowserUtil;
import com.orangeHRM.utils.ElementUtil;

public class DashboardPage {
	
	private WebDriver driver;
	private BrowserUtil browserUtil;
	private ElementUtil eleUtil;
	
	private By DashboardHeaderLocator = By.tagName("h6");
	private By UserNameDropDownLocator = By.className("oxd-userdropdown-name");
	private By DropDownMenuLocator = By.className("oxd-userdropdown-tab");
	private By DropDownMenuItemsLocator = By.className("oxd-userdropdown-link");
	
	private By AboutLinkLocator = By.linkText("About");
	private By AboutMenuItemHeader = By.cssSelector(".orangehrm-main-title");
	private By AboutMenuItemData = By.xpath("//p[contains(@class,'orangehrm-about')]");
	
	private By SupportLinkLocator = By.linkText("Support");
	private By ChangePasswordLinkLocator = By.linkText("Change Password");
	private By LogoutLinkLocator = By.linkText("Logout");
	
	public DashboardPage(WebDriver driver, BrowserUtil browserUtil) 
	{
		this.driver = driver;
		browserUtil = new BrowserUtil(driver);
		eleUtil = new ElementUtil(driver);
	}
	
	public String getDashboardPageUrl() 
	{
		String actualURL = browserUtil.getPageURL(TimeOutConstants.MEDIUM_TIMEOUT, DashboardPageConstants.DASHBOARD_PAGE_URL_HAS);
		return actualURL != null ? actualURL : "";
	}
	
	public String getDahsboardHeader() {
		String headerText = eleUtil.doGetElementText(DashboardHeaderLocator);
		System.out.println("Actual Dashboard header: " + headerText);
		return headerText;		
	}
	
	public String getUserNameFromMenu()
	{
		String userName = eleUtil.doGetElementText(UserNameDropDownLocator);
		System.out.println("Actual user name in the menu: " + userName);
		return userName;
	}
	
	public List<String> getDahsboardMenuItems()
	{
		eleUtil.doClick(DropDownMenuLocator);
		List<String> menuItemsList = eleUtil.doGetElementsText(DropDownMenuItemsLocator, TimeOutConstants.SMALL_TIMEOUT);
		System.out.println("Actual Menu Items: " + menuItemsList.toString());
		return menuItemsList;
	}
	
	public String clickOnAboutMenuItem() {
		eleUtil.doClick(DropDownMenuLocator);
		eleUtil.doClick(AboutLinkLocator);
		String title = eleUtil.doGetElementText(AboutMenuItemHeader, TimeOutConstants.MEDIUM_TIMEOUT );
		System.out.println("Actual About Menu Item Header: " + title);
		return title;
	}
	
	public Map<String,String> getDataFromAbutMenu() {
//		eleUtil.doClick(DropDownMenuLocator);
//		eleUtil.doClick(AboutLinkLocator);
		
//		eleUtil.doActionsClick(DropDownMenuLocator);
//		eleUtil.doActionsClick(AboutLinkLocator);

		List<String> dataInAboutMenuList = eleUtil.doGetElementsText(AboutMenuItemData, TimeOutConstants.MEDIUM_TIMEOUT);
		
		Map<String,String> dataInAboutMenu = new HashMap<>();
		for (int i = 0; i < dataInAboutMenuList.size() - 1; i += 2) {
			dataInAboutMenu.put(dataInAboutMenuList.get(i).trim(), dataInAboutMenuList.get(i + 1).trim());
		}
		
		System.out.println("Actual Data in the About menu item: " +  dataInAboutMenu);
		return dataInAboutMenu;
	}
	
	
	
	

}
