import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Site {

    private final Info info = new Info();
    private WebDriver driver;
    private LoginPage login;
    private InventoryPage inventory;
    private CartPage cart;
    private final SetDriver settings = new SetDriver();


    @BeforeEach
    public void setUp() {
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

    @Test
    public void login() {

            login.successLoginUser();

            assertTrue(
                    inventory.isInventoryPageOpened()
            );
        }


    @Test
    public void addToCart() {

            login.successLoginUser();

            inventory.addingBackpack();

            inventory.waitForVisibility(cart.cartButtonClassName());

            assertAll(
                    () -> assertTrue(inventory.findRemoveForBackpack()
                            .isDisplayed()),

                    () -> assertTrue(cart.findCartButton()
                        .isDisplayed()),

                    () -> assertEquals(info.getOneItemInCart(), cart.findCartButton()
                            .getAttribute(info.getAriaLabel()))
            );

            cart.openCartPage();

            assertTrue(cart.isCartPageOpened());

            inventory.waitForVisibility(inventory.itemNames());

            assertEquals(info.getBackpackName(), inventory.findNameField().getText());

    }

    @Test
    public void deleteFromCart() {

        login.successLoginUser();

        inventory.addingBackpack();

        cart.openCartPage();

        assertTrue(cart.isCartPageOpened());

        inventory.waitForVisibility(inventory.removeBackpackFromCart());

        inventory.waitForClickable(inventory.removeBackpackFromCart());

        inventory.removingBackpack();

        inventory.waitForElementDisappear(
                inventory.removeBackpackFromCart()
        );

        inventory.waitForElementDisappear(
                cart.cartBadge()
        );

        assertTrue(cart.findCartButton()
                .getAttribute(info.getAriaLabel())
                .contains(info.getEmptyCart()));

        assertEquals(0, cart.getNumberOfItems());
    }

    @Test
    public void addTwoItemsInCart() {
        inventory.addTwoItemsInCart();

        assertAll(
                () -> assertTrue(cart.nameFields()
                        .get(0).getText().contains(info.getBackpackName())),
                () -> assertTrue(cart.nameFields()
                        .get(1).getText().contains(info.getBikeLightName())),
                () ->assertTrue(cart.findCartButton()
                        .getAttribute(info.getAriaLabel())
                        .contentEquals(info.getTwoItemsInCart())),
                () -> assertEquals(2, cart.getNumberOfItems())
        );
    }

    @Test
    public void addTwoRemoveOneFromCart() {

        inventory.addTwoItemsInCart();

        inventory.removingBikeLight();

        inventory.waitForElementDisappear(inventory.removeBikeLight());
        inventory.waiting().until(ExpectedConditions
                .numberOfElementsToBe(inventory.itemNames(), 1));

        assertEquals(info.getBackpackName(), inventory.findNameField().getText());
    }

    @Test
    public void sortingItemsByNameZtoA() {

        login.successLoginUser();

        List<String> first = inventory.getNames();

        List<String> expected = new ArrayList<>(first);
        expected.sort(Comparator.reverseOrder());

        inventory.sortingZtoA();

        List<String> actual = inventory.getNames();

        assertEquals(expected, actual);

    }

    @Test
    public void sortingItemsByNameAtoZ() {
        login.successLoginUser();

        List<String> first = inventory.getNames();

        List<String> expected = new ArrayList<>(first);
        expected.sort(Comparator.naturalOrder());

        inventory.sortingAtoZ();

        List<String> actual = inventory.getNames();

        assertEquals(expected, actual);
    }
;
    @Test
    public void sortingItemsByPriceLoHi() {

        login.successLoginUser();

        List<Double> first = inventory.getPrices();

        List<Double> expected = new ArrayList<>(first);
        expected.sort(Comparator.naturalOrder());

        inventory.sortingLoHi();

        List<Double> actual = inventory.getPrices();

        assertEquals(expected, actual);

    }

    @Test
    public void sortingItemsByPriceHiLo() {

        login.successLoginUser();

        List<Double> first = inventory.getPrices();

        List<Double> expected = new ArrayList<>(first);
        expected.sort(Comparator.reverseOrder());

        inventory.sortingHiLo();

        List<Double> actual = inventory.getPrices();

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

        login.waitForError();

        assertAll(
                () -> assertTrue(login.findErrorLogin().isDisplayed()),
                () -> assertEquals(error, login.getErrorText())
        );
    }

    @Test
    void userPath() {
        login.successLoginUser();

        inventory.addingBackpack();

        cart.openCartPage();

        inventory.waitForVisibility(inventory.itemNames());

        assertAll(
                () -> assertEquals(info.getBackpackName(), cart.getItemName()),
                () -> assertEquals(info.getPriceOfBackpack(), cart.getPrice()),
                () -> assertEquals(1, cart.getNumberOfItems())
        );

        inventory.removingBackpack();

        inventory.waitForElementDisappear(
                inventory.removeBackpackFromCart()
        );

        inventory.waitForElementDisappear(
                cart.cartBadge()
        );

        assertEquals(0, cart.getNumberOfItems());

    }
}

