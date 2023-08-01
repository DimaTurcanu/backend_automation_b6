package ui.scripts;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ui.pages.TGBackendPage;
import utils.Driver;

public class Base {

    public static WebDriver driver;
    public static TGBackendPage tgBackendPage;


    @BeforeMethod
    public void setUp(){
        driver = Driver.getDriver();
    }

    @AfterMethod
    public void tearDown(){
        Driver.quitDriver();
    }
}
