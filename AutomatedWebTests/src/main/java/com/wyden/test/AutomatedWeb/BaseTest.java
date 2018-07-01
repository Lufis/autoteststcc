package com.wyden.test.AutomatedWeb;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
	private WebDriver driver;

	@BeforeClass
	public void instanciarWebDriver() {
		String browser = System.getProperty("browser");
		if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:/WebDrivers/ChromeDriver/chromedriver.exe");
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--start-maximized");
			driver = new ChromeDriver(chromeOptions);
		} else if (browser.equals("iexplorer")) {
			System.setProperty("webdriver.ie.driver", "C:/WebDrivers/IEDriver/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		} else if (browser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", "C:/WebDrivers/GeckoDriver/geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://localhost:8090/nis");

	}

	@AfterClass
	public void finalizarWebDriver() {
		driver.quit();
	}

	public WebDriver getDriver() {
		return driver;
	}

}
