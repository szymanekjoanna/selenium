import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class UseTestNG {
    private WebDriver driver; // Selenium control driver
    private String baseUrl; // baseUrl of website Guru99


    @DataProvider(name = "ExcelData")
    public Object[][] testData() throws Exception {
        return Util.getDataFromExcel(Util.FILE_PATH, Util.SHEET_NAME, Util.TABLE_NAME);
    }

    @BeforeMethod
    public void setUp() throws Exception {
        //System.setProperty("webdriver.chrome.driver",CHROME_LOCATION);
        //driver = new ChromeDriver();
        System.setProperty("webdriver.gecko.driver",Util.FIREFOX_LOCATION);
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Util.WAIT_TIME, TimeUnit.SECONDS);
        driver.get("http://www.demo.guru99.com/V4/");
    }

    @Test(dataProvider = "ExcelData")
    public void testCase04(String username, String password) throws Exception {
        String actualBoxMsg;
        driver.findElement(By.name("uid")).clear();
        driver.findElement(By.name("uid")).sendKeys(username);
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("btnLogin")).click();
        try {
            Alert alt = driver.switchTo().alert();
            actualBoxMsg = alt.getText();
            alt.accept();
            assertEquals(actualBoxMsg, Util.VALID_ALERT_TEXT);
        } catch (NoAlertPresentException Ex) {
            assertEquals(driver.getTitle(), Util.EXPECT_TITLE);
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        driver.quit();
    }
}