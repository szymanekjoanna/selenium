package seleniumITestowanieAplikacji;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class GoogleSearchTestOnChrome {

    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver","./src/test/resources/drivers/chromedriver.exe");

        // Uruchom Chrome
        driver = new ChromeDriver();
        //Zmaksymalizuj okno przeglądarki
        driver.manage().window().maximize();
        //Przejdź do Google
        driver.get("http://www.google.com");
    }

    @Test
    public void testGoogleSearch() {
        // Znajdź element wprowadzania tekstu na podstawie jego nazwy
        WebElement element = driver.findElement(By.name("q"));

        // Wpisz informacje do wyszukania
        element.sendKeys("Testowanie z Selenium. Receptury");

        // Teraz prześlij formularz. WebDriver znajdzie formularz na podstawie elementu
        element.submit();

        // Strona wyszukiwania w Google jest dynamicznie renderowana za pomocą JavaScript.
        // Czekamy na załadowanie się strony. Timeout po 10 sekundach.
        new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("testowanie z selenium. receptury");
            }
        });

        assertEquals("Testowanie z Selenium. Receptury - Szukaj w Google",driver.getTitle());
    }

    @Test
    public void findLink(){
        WebElement gmialLink = driver.findElement(By.linkText("Gmail"));
        assertEquals("https://mail.google.com/mail/?tab=wm",gmialLink.getAttribute("href"));
    }

    @Test
    public void findLinks(){
        List<WebElement> links = driver.findElements(By.tagName("a"));
        for(WebElement link : links){
            //System.out.println(link.getText());
            System.out.println(link.getAttribute("href"));
        }
    }

    @Test
    public void partialLinkText(){
        WebElement inboxLink = driver.findElement(By.partialLinkText("Grafik"));
        System.out.println(inboxLink.getText());
    }

    @After
    public void tearDown() throws Exception {
        // Zamknij przeglądarkę
        driver.quit();
    }
}

