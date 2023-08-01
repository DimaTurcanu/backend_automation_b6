package ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Driver;

import java.util.List;

public class TGBackendPage {

    public TGBackendPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(css = "input[name='firstName']")
    public WebElement firstNameInput;

    @FindBy(css = "input[name='lastName']")
    public WebElement lastNameInput;

    @FindBy(css = "input[name='email']")
    public WebElement emailInput;

    @FindBy(css = "input[name='dob']")
    public WebElement dobInput;

    @FindBy(css = "button[type='submit']")
    public WebElement addButton;

    @FindBy(css = ".common_list__slsfj")
    public List<WebElement> namesAndLastNames;
}