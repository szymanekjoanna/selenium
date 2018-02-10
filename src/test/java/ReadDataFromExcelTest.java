import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
// Needed to make Selenium work with Excel
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class ReadDataFromExcelTest {

    private WebDriver driver;

    public void setUp(){
        //System.setProperty("webdriver.chrome.driver",CHROME_LOCATION);
        //driver = new ChromeDriver();
        System.setProperty("webdriver.gecko.driver",Util.FIREFOX_LOCATION);
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Util.WAIT_TIME, TimeUnit.SECONDS);
        driver.get("http://www.demo.guru99.com/V4/");
    }


    /**
     * SS1: Enter valid userid & password
     *          Expected: Login successful home page shown
     * SS2: Enter invalid userid & valid password
     * SS3: Enter valid userid & invalid password
     * SS4: Enter invalid userid & invalid password
     *          Expected: A pop-up “User or Password is not valid” is shown
     */
    @Test
    public void EnterValidUsernameAndValidPassword(String USERNAME, String PASSWORD) throws Throwable {
        setUp();
        driver.findElement(By.name("uid")).clear();
        driver.findElement(By.name("uid")).sendKeys(USERNAME);
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(PASSWORD);
        driver.findElement(By.name("btnLogin")).click();
        assertEquals(Util.EXPECT_TITLE,driver.getTitle());
        driver.quit();
    }

    @Test
    public void script() throws Exception {
        String[][] testData = Util.getDataFromExcel(Util.FILE_PATH,Util.SHEET_NAME, Util.TABLE_NAME);
        String actualTitle;
        String actualBoxtitle;

        for (int i = 0; i < testData.length; i++) {
            setUp();
            driver.findElement(By.name("uid")).clear();
            driver.findElement(By.name("uid")).sendKeys(testData[i][0]);
            driver.findElement(By.name("password")).clear();
            driver.findElement(By.name("password")).sendKeys(testData[i][1]);
            driver.findElement(By.name("btnLogin")).click();
            try{
                Alert alt = driver.switchTo().alert();
                actualBoxtitle = alt.getText();
                alt.accept();
                assertEquals(actualBoxtitle,Util.VALID_ALERT_TEXT);
            }
            catch (NoAlertPresentException Ex){
                actualTitle = driver.getTitle();
                assertEquals(actualTitle,Util.EXPECT_TITLE);
            }
            driver.close();
        }
    }
}
