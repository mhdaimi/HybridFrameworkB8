package keywordLibrary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Keywords {
	static WebDriver browser;
	static String parentWindowhandle;
	static String message;
	
	public static void invokeKeyword(String methodName, String arg1, String arg2, String arg3) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = Keywords.class.getDeclaredMethod(methodName, String.class, String.class, String.class);
		method.invoke(Keywords.class, arg1, arg2, arg3);
	}
	
	public static void launchBrowser(String arg1, String arg2, String arg3) throws Exception {
		if(arg1.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver","C:\\Users\\mhdai\\Downloads\\chromedriver_win32_85\\chromedriver.exe"); 
			browser = new ChromeDriver();
		}
		else if(arg1.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver","C:\\Users\\mhdai\\Downloads\\geckodriver-v0.27.0-win64\\geckodriver.exe");
			browser = new FirefoxDriver();
		} else {
			throw new Exception("invalidBrowserName");
		}
		browser.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		browser.manage().window().maximize();
		browser.get(arg2);
		
	}
	
	public static void enterText(String locator, String arg2, String arg3) {
		if(locator.equals("id")) {
			browser.findElement(By.id(arg2)).sendKeys(arg3);
		} else if(locator.equals("name")) {
			browser.findElement(By.name(arg2)).sendKeys(arg3);
		} else if(locator.equals("xpath")) {
			browser.findElement(By.xpath(arg2)).sendKeys(arg3);
		} else if(locator.equals("css")) {
			browser.findElement(By.cssSelector(arg2)).sendKeys(arg3);
		}
	}
	
	
	public static void click(String locator, String arg2, String arg3) {
		if(locator.equals("id")) {
			browser.findElement(By.id(arg2)).click();
		} else if(locator.equals("name")) {
			browser.findElement(By.name(arg2)).click();
		} else if(locator.equals("xpath")) {
			browser.findElement(By.xpath(arg2)).click();
		} else if(locator.equals("css")) {
			browser.findElement(By.cssSelector(arg2)).click();
		}
		
	}
	
	public static void selectValue(String locator, String arg2, String arg3) {
		if(locator.equals("id")) {
			browser.findElement(By.id(arg2)).click();
		} else if(locator.equals("name")) {
			browser.findElement(By.name(arg2)).click();
		} else if(locator.equals("xpath")) {
			browser.findElement(By.xpath(arg2)).click();
		} else if(locator.equals("css")) {
			browser.findElement(By.cssSelector(arg2)).click();
		}
	}
	
	public static void selectDate(String locator, String arg2, String arg3) {
		String mmToMonthYear = getmonthYearInString(arg3);
		String monthYear = browser.findElement(By.cssSelector(".DayPicker-Month > div")).getText();
		while(! monthYear.equals(mmToMonthYear)) {
			browser.findElement(By.cssSelector(".DayPicker-NavButton--next")).click();
			monthYear = browser.findElement(By.cssSelector(".DayPicker-Month > div")).getText();
		}
		if(monthYear.equals(mmToMonthYear)) {
			browser.findElement(By.id("fare_" + arg3)).click();
		}
	}
	
	public static String getmonthYearInString(String inputDate) {
		HashMap<String, String> mmToMonth = new HashMap<String, String>();
		mmToMonth.put("01", "January");
		mmToMonth.put("02", "February");
		mmToMonth.put("03", "March");
		mmToMonth.put("04", "April");
		mmToMonth.put("05", "May");
		mmToMonth.put("06", "June");
		mmToMonth.put("07", "July");
		mmToMonth.put("08", "August");
		mmToMonth.put("09", "September");
		mmToMonth.put("10", "October");
		mmToMonth.put("11", "November");
		mmToMonth.put("12", "December");
		
		String mm = inputDate.substring(4, 6);
		String yr = inputDate.substring(0,4);
		String month = mmToMonth.get(mm);
		
		return month + " " + yr;
	}

	
	public static void getParentWindowhandle(String locator, String arg2, String arg3) {
		parentWindowhandle = browser.getWindowHandle();
	}

	
	public static void clickLink(String locator, String arg2, String arg3) {
		if(locator.equals("linkText")) {
			browser.findElement(By.linkText(arg2)).click();
		} else if(locator.equals("partialLinkText")) {
			browser.findElement(By.partialLinkText(arg2)).click();
		}
	}
	
	
	public static void waitForElement(String locator, String arg2, String arg3) {
		WebDriverWait wait = new WebDriverWait(browser, 10);
		
		if(locator.equals("linkText")) {
			wait.until(ExpectedConditions.elementToBeClickable(By.linkText(arg2)));
		} else if(locator.equals("id")) {
			wait.until(ExpectedConditions.elementToBeClickable(By.id(arg2)));
		} else if(locator.equals("xpath")) {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(arg2)));
		}
	}
	
	public static void switchToWindow(String locator, String arg2, String arg3) {
		Set<String> allWindows = browser.getWindowHandles();
		for (String window : allWindows) {
			if(! window.equals(parentWindowhandle)) {
				browser.switchTo().window(window);
				if(browser.getTitle().contains(arg2)) {
					System.out.println("Switched to window with title: " + arg2);
				}
			}
			
		}
	}
	
	public static void switchToParentWindow(String locator, String arg2, String arg3) {
		browser.switchTo().window(parentWindowhandle);
	}
	
	
	public static void fetchText(String locator, String arg2, String arg3) {
		if(locator.equals("id")) {
			message = browser.findElement(By.id(arg2)).getText();
		} else if(locator.equals("name")) {
			message = browser.findElement(By.name(arg2)).getText();
		} else if(locator.equals("xpath")) {
			message = browser.findElement(By.xpath(arg2)).getText();
		} else if(locator.equals("css")) {
			message = browser.findElement(By.cssSelector(arg2)).getText();
		}
	}
	
	public static void printMessage(String locator, String arg2, String arg3) {
		System.out.println("Message: " + message);
	}
	
	public static void closeBrowser(String locator, String arg2, String arg3) {
		browser.quit();
	}
	
	
	public static void selectFromDropDown(String locator, String arg2, String arg3) {
		//code here
	}
	
	public static void selectAValue1(String locator, String arg2, String arg3) {
		//code here
	}
	
	public static void selectAValue(String locator, String arg2, String arg3) {
		//code here
	}
	
	public static void blah() {
		
	}
}
