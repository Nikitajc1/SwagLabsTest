package uiTests;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import uiHelpers.Info;
import uiHelpers.InventoryPage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import uiHelpers.InventoryPage.*;
import uiHelpers.*;

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

        Assertions.assertTrue(cart.isCartPageOpened());

        inventory.waitForVisibility(inventory.removeBackpackFromCart());

        inventory.waitForClickable(inventory.removeBackpackFromCart());

        inventory.removingBackpack();

        inventory.waitForElementDisappear(
                inventory.removeBackpackFromCart()
        );

        inventory.waitForElementDisappear(
                cart.cartBadge()
        );

        Assertions.assertTrue(cart.findCartButton()
                .getAttribute(info.getAriaLabel())
                .contains(info.getEmptyCart()));

        Assertions.assertEquals(0, cart.getNumberOfItems());
    }

    @Test
    public void addTwoItemsInCart() {
        inventory.addTwoItemsInCart();

        assertAll(
                () -> Assertions.assertTrue(cart.nameFields()
                        .get(0).getText().contains(info.getBackpackName())),
                () -> Assertions.assertTrue(cart.nameFields()
                        .get(1).getText().contains(info.getBikeLightName())),
                () -> Assertions.assertTrue(cart.findCartButton()
                        .getAttribute(info.getAriaLabel())
                        .contentEquals(info.getTwoItemsInCart())),
                () -> Assertions.assertEquals(2, cart.getNumberOfItems())
        );
    }

    @Test
    public void addTwoRemoveOneFromCart() {

        inventory.addTwoItemsInCart();

        inventory.removingBikeLight();

        inventory.waitForElementDisappear(inventory.removeBikeLight());
        inventory.waiting().until(ExpectedConditions
                .numberOfElementsToBe(inventory.itemNames(), 1));

        Assertions.assertEquals(info.getBackpackName(), inventory.findNameField().getText());
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

        assertEquals(actual.getFirst(), inventory.highestPrice(actual));
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
                () -> Assertions.assertTrue(login.findErrorLogin().isDisplayed()),
                () -> Assertions.assertEquals(error, login.getErrorText())
        );
    }

    @Test
    void userPath() {
        login.successLoginUser();

        inventory.addingBackpack();

        cart.openCartPage();

        inventory.waitForVisibility(inventory.itemNames());

        assertAll(
                () -> Assertions.assertEquals(info.getBackpackName(), cart.getItemName()),
                () -> Assertions.assertEquals(info.getPriceOfBackpack(), cart.getPrice()),
                () -> Assertions.assertEquals(1, cart.getNumberOfItems())
        );

        inventory.removingBackpack();

        inventory.waitForElementDisappear(
                inventory.removeBackpackFromCart()
        );

        inventory.waitForElementDisappear(
                cart.cartBadge()
        );

        Assertions.assertEquals(0, cart.getNumberOfItems());

    }

    @ParameterizedTest
    @CsvSource({
            "0, Sauce Labs Backpack",
            "1, Sauce Labs Bike Light"
    })
    void checkNames(int index, String name) {
        login.successLoginUser();

        inventory.addTwoItemsInCart();
        cart.openCartPage();
        inventory.waiting().until(
                ExpectedConditions.numberOfElementsToBe(cart.itemNames(), 2)
        );

        List<WebElement> names = cart.nameFields();

        assertEquals(name, names.get(index).getText());
    }

    @Test
    void updateCart() {
        login.successLoginUser();
        inventory.addingBackpack();
        cart.openCartPage();

        driver.navigate().refresh();
        cart.waitForVisibility(cart.itemNames());

        Assertions.assertEquals(info.getBackpackName(), cart.getItemName());
    }

    @Test
    void openBurgerMenu() {
        login.successLoginUser();
        inventory.openingBurgerMenu();

        assertTrue(inventory.burgerMenuWrap().isDisplayed());
    }

    @Test
    void checkIfSliderPageIsOpened() {
        login.successLoginUser();
        inventory.openingBurgerMenu();
        inventory.waitForClickable(inventory.getDynamicSidebar());
        inventory.openDynamicSidebar();
        inventory.openSliderMenu();

        assertTrue(inventory.isSliderPageOpened());
    }

    @Test
    void checkDots() {
        login.successLoginUser();
        inventory.openingBurgerMenu();
        inventory.waitForClickable(inventory.getDynamicSidebar());
        inventory.openDynamicSidebar();
        inventory.openSliderMenu();
        inventory.waitForVisibility(inventory.getDots());

        List<WebElement> dots = inventory.dots();
        WebElement dot = dots.get(4);
        dot.click();

        assertAll(
                () -> assertEquals(info.getColorOfActiveDot(), dot.getCssValue("background-color")),
                () -> assertEquals("true", dot.getAttribute("aria-current").toString())
        );
    }
}

