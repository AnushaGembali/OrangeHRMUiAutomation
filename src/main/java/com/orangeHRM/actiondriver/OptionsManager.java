package com.orangeHRM.actiondriver;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {
	
	private Properties prop;
	private boolean headless;
	private boolean incognito;
	private boolean runInRemote;
	
	public OptionsManager(Properties prop)
	{
		this.prop = prop;
		headless = Boolean.parseBoolean(prop.getProperty("headless").trim());
		incognito = Boolean.parseBoolean(prop.getProperty("incognito").trim());
		runInRemote = Boolean.parseBoolean(prop.getProperty("remote").trim());
	}
	
	public ChromeOptions getChromeOptions() {
		
		ChromeOptions co = new ChromeOptions();
		if(headless)
		{
			co.addArguments("--headless");
			System.out.println("====== Running in Headless Mode ======");
		}

		if(incognito)
		{
			co.addArguments("--incognito");
			System.out.println("====== Running in Incognito Mode ======");
		}
		
		if(runInRemote)
		{
			co.setCapability("browserName", "chrome");
			co.setBrowserVersion(prop.getProperty("browserVersion").trim());
			
			Map<String,Object> selenoidOptions = new HashMap<>();
			selenoidOptions.put("enableVNC", true);
			selenoidOptions.put("screenResolution", "1920x1080x24");
			selenoidOptions.put("name", prop.getProperty("testName"));
			
			co.setCapability("selenoid:options", selenoidOptions);
		}	
		return co;
	}
	
	public FirefoxOptions getFirefoxOptions() {
		
		FirefoxOptions fo = new FirefoxOptions();
		if(headless)
		{
			fo.addArguments("--headless");
			System.out.println("====== Running in Headless Mode ======");
		}

		if(incognito)
		{
			fo.addArguments("--incognito");
			System.out.println("====== Running in Incognito Mode ======");
		}
		
		if(runInRemote)
		{
			fo.setCapability("browserName", "firefox");
			fo.setBrowserVersion(prop.getProperty("browserVersion").trim());
			
			Map<String,Object> selenoidOptions = new HashMap<>();
			selenoidOptions.put("enableVNC", true);
			selenoidOptions.put("screenResolution", "1920x1080x24");
			selenoidOptions.put("name", prop.getProperty("testName"));
			
			fo.setCapability("selenoid:options", selenoidOptions);
		}	
		return fo;	
	}
	
	public EdgeOptions getEdgeOptions() {
		
		EdgeOptions eo = new EdgeOptions();
		
		if(headless)
		{
			eo.addArguments("--headless");
			System.out.println("====== Running in Headless Mode ======");
		}
		
		if(incognito)
		{
			eo.addArguments("--inPrivate");
			System.out.println("====== Running in Incognito Mode ======");
		}	
		if(runInRemote)
		{
			eo.setCapability("browserName", "edge");
			eo.setBrowserVersion(prop.getProperty("browserVersion").trim());
			
			Map<String,Object> selenoidOptions = new HashMap<>();
			selenoidOptions.put("enableVNC", true);
			selenoidOptions.put("screenResolution", "1920*1080*24");
			selenoidOptions.put("name", prop.getProperty("testName"));
			
			eo.setCapability("selenoid:options", selenoidOptions);
		}
		
		return eo;	
	}
}
