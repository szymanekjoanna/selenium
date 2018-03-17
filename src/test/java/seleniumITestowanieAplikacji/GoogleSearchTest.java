package seleniumITestowanieAplikacji;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.*;

import static org.junit.Assert.*;

public class GoogleSearchTest {

    private WebDriver driver;
    private final String FIREFOX_LOCATION = "./src/test/resources/drivers/geckodriver.exe";

    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver",FIREFOX_LOCATION);

        // Uruchom nowy egzemplarz przeglądarki Firefox
        driver = new FirefoxDriver();
        //Zmaksymalizuj okno przeglądarki
        driver.manage().window().maximize();
        //Przejdź do Google
        driver.get("http://www.google.com");
    }

    @Test
    public void testGoogleSearch() {
        // Znajdź element wprowadzania tekstu na podstawie jego nazwy
        WebElement element = driver.findElement(By.name("q"));
        // Wyczyść teskst zapisany w elemencie
        element.clear();

        // Wpisz informacje do wyszukania
        element.sendKeys("Testowanie z Selenium. Receptury");

        // Prześlij formularz
        element.submit();

        // Strona wyszukiwania w Google jest dynamicznie renderowana za pomocą JavaScript.
        // Czekamy na załadowanie się strony. Timeout po 10 sekundach.
        new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase()
                        .startsWith("testowanie z selenium. receptury");
            }
        });

        assertEquals("Testowanie z Selenium. Receptury - Szukaj w Google",
                driver.getTitle());
    }

    @After
    public void tearDown() throws Exception {
        // Zamknij przeglądarkę
        driver.close();
    }
}

