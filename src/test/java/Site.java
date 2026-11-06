import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Site {

    private final Info info = new Info();
    private WebDriver driver;
    private LoginPage login;
    private InventoryPage inventory;
    private CartPage cart;
    private final By itemNames = By.className(info.getInventoryNameField());


    @BeforeEach
    public void setUp() {
        SetDriver settings = new SetDriver();
        driver = settings.setDriver();

        login = new LoginPage(driver);
        inventory = new InventoryPage(driver);
        cart = new CartPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriverWait waiting() {
        return new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }


    @Test
    public void login() {

            login.successLoginUser();

            assertTrue(
                    driver.getCurrentUrl().contains(info.getInventoryURL())
            );
        }


    @Test
    public void addToCart() {

            login.successLoginUser();

            inventory.addingBackpack();

            waiting().until(ExpectedConditions.visibilityOfElementLocated(cart.cartButtonClassName()));

            assertAll(
                    () -> assertTrue(driver.findElement(By.id(info.getRemoveBackpackButton()))
                            .isDisplayed()),

                    () -> assertTrue(driver.findElement(cart.cartButtonClassName())
                        .isDisplayed()),

                    () -> assertTrue(driver.findElement(cart.cartButtonClassName())
                    .getAttribute(info.getAriaLabel()).contentEquals(info.getOneItemInCart()))
            );

            cart.openCartPage();

            assertTrue(cart.isCartPageOpened());

            waiting().until(ExpectedConditions.visibilityOfElementLocated(itemNames));

            waiting().until(ExpectedConditions.elementToBeClickable(itemNames));

            assertTrue(driver
                    .findElement(itemNames)
                    .getText().contentEquals(info.getBackpackName()));

            String expected = info.getBikeLightName();
            String actual = driver
                    .findElement(itemNames)
                    .getText();

            assertNotEquals(expected, actual);

    }

    @Test
    public void deleteFromCart() {

        login.successLoginUser();

        inventory.addingBackpack();

        cart.openCartPage();

        assertTrue(cart.isCartPageOpened());

        waiting().until(
                ExpectedConditions.visibilityOfElementLocated(inventory.removeBackpackFromCart())
        );

        waiting().until(
                ExpectedConditions.elementToBeClickable(inventory.removeBackpackFromCart())
        );

        inventory.removingBackpack();

        waiting().until(ExpectedConditions.attributeContains(
                cart.cartButtonClassName(),
                info.getAriaLabel(),
                info.getEmptyCart()
        ));

        waiting().until(ExpectedConditions.invisibilityOfElementLocated(
                inventory.removeBackpackFromCart()
        ));

        assertTrue(driver.findElement(cart.cartButtonClassName())
                .getAttribute(info.getAriaLabel())
                .contains(info.getEmptyCart()));
    }

    @Test
    public void addTwoItemsInCart() {
        inventory.addTwoItemsInCart();
    }

    @Test
    public void addTwoRemoveOneFromCart() {

        inventory.addTwoItemsInCart();

        inventory.removingBikeLight();

        waiting().until(ExpectedConditions
                .invisibilityOfElementLocated(inventory.removeBikeLight()));
        waiting().until(ExpectedConditions
                .numberOfElementsToBe(itemNames, 1));

        assertTrue(driver.findElement(itemNames).getText().contentEquals(info.getBackpackName()));

    }

    @Test
    public void sortingItemsByName() {

        login.successLoginUser();

        inventory.sortingZtoA();

        List<String> actual = inventory.getNames();

        List<String> expected = new ArrayList<>(actual);
        expected.sort(Comparator.reverseOrder());

        inventory.sortingAtoZ();

        List<String> actual2 = inventory.getNames();

        List<String> expected2 = new ArrayList<>(actual2);
        expected2.sort(Comparator.naturalOrder());

        assertAll(
                () -> assertEquals(expected, actual),
                () -> assertEquals(expected2, actual2)
        );

    }

    @Test
    public void sortingItemsByPriceLoHi() {

        login.successLoginUser();

        inventory.sortingLoHi();

        List<Double> actual = inventory.getPrices();

        List<Double> expected = new ArrayList<>(actual);
        expected.sort(Comparator.naturalOrder());

        assertEquals(expected, actual);

    }

    @Test
    public void sortingItemsByPriceHiLo() {

        login.successLoginUser();

        inventory.sortingHiLo();

        List<Double> actual = inventory.getPrices();

        List<Double> expected = new ArrayList<>(actual);
        expected.sort(Comparator.reverseOrder());

        assertEquals(expected, actual);

    }

    @ParameterizedTest
    @CsvSource({
            "wrong_user, secret_sauce, Epic sadface: Username and password do not match any user in this service",
            "standard_user, 123, Epic sadface: Username and password do not match any user in this service",
            "'', '', Epic sadface: Username is required"
    })
    void failLogin(String username, String password, String error) {
        login.failedLogin(username, password);

        assertTrue(driver.findElement(By.xpath(info.getErrorXpath())).isDisplayed());
        assertEquals(error, login.getErrorText());
    }
}

