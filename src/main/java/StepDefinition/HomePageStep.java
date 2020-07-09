package StepDefinition;

import org.testng.Assert;


import BaseUtil.CommonMethod;
import PageObjects.HomePage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

public class HomePageStep extends CommonMethod { 
public HomePage homePage;
	
	
	public HomePageStep() {		
		homePage = new HomePage(driver);
		
	}
	
	  @When("^Logged into the application verify home page$")
	    public void logged_into_the_application_verify_home_page() throws Throwable {
		  waitPageLoad();
		  String title = driver.getTitle();
			System.out.println("home page title is : " + title);
			Assert.assertEquals(title, "Reports dashboard");
	    }
	  
	  @And("^Verify home page header$")
	    public void verify_home_page_header() throws Throwable {
			String header = highlightElement(homePage.header()).getText();

			System.out.println("home page header is : " + header);
			Assert.assertEquals(header, "Sales Dashboard");
	  }
	
}
