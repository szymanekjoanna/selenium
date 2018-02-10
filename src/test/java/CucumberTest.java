import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;


/*        Test Steps
        1)  Go to http://www.demo.guru99.com/V4/
        2) Enter valid UserId
        3) Enter valid Password
        4) Click Login
        */

public class CucumberTest {

    private WebDriver driver;

    private final String USERNAME = "mngr117533";
    private final String PASSWORD = "dagatem";
    private final String FIREFOX_LOCATION = "./src/test/resources/drivers/geckodriver.exe";
    private final String CHROME_LOCATION = "./src/test/resources/drivers/chromedriver.exe";
    public static final String EXPECT_TITLE = "Guru99 Bank Manager HomePage";
    public static final int WAIT_TIME = 30;

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver",CHROME_LOCATION);
        driver = new ChromeDriver();
        //System.setProperty("webdriver.gecko.driver",FIREFOX_LOCATION);
        //driver = new FirefoxDriver();
        //driver.manage().window().maximize();
        driver.manage().timeouts()
                .implicitlyWait(WAIT_TIME, TimeUnit.SECONDS);

    }

    @When("^I am on http://www\\.demo\\.guru99\\.com/V4/$")
    public void i_am_on_http_www_demo_guru_com_V() throws Throwable {
        setUp();
        driver.get("http://www.demo.guru99.com/V4/");
    }

    @When("^I enter valid UserId$")
    public void i_enter_valid_UserId() throws Throwable {
        driver.findElement(By.name("uid")).clear();
        driver.findElement(By.name("uid")).sendKeys(USERNAME);
    }

    @When("^I enter valid Password$")
    public void i_enter_valid_Password() throws Throwable {
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(PASSWORD);
    }

    @When("^Click Login$")
    public void click_Login() throws Throwable {
        driver.findElement(By.name("btnLogin")).click();
    }

    @Then("^Login successful$")
    public void login_successful() throws Throwable {
        assertEquals(EXPECT_TITLE,driver.getTitle());
        driver.quit();
    }
}
