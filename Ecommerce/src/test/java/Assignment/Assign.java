package Assignment;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Assign {

	WebDriver driver;
	//Soft Assertion added
	SoftAssert SoftAssert=new SoftAssert();
	//Reports Creation started
	ExtentReports extents;
	ExtentSparkReporter spark;
	ExtentTest test1;
	@BeforeTest
	public void BT() {
		//System.setProperty("webdriver.chrome.driver", "C:\\selenium\\chromedriver.exe");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		 extents=new ExtentReports();
		 //Reports will be saved in the name extendReports.html
		 spark=new ExtentSparkReporter("extentReports.html");
		 //attached report
		 extents.attachReporter(spark);
	}

	@Test
	public void practiceEcommerceTest() throws InterruptedException
	{
		//Added some configuration to Reports Like Report theme,Report name and title.
		spark.config().setTheme(Theme.DARK);
		spark.config().setDocumentTitle("Ecommerce Report");
		spark.config().setReportName("Extent Automation report");
		
		//Created a test and add all logs in the given test
		
		 test1=extents.createTest("Ecommerce_Test");
		test1.pass("Land to Login Page");

		String title="Login - My Store";
		//Assertion to check title of the page.
		SoftAssert.assertEquals(driver.getTitle(), title);
		test1.pass("Title of page is captured");
		WebElement email_id=driver.findElement(By.id("email"));
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView()", email_id);
		js.executeScript("document.getElementById('email').value='asthajain80@gmail.com'");
		SoftAssert.assertTrue(email_id.isDisplayed());
		test1.info("user has entered username");
		WebElement password=driver.findElement(By.id("passwd"));
		js.executeScript("document.getElementById('passwd').value='asthajain13'");
		test1.info("user has entered password");
		SoftAssert.assertTrue(password.isDisplayed());
		SoftAssert.assertTrue(driver.findElement(By.id("SubmitLogin")).isEnabled());
		js.executeScript("document.getElementById('SubmitLogin').click()");
		test1.pass("Login Successfully");
		js.executeScript("document.getElementsByTagName('a')[10].click()");
		Thread.sleep(3000);
		js.executeScript("document.getElementsByClassName('product_list grid row')[0].scrollIntoView()");
		test1.info("page has been scrolled till product availability");
		String dressStatus=driver.findElement(By.className("available-now")).getText();
		System.out.println(dressStatus);
		SoftAssert.assertEquals("In stock",dressStatus);
	if(dressStatus.equals("In stock"))
	{
		js.executeScript("document.getElementsByClassName('button ajax_add_to_cart_button btn btn-default')[0].click()");
		test1.pass("Product added to cart successfully");
		js.executeScript("document.getElementsByClassName('btn btn-default button button-medium')[0].click()");
		test1.pass("product proceed to checkout");
		
	}
	else
	{
		driver.navigate().back();
	}
	

	}
	@AfterTest
	public void AfterTestExecute()
	{
		//to check all assertion its mandatory to add assertAll
		SoftAssert.assertAll();
		driver.close();
		test1.pass("browser get closed");
		//to erase previous data and generate new report
		extents.flush();
	
	}
}

