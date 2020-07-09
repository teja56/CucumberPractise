package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;

import BaseUtil.BaseClass;
import BaseUtil.CommonMethod;

public class LoginPage extends BaseClass {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public static CommonMethod commonMethod = new CommonMethod();

	private static final ByXPath emailID = new ByXPath("//*[@id='username']");
	private static final ByXPath password = new ByXPath("//*[@id='password']");
	private static final ByXPath loginButton = new ByXPath("//*[@id='loginBtn']");
	private static final ByXPath signUpLink = new ByXPath("//*[text()='Sign up']");
	private static final ByXPath loginErrorText = new ByXPath("//div[@class='private-alert__inner']");

	public WebElement emailId() {
		return commonMethod.waitForElement(emailID);
	}

	public WebElement password() {
		return commonMethod.waitForElement(password);
	}

	public WebElement loginButton() {
		return commonMethod.waitForElement(loginButton);
	}

	public WebElement signUpLink() {
		return commonMethod.waitForElement(signUpLink);
	}

	public WebElement loginErrorText() {
		return commonMethod.waitForElement(loginErrorText);
	}

}
