package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.By.ByXPath;

import BaseUtil.BaseClass;
import BaseUtil.CommonMethod;

public class HomePage extends BaseClass {
	public HomePage(WebDriver driver) {
		super(driver);
	}

	public static CommonMethod commonMethod = new CommonMethod();

	private static final ByCssSelector header = new  ByCssSelector("h1.private-page__title");
	private static final ByCssSelector accountName = new  ByCssSelector("span.account-name ");
	
	
	
	private static final ByXPath mainContactsLink = new ByXPath("//*[@id='nav-primary-contacts-branch']");
	private static final ByXPath childContactsLink = new ByXPath("//*[@id='nav-secondary-contacts']");

	public WebElement header() {
		return commonMethod.waitForElement(header);
	}

	public WebElement accountName() {
		return commonMethod.waitForElement(accountName);
	}

	public WebElement mainContactsLink() {
		return commonMethod.waitForElement(mainContactsLink);
	}

	public WebElement childContactsLink() {
		return commonMethod.waitForElement(childContactsLink);
	}

	
	
}