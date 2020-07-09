package StepDefinition;

import java.io.IOException;

import BaseUtil.BaseClass;
import BaseUtil.CommonMethod;
import PageObjects.LoginPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginStep extends CommonMethod {

	public LoginPage loginToPage;
	public BaseClass baseClass = new BaseClass(driver);	

	public LoginStep() throws IOException {
		Setup();
		loginToPage = new LoginPage(driver);
	}

	@Given("User on the login Page")
	public void user_on_the_home_page() throws Throwable {
		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
		waitPageLoad();
		logger.info("Application opened in given Browser");
	}
	


	@When("User enters the username")
	public void he_enters_the_username_as() throws InterruptedException {
		clearAndSendKeysToElement(highlightElement(loginToPage.emailId()), prop.getProperty("userId"));
		logger.info("User Id entered");

	}

	@When("User enters the password")
	public void he_enters_the_password_as() throws InterruptedException {
		clearAndSendKeysToElement(highlightElement(loginToPage.password()), prop.getProperty("password"));
		logger.info("Password Entered");
	}

	@When("Click on Login button")
	public void click_on_Login_button() {
		click(highlightElement(loginToPage.loginButton()));
		logger.info("Login button clicked");
	}
	
		
	@Then("Verify Application is closed")
	public void verify_Application_is_closed() throws IOException, InterruptedException {
		waitPageLoad();
		TearDown();
		logger.info("Application is Closed");
	}

}