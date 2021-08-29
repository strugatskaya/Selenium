
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class AlphaBankTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestAllFieldsIsCorrectPositive1() {
        List<WebElement> textField = driver.findElements(By.className("input__control"));
        textField.get(0).sendKeys("Понд Эми");
        textField.get(1).sendKeys("+39999999999");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldTestPositiveAllFieldsIsCorrectWithDashPositive() {
        List<WebElement> textField = driver.findElements(By.className("input__control"));
        textField.get(0).sendKeys("Клара Освин-Освальд");
        textField.get(1).sendKeys("+70000000000");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldTestWithLatinLettersNameNegative() {
        List<WebElement> textField = driver.findElements(By.className("input__control"));
        textField.get(0).sendKeys("The Doctor");
        textField.get(1).sendKeys("+00000000000");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldTestWithTenTelNumbersNegative() {
        driver.findElement(By.cssSelector("input[name=name]")).sendKeys("Доктор Тардис");
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("+7999999999");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().trim();
        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());

    }

    @Test
    void shouldTestWithNumbersInNameNegative() {
        driver.findElement(By.cssSelector("input[name=name]")).sendKeys("Понд Эми6");
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("+10000000000");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().trim();
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldTestWithNoPlusIntelNegative() {
        driver.findElement(By.cssSelector("input[name=name]")).sendKeys("Понд Эми");
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("39999999999");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().trim();
        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldTestWithNoNameNegative() {
        driver.findElement(By.cssSelector("input[name=name]")).sendKeys("");
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("+39999999999");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().trim();
        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldTestWithNoTelNegative() {
        driver.findElement(By.cssSelector("input[name=name]")).sendKeys("Эми Понд");
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().trim();
        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldTestWithNumbersInsteadNameNegative() {
        driver.findElement(By.cssSelector("input[name=name]")).sendKeys("+0000000000");
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("+0000000000");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().trim();
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());

    }

    @Test
    void shouldTestWithLettersInsteadOfTelNegative() {
        driver.findElement(By.cssSelector("input[name=name]")).sendKeys("Арагорн Элессар");
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("Арагорн Элессар");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().trim();
        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());

    }

    @Test
    void shouldTestTwelveNumbersInTesNegative() {
        driver.findElement(By.cssSelector("input[name=name]")).sendKeys("Арагорн");
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("+399999999999");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().trim();
        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldTestSpacesInsteadOfNameNegative() {
        driver.findElement(By.cssSelector("input[name=name]")).sendKeys("   ");
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("+70000000000");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().trim();
        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());

    }

    @Test
    void shouldTestSpacesInsteadOfTelNegative() {
        driver.findElement(By.cssSelector("input[name=name]")).sendKeys("Том Бомбадил");
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("     ");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().trim();
        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());

    }

    @Test
    void shouldTestWithoutCheckBox() {
        driver.findElement(By.cssSelector("input[name=name]")).sendKeys("Галадриэль Лотлориэн");
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("+79236459245");
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText().trim();
        String expectedText = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        Assertions.assertEquals(expectedText, actualText);
    }
}


