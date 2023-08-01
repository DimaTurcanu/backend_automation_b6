package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.concurrent.TimeUnit;

public class Driver {

    private static WebDriver driver;

    private Driver(){}

    public static WebDriver getDriver(){
        if(driver == null){

            String browser = ConfigReader.getProperty("browser");

            switch (browser){
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup(); // Set up driver
                    driver = new FirefoxDriver(); // Launch a driver
                    break;
                case "safari":
                    WebDriverManager.getInstance(SafariDriver.class);
                    driver = new SafariDriver();
                    break;
                case "edge":
                    // TODO implement Microsoft Edge driver instantiation
                    break;
                default:
                    throw new IllegalStateException(browser + " browser does not match any case!!!");
            }

            driver.manage().window().maximize(); // Maximizes the window
            driver.manage().timeouts().implicitlyWait(Integer.parseInt(ConfigReader.getProperty("implicit_wait")), TimeUnit.SECONDS); // implicit wait
        }
        return driver;
    }


    public static void quitDriver(){
        if(driver != null){
            driver.manage().deleteAllCookies();
            driver.quit();
            driver = null;
        }
    }
}