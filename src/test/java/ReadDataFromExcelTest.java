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

    private final String USERNAME = "mngr117533";
    private final String INVALID_USERNAME = "essfeefsr";
    private final String PASSWORD = "dagatem";
    private final String INVALID_PASSWORD = "dagsdsdatem";
    private final String FIREFOX_LOCATION = "./src/test/resources/drivers/geckodriver.exe";
    private final String CHROME_LOCATION = "./src/test/resources/drivers/chromedriver.exe";
    public static final String EXPECT_TITLE = "Guru99 Bank Manager HomePage";
    public static final String VALID_ALERT_TEXT = "User or Password is not valid";
    public static final int WAIT_TIME = 30;

    public static final String FILE_PATH = "./src/test/resources/testData.xls"; // File Path
    public static final String SHEET_NAME = "Data"; // Sheet name
    public static final String TABLE_NAME = "testData"; // Name of data table


    public void setUp(){
        //System.setProperty("webdriver.chrome.driver",CHROME_LOCATION);
        //driver = new ChromeDriver();
        System.setProperty("webdriver.gecko.driver",FIREFOX_LOCATION);
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(WAIT_TIME, TimeUnit.SECONDS);
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
        assertEquals(EXPECT_TITLE,driver.getTitle());
        driver.quit();
    }

    @Test
    public void script() throws Exception {
        String[][] testData = getDataFromExcel(FILE_PATH,SHEET_NAME, TABLE_NAME);
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
                assertEquals(actualBoxtitle,VALID_ALERT_TEXT);
            }
            catch (NoAlertPresentException Ex){
                actualTitle = driver.getTitle();
                assertEquals(actualTitle,EXPECT_TITLE);
            }
            driver.close();
        }
    }


    public static String[][] getDataFromExcel(String xlFilePath,
                                              String sheetName, String tableName) throws Exception {

        Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
        Sheet sheet = workbook.getSheet(sheetName);

        int startRow, startCol, endRow, endCol, ci, cj;

        Cell tableStart = sheet.findCell(tableName);
        startRow = tableStart.getRow();
        startCol = tableStart.getColumn();

        Cell tableEnd = sheet.findCell(tableName, startCol + 1, startRow + 1,
                100, 64000, false);
        endRow = tableEnd.getRow();
        endCol = tableEnd.getColumn();

        String[][] tabArray = new String[endRow - startRow - 1][endCol - startCol - 1];
        ci = 0;

        for (int i = startRow + 1; i < endRow; i++, ci++) {
            cj = 0;
            for (int j = startCol + 1; j < endCol; j++, cj++)
                tabArray[ci][cj] = sheet.getCell(j, i).getContents();
        }
        return (tabArray);
    }


}
