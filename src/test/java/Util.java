import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.io.File;

public class Util {

    public final String USERNAME = "mngr117533";
    public final String INVALID_USERNAME = "essfeefsr";
    public final String PASSWORD = "dagatem";
    public final String INVALID_PASSWORD = "dagsdsdatem";
    public static final String FIREFOX_LOCATION = "./src/test/resources/drivers/geckodriver.exe";
    public static final String CHROME_LOCATION = "./src/test/resources/drivers/chromedriver.exe";
    public static final String EXPECT_TITLE = "Guru99 Bank Manager HomePage";
    public static final String VALID_ALERT_TEXT = "User or Password is not valid";
    public static final int WAIT_TIME = 30;

    public static final String FILE_PATH = "./src/test/resources/testData.xls"; // File Path
    public static final String SHEET_NAME = "Data"; // Sheet name
    public static final String TABLE_NAME = "testData"; // Name of data table


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
