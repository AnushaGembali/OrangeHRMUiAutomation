package com.orangeHRM.utils;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangeHRM.actiondriver.ActionDriver;

import io.qameta.allure.Step;

public class ElementUtil {
	
	private WebDriver driver;
	private Actions actions;
	private JavascriptExecutorUtil jsUtil;
	
	public ElementUtil(WebDriver driver){
		this.driver = driver;
		actions = new Actions(driver);
		jsUtil = new JavascriptExecutorUtil(driver);
	}

	private void checkElementHighlight(WebElement element) {
		if(Boolean.parseBoolean(ActionDriver.isHighLight)) {
			jsUtil.flashElement(element);
		}
	}
	
	@Step("Getting element with locator:{0}")
	public WebElement doGetElement(By locator) {
		WebElement element =  driver.findElement(locator);
		checkElementHighlight(element);
		return element;
		
	}
	
	@Step("Getting element with locator:{0} and with wait time:{1}")
	public WebElement doGetElement(By locator, int time) {
		WebElement element = doGetElementAfterVisisble(locator, time);	
		checkElementHighlight(element);
		return element;
	} 
	
	public List<WebElement> doGetElements(By locator){
		return driver.findElements(locator);
	}
	
	public List<WebElement> doGetElements(By locator, int time){
		return doGetElementsAfterVisisble(locator, time);
	}
	
	@Step("Sending value:{1} into input field with locator:{0}")
	public void doSendKeys(By locator, String value) {
		WebElement element = doGetElement(locator);
		element.clear();
		element.sendKeys(value);
	}
	
	@Step("Sending value:{1} into input field with locator:{0} and with wait time:{2}")
	public void doSendKeys(By locator, String value, int waitTime) {
		WebElement element = doGetElementAfterVisisble(locator, waitTime);
		element.clear();
		element.sendKeys(value);
	}
	
	@Step("Sending value:{1} into input field with locator:{0}")
	public void doSendKeys(By locator, CharSequence... value) {
		WebElement element = doGetElement(locator);
		element.clear();
		doGetElement(locator).sendKeys(value);
	}
	
	@Step("Sending value:{2} into input field with locator:{0} and with wait time:{1}")
	public void doSendKeys(By locator, int waitTime, CharSequence... value) {
		WebElement element = doGetElementAfterVisisble(locator, waitTime);
		element.clear();
		doGetElement(locator).sendKeys(value);
	}
	
	
	@Step("Clicking on element with Locator{0}")
	public void doClick(By locator) {
		doGetElement(locator).click();
	}
	
	@Step("Clicking on element with Locator{0} with wait time:{1}")
	public void doClick(By locator, int time) {
		doGetElementAfterVisisble(locator, time).click();
	}
	
	@Step("getting text of element with locator{0}")
	public String doGetElementText(By locator) {
		String text = doGetElement(locator).getText();
		if(text != null) {
			return text;
		}
		System.out.println("Element text is null: " + text);
		return null;		
	}
	
	public String doGetElementText(By locator, int time) {
		String text = doGetElementAfterVisisble(locator, time).getText();
		if(text != null) {
			return text;
		}
		System.out.println("Element text is null: " + text);
		return null;		
	}
	
	
	public boolean doIsElementDisplayed(By locator) {
		try {
			return doGetElement(locator).isDisplayed();
		}
		catch(NoSuchElementException e) {
			return false;	
		}
	}
	
	public boolean doIsElementDisplayed(By locator, int time) {
		try {
			return doGetElement(locator, time).isDisplayed();
		}
		catch(TimeoutException e) {
			return false;	
		}
	}
	
	public boolean doIsElementEnabled(By locator) {
		try {
			return doGetElement(locator).isEnabled();
		}
		catch(TimeoutException e) {
			return false;	
		}
	}
	
	public boolean doIsElementEnabled(By locator, int time) {
		try {
			return doGetElement(locator, time).isEnabled();
		}
		catch(TimeoutException e) {
			return false;	
		}
	}
	
	public void doClearTheInputField(By locator) {
		doGetElement(locator).clear();
	}
	
	public String doGetElementAttribute(By locator, String attName) {
		return doGetElement(locator).getDomAttribute(attName);
	}
	
	/**
	 * to get the text of elements
	 * @param locator
	 * @return
	 */
	
	public List<String> doGetElementsText(By locator){
		List<WebElement> eleList = doGetElements(locator);
		return eleList.stream()
				.map(e -> e.getText())
				.filter(str -> str.length()!=0)
				.collect(Collectors.toList());
	}
	
	public List<String> doGetElementsText(By locator, int time){
		List<WebElement> eleList = doGetElements(locator, time);
		return eleList.stream()
				.map(e -> e.getText())
				.filter(str -> str.length()!=0)
				.collect(Collectors.toList());
	}
	
	/**
	 * To Print the text of element
	 * @param locator
	 */
	
	public void doPrintElementsText(By locator){
		List<WebElement> eleList = doGetElements(locator);
		eleList.stream()
				.map(e -> e.getText())
				.filter(str -> str.length()!=0)
				.forEach(System.out::println);
	}
	
	public void doPrintElementsText(By locator, int time){
		List<WebElement> eleList = doGetElements(locator, time);
		eleList.stream()
				.map(e -> e.getText())
				.filter(str -> str.length()!=0)
				.forEach(System.out::println);
	}
	
	
	/**
	 * To get the count of elements
	 * @param locator
	 */
	
	public int doGetElementsListCount(By locator) {
		return doGetElements(locator).size();
	}
	
	public int doGetElementsListCount(By locator, int time) {
		return doGetElements(locator,time).size();
	}
	
	public void doSelectDropDownValueByVisibleText(By locator, String text) {
		Select select = doGetSelectWebElement(locator);
		select.selectByVisibleText(text);
	}
	
	public void doSelectDropDownValueByIndex(By locator, int index) {
		Select select = doGetSelectWebElement(locator);
		select.selectByIndex(index);;
	}
	
	public void doSelectDropDownValueByValue(By locator, String value) {
		Select select = doGetSelectWebElement(locator);
		select.selectByValue(value);
	}
	
	 public int doGetDropDownOptionsCount(By locator) {
		 Select select = doGetSelectWebElement(locator);
		 return select.getOptions().size();
	 }
	 
	 public List<String> doGetDropDownOptionsTextList(By locator){
		 	Select select = doGetSelectWebElement(locator);
			List<WebElement> eleList = select.getOptions();
			return eleList.stream()
					.map(e -> e.getText())
					.filter(str -> str.length()!=0)
					.collect(Collectors.toList());
	 }
	 
	 public void doPrintDropDownOptionsText(By locator) {
		 doGetDropDownOptionsTextList(locator).stream()
		 										.forEach(System.out::println);
		 
	 }
	 
	 public void doSelectDropDownValueWithoutSelect(By locator, String value) {
		 doSelectDropDownValue(doGetElements(locator), value);
	 }
	 
	 public void doSelectDropDownValueWithSelect(By locator, String value) {
		 Select select = doGetSelectWebElement(locator);
		 doSelectDropDownValue(select.getOptions(), value);
		 
	 }
	 
	 public void doSelectDropDownValue(List<WebElement> optionsList, String value) {
		 for (WebElement webElement : optionsList) {
				if(webElement.getText().equalsIgnoreCase(value)) {
					webElement.click();
					break;
				}
			}
	 }
	 
	 private Select doGetSelectWebElement(By locator) {
		 return new Select(doGetElement(locator));
	 }
	 
	 public void handleMultiMenu(By...locators) throws InterruptedException {
			 actions = new Actions(driver);
			
			for(By locator : locators) {
				actions.moveToElement(doGetElement(locator))
						.perform();
			} 		
			doGetElement(locators[locators.length -1]).click();
		}
	 
	 public void handleMultiMenu(String...locators) throws InterruptedException {
		 	actions = new Actions(driver);
			
			for(String locator : locators) {
				actions.moveToElement(doGetElement(By.xpath("//*[text()='"+ locator +"']")))
						.perform();
			} 		
			doGetElement(By.xpath("//*[text()='"+ locators[locators.length -1] +"']"));
		}
	 
	 public void doActionsClick(By locator) {
		 actions.click(doGetElement(locator)).perform();
	 }
	 
	 public void doActionsSendKeys(By locator, String text) {
		 actions.sendKeys(doGetElement(locator), text).perform();
	 }
	 
	 public void doActionsRightClick(By locator) {
		 actions.contextClick(doGetElement(locator)).perform();
	 }
	 
	 public void doActionsSendKeysWithPause(By locator, String text, long value) {
		 actions.sendKeys(doGetElement(locator), text)
		 		.pause(value)
		 		.perform();
	 }
	 
	 public void doActionsDragAndDrop(By sourceLocator, By targetLocator) {
		 actions.dragAndDrop(doGetElement(sourceLocator), doGetElement(targetLocator))
		 		.perform();
		 
//		 actions.clickAndHold(doGetElement(sourceLocator))
//		 		.moveToElement(doGetElement(targetLocator))
//		 		.release()
//		 		.build()
//		 		.perform();
	 }
	 
	 public void doRightClickUsingActions(By locator) {
		 actions.contextClick(doGetElement(locator))
		 		.perform();
	 }
	 
	 public void doScrollToPageTopUsingActions() {
		 actions.sendKeys(Keys.CONTROL)
		 		.sendKeys(Keys.HOME)
		 		.build()
		 		.perform();
	 }
	 
	 public void doScrollToPageBottonUsingActions() {
		 actions.sendKeys(Keys.CONTROL)
		 		.sendKeys(Keys.END)
		 		.build()
		 		.perform();
	 }
	 
	 public void doScrollToElementUsingActions(By locator) {
		 actions.scrollToElement(doGetElement(locator))
		 		.build()
		 		.perform();
	 }
	 
	 public void doScrollToPageUpUsingActions() {
		 actions.sendKeys(Keys.PAGE_UP)
		 		.perform();
	 }
	 
	 public void doScrollToPageDownUsingActions() {
		 actions.sendKeys(Keys.PAGE_DOWN)
		 		.perform();
	 }
	 
	 
	 /**
	  * An expectation for checking that an element is present on the DOM of a page. 
	  * 	This does not necessarily mean that the element is visible.
	  * @param locator
	  * @param time
	  * @return
	  */
	 public WebElement doGetElementAfterPresent(By locator, int time) {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		 return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	 }
	 
	 /**An expectation for checking that an element is present on the DOM of a page and visible.
	  * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
	  * default polling/interval time = 500ms
	  * @param locator
	  * @param time
	  * @return
	  */
	 
	 private WebElement doGetElementAfterVisisble(By locator, int time) {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		 return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	 }
	 
	 /**
	  * An expectation for checking that there is at least one element present on a web page.
	  * @param locator
	  * @param time
	  * @return
	  */
	 public List<WebElement> doGetElementsAfterPresent(By locator, int time) {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		 return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	 }
	 
	 
	 /**
	  * An expectation for checking that all elements present on the web page that match the locator are visible.
	  *  Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
	  * @param locator
	  * @param time
	  * @return
	  */
	 private List<WebElement> doGetElementsAfterVisisble(By locator, int time) {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		 return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	 }
	 
	 /**
	  * An expectation for checking an element is visible and enabled such that you can click it.
	  * @param locator
	  * @param time
	  */
	 public void waitForElementToBeClickable(By locator, int time) {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		 wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	 }
	 
	 /**
	  * An expectation for checking whether the given frame is available to switch to. 
	  * If the frame is available it switches the given driver to the specified frame.
	  * @param locator
	  * @param timeout
	  */
	 
	 public void waitForFrame(By locator, int timeout) {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	 }
	 
	 public void waitForFrame(WebElement locator, int timeout) {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	 }
	 
	 public void waitForFrame(int index, int timeout) {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
	 }

	 public void waitForFrame(String idOrName, int timeout) {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
	 }
	 
	 public boolean waitForNewWindowOrTab(int numOfWindows, int timeout) {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		 try {
			 return wait.until(ExpectedConditions.numberOfWindowsToBe(numOfWindows));
		 }
		 catch(TimeoutException e) {
			 System.out.println("Number of windows are not matched");
			 return false;
		 }
	 }
	 
	 public Alert waitForAlert(int waitTime ){
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
		 Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		 return alert;
	 }
	 
	 
	 public boolean isPageLoaded(int timeOut)
	 {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		 String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'")).toString();
		 return Boolean.parseBoolean(flag);
	 }

}

