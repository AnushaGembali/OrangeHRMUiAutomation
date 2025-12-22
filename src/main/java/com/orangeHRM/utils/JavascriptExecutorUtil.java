package com.orangeHRM.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavascriptExecutorUtil {

	private JavascriptExecutor js;
	
	JavascriptExecutorUtil(WebDriver driver){
		js = (JavascriptExecutor) driver;
	}
	
	public String getTitleUsingJs() {
		return js.executeScript("return document.title;").toString();
	}
	
	public String getURLUsingJs() {
		return js.executeScript("return document.URL;").toString();
	}
	

	public String getPageTextUsingJs() {
		return js.executeScript("return document.documentElement.innerText;")
				.toString();
	}

	public void generateAlertUsingJs(String message) {
		js.executeScript("alert('" + message + "');");
	}
	
	public void pageRefreshUsingJs() {
		js.executeScript("history.go(0);");
	}
	
	public void goBackUsingJs() {
		js.executeScript("history.go(-1);");
	}
	
	public void goForwardUsingJs() {
		js.executeScript("history.go(1);");
	}
	
	/**
	 * Example document.body.style.zoom='200.0%'
	 * @param zoomPercent
	 */
	
	public void doZoomUsingJs(String zoomPercent) {
		js.executeScript("document.body.style.zoom='" + zoomPercent + "';");
	}
	
	/**
	 * Example document.body.style.zoom='scale(0.5)'
	 * @param zoomPercent
	 */
	public void doFirefoxZoomUsingJs(String zoomPercent) {
		js.executeScript("document.body.style.zoom='scale(" + zoomPercent + ")';");
	}
	
	public void scrollToPageBottomUsingJs() {
		js.executeScript("window.scrollTo(0,document.body.scrollHeight);");
	}
	
	public void scrollToPageTopUsingJs() {
		js.executeScript("window.scrollTo(document.body.scrollHeight,0);");
	}
	
	public void scrollToHeightUsingJs(int height) {
		js.executeScript("window.scrollTo(0," + height + ");");
	}
	
	public void scrollToPageMiddileUsingJs() {
		js.executeScript("window.scrollTo(0,document.body.scrollHeight/2);");
	}
	
	public void scrollToElementUsingJs(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public void drawBorderUsingJs(WebElement element) {
		js.executeScript("arguments[0].style.border= '3px solid red'", element);
	}
	
	public void changeColorUsingJs(WebElement element, String color){
		js.executeScript( "arguments[0].style.backgroundColor = '" + color + "'", element);
	}
	
	public void flashElement(WebElement element)  {
		String backgroundColorOfEle = element.getCssValue("backgroundColor");
		drawBorderUsingJs(element);
		for(int i = 0; i < 30; i++) {
			changeColorUsingJs(element, "rgb(0,200,0)") ; //Green
			changeColorUsingJs(element, backgroundColorOfEle);
		}
	}
	
}