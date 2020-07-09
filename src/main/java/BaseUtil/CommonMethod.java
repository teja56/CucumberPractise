package BaseUtil;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import RunnerClass.TestRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class CommonMethod extends TestRunner {

	public static WebDriver driver;
	public static InputStream inputStream;
	public static SessionId session;
	public List<String> list;
	public List<List<String>> excelSheetRows;
	public Workbook workBook = null;
	public File file;
	public static boolean driverClosed;
	public static byte[] SrcFile;
	public static Logger logger;
	public static WebElement myElement;
	public static Map<String, String> rowMap;
	public static List<Map<String, String>> rowMapList;

	public void Setup() throws IOException {
		logger = Logger.getLogger("ApplicationLog");
		openBrowser(prop.getProperty("browser"));
		driverClosed = false;
	}

	public void TearDown() {
		try {
			inputStream.close();
		} catch (IOException e) {
			System.out.println("Exception >> " + e.getMessage());
		} finally {
			driverClosed = true;
			driver.quit();
		}
	}

	public Properties getPropValues() {
		try {
			prop = new Properties();
			String propFileName = "configuration.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

		} catch (IOException e) {
			System.out.println("Exception: " + e);
		}
		return prop;
	}

	public static String getCellValueAsString(Cell cell) {
		String strCellValue = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case STRING:
				strCellValue = cell.toString();
				break;

			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					strCellValue = dateFormat.format(cell.getDateCellValue());
				} else {
					DataFormatter fmt = new DataFormatter();
					strCellValue = fmt.formatCellValue(cell);
				}
				break;

			case BOOLEAN:
				strCellValue = new String(new Boolean(cell.getBooleanCellValue()).toString());
				break;

			case BLANK:
				strCellValue = "";
				break;
				
			default:
				System.out.println("Not able to read cell data");
				break;
			}
		}
		return strCellValue;
	}

	public void getCellType(List<String> innerList, Row row, int j) {
		String cellValue = getCellValueAsString(row.getCell(j));
		innerList.add(cellValue);
	}

	public Workbook fileType(String filePath, String fileName) throws IOException {
		file = new File(filePath + "/" + fileName);
		inputStream = new FileInputStream(file);
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if (fileExtensionName.equals(".xlsx")) {
			workBook = new XSSFWorkbook(inputStream);
		} else if (fileExtensionName.equals(".xls")) {
			workBook = new HSSFWorkbook(inputStream);
		}
		return workBook;
	}

	public List<List<String>> readExcel(String filePath, String fileName, String sheetName) throws IOException {
		workBook = fileType(filePath, fileName);
		Sheet sheet = workBook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum() + 1;
		int columnCount = sheet.getRow(0).getLastCellNum();
		excelSheetRows = new LinkedList<>();
		for (int i = 1; i < rowCount; i++) {
			Row row = sheet.getRow(i);
			list = new LinkedList<>();
			for (int j = 0; j < columnCount; j++) {
				getCellType(list, row, j);
			}
			excelSheetRows.add(list);
		}
		inputStream.close();
		return excelSheetRows;
	}

	public List<Map<String, String>> listToMap(String filePath, String fileName, String sheetName) throws IOException {
		List<List<String>> myList = readExcel(filePath, fileName, sheetName);
		String key = null;
		String value = null;

		int rowSize = myList.size();
		rowMapList = new LinkedList<>();
		for (int i = 0; i < rowSize; i++) {
			int colSize = myList.get(i).size();
			rowMap = new HashMap<>();
			for (int j = 0; j < colSize; j++) {
				if ((i + 1) < rowSize) {
					key = myList.get(0).get(j).toString();
					value = myList.get(i + 1).get(j).toString();
					rowMap.put(key, value);
				}
			}
			if (i + 1 < rowSize) {
				rowMapList.add(rowMap);
			}
		}
		return rowMapList;
	}

	public byte[] visiblePageScreenshot() {
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		SrcFile = scrShot.getScreenshotAs(OutputType.BYTES);
		return SrcFile;
	}

	public byte[] fullPageScreenshot() throws IOException {
		Screenshot screenshot = new AShot()
				.shootingStrategy(ShootingStrategies.viewportPasting(ShootingStrategies.scaling(1.5f), 1500))
				.takeScreenshot(driver);
		BufferedImage image = screenshot.getImage();
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", byteArray);
		byteArray.flush();
		byte[] imageInByte = byteArray.toByteArray();
		byteArray.close();
		return imageInByte;
	}

	public File writeByte(byte[] bytes) throws IOException {
		FileOutputStream fileOuputStream = new FileOutputStream("filename");
		fileOuputStream.write(bytes);
		fileOuputStream.close();
		return file;
	}

	public WebElement highlightElement(WebElement webElement) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style','background: yellow; border: 2px solid red;');",
				webElement);
		return webElement;
	}

	public WebDriver openBrowser(String Browser) throws IOException {
		if (Browser.equals("FireFox")) {
			WebDriverManager.firefoxdriver().setup();
			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "target/Driverlogs.txt");
			driver = new FirefoxDriver();
			session = ((FirefoxDriver) driver).getSessionId();
			System.out.println("Session id is >>>>> " + session.toString());
		} else if (Browser.equals("Chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			driver = new ChromeDriver(options);
			session = ((ChromeDriver) driver).getSessionId();
			System.out.println("Session id is >>>>> " + session.toString());
		} else if (Browser.equals("IE")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			session = ((InternetExplorerDriver) driver).getSessionId();
			System.out.println("Session id is >>>>> " + session.toString());
		}
		return driver;
	}

	public WebElement waitForElement(By element, long timeout) {
		WebElement myElement = null;
		try {
			myElement = new WebDriverWait(driver, timeout).until(ExpectedConditions.presenceOfElementLocated(element));
		} catch (Exception e) {
			System.out.println("Exception Message : " + e.getMessage());
		} finally {
			if (myElement == null) {
				logger.info("Unable to find the WebElement in the web page by using its locator: " + element
						+ " within the timeout: " + timeout);
			}
		}
		return myElement;
	}

	public WebElement waitForElement(By element) {
		return waitForElement(element, 15);
	}

	public List<WebElement> waitForElements(By element, long timeout) {
		WebElement myElements = null;
		try {
			myElements = new WebDriverWait(driver, timeout).until(ExpectedConditions.presenceOfElementLocated(element));
		} catch (Exception e) {
			System.out.println("Exception Message : " + e.getMessage());
		} finally {
			if (myElement == null) {
				logger.info("Unable to find the WebElement in the web page by using its locator: " + element
						+ " within the timeout: " + timeout);
			}
		}
		return driver.findElements(element);
	}

	public List<WebElement> waitForElements(By element) {
		return waitForElements(element, 15);
	}

	public void click(WebElement webElement) {
		if (webElement != null) {
			webElement.click();
		}
	}

	public void clickJS(WebElement webElement) {
		if (webElement != null) {
			// String js = "arguments[0].style.height='auto';
			// arguments[0].style.visibility='visible';";
			try {
				String js = "arguments[0].click();";
				((JavascriptExecutor) driver).executeScript(js, webElement);
				webElement.click();
			} catch (org.openqa.selenium.StaleElementReferenceException ex) {

			} catch (org.openqa.selenium.ElementClickInterceptedException exe) {

			}
		}
	}

	public void clickCheck(WebElement webElement) {
		boolean staleElement = true;
		while (staleElement) {
			try {
				String js = "arguments[0].click();";
				((JavascriptExecutor) driver).executeScript(js, webElement);
				webElement.click();
				staleElement = false;
			} catch (StaleElementReferenceException e) {
				staleElement = true;

			}
		}
	}
	public void clickAction(WebElement webElement) {
		if (webElement != null) {
			Actions ob = new Actions(driver);
			ob.moveToElement(webElement);
			ob.click(webElement);
			Action action = ob.build();
			action.perform();
		}
	}

	public boolean isElementDisplayed(WebElement webElement) {
		if (webElement != null) {
			webElement.isDisplayed();
		}
		return false;
	}

	public void clearAndSendKeysToElement(WebElement webElement, String text) throws InterruptedException {
		if (webElement != null) {
			clearInputField(webElement);
			webElement.sendKeys(text);
		}
	}

	public void clearInputField(WebElement webElement) {
		if (webElement != null) {
			webElement.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			webElement.sendKeys(Keys.DELETE);
		}
	}

	public void SelectDropDownByText(WebElement webElement, String Value) {
		if (webElement != null) {
			Select select = new Select(webElement);
			select.selectByVisibleText(Value);
		}
	}
	
	public void SelectDropDownByValue(WebElement webElement, String Value) {
		if (webElement != null) {
			Select select = new Select(webElement);
			select.selectByValue(Value);
		}
	}

	public boolean isNullOrEmpty(String str) {
		if (str != null && !str.isEmpty())
			return false;
		return true;
	}

	public void waitPageLoad() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for (int i = 0; i < 25; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.info("Page Not Loaded within 25 seconds");
			}
			if (String.valueOf(js.executeScript("return document.readyState")).equals("complete")) {
				Thread.sleep(5000);
				break;
			}
		}
	}

	public void pageRefresh() {
		driver.navigate().refresh();
	}

	public String randomString(int n) {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}
	
	public static int generateRandomDigits(int n) {
	    int m = (int) Math.pow(10, n - 1);
	    return m + new Random().nextInt(9 * m);
	}

	public String getAttribute(WebElement element, String str) {
		String attributeValue = element.getAttribute(str);
		return attributeValue;
	}

	public String dateConversion(Date date, String dateFormat) {
		String DATE_FORMAT = dateFormat;
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String formattedDate = sdf.format(date); 		
		return formattedDate;
	}

	public boolean verifyTitle(String expectedTitle){
		return driver.getTitle().contains(expectedTitle);
	}
	
	public void VerifyText(WebElement element, String value) {
		Assert.assertEquals(element.getText(),value);
	}

}