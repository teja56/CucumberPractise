package RunnerClass;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.codehaus.plexus.util.FileUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import cucumber.api.CucumberOptions;

@CucumberOptions(
			features = "src/main/resources/FeatureFiles", 
			glue = { "StepDefinition" }, 
			plugin = { 
						"html:target/cucumber-reports/htmlReport",
						"json:target/cucumber-reports/JsonReport/CucumberTestReport.json", 
						"testng:target/cucumber-reports/xmlReport/CucumberTestReport.xml",
						"rerun:target/rerun.txt",
			},
		    //dryRun = true,
			monochrome = true
			//tags = {"@Basic,@Login"}  // - Logical OR
			//tags = {"@Basic","@Login"}   //- Logical AND
			//if want to ignore tag execution, Use ~ sign in front of tag
		)
public class TestRunner extends BaseUtil.cucumber.api.testng.AbstractTestNGCucumberTests{
	
	public static BaseUtil.CommonMethod commonMethod = new BaseUtil.CommonMethod();;
	public static Properties prop;
	public static BaseUtil.StepData stepData;
	
	@BeforeSuite
	public static void loggerProperties() {
		String configFileName = "./src/main/resources/log4j.properties";
		PropertyConfigurator.configure(configFileName);
		prop = commonMethod.getPropValues();
		stepData = new BaseUtil.StepData();	
	}
	
	@AfterSuite
	public static void fileDeleted() throws IOException {
		FileUtils.deleteDirectory("./src/main/resources/FeatureFiles/temp/");
	}
}