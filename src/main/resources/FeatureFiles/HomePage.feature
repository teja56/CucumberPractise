Feature: Home Page Verification 

Background: User logins to HubSpot Application 
	Given User on the login Page
	When User enters the username 
	And User enters the password 
	And Click on Login button 
	
Scenario: Verify Home Page
	When Logged into the application verify home page
	And Verify home page header
	And Verify Application is closed 
	
	