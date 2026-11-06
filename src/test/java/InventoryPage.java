import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventoryPage {

    private final Info info = new Info();
    private final WebDriver driver;
    private final By itemNames = By.className(info.getInventoryNameField());

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public By addBackpackToCart() {
        return By.id(info.getBackpackAddButton());
    }

    public void addingBackpack() {
        driver.findElement(addBackpackToCart()).click();
    }

    public By removeBackpackFromCart() {
        return By.id(info.getRemoveBackpackButton());
    }

    public void removingBackpack() {
        driver.findElement(removeBackpackFromCart()).click();
    }

    public By addBikeLightToCart() {
        return By.id(info.getBikeLightAddButton());
    }

    public By removeBikeLight() {return By.id(info.getRemoveBikeLight());}

    public void removingBikeLight() {
        driver.findElement(removeBikeLight()).click();
    }

    public WebDriverWait waiting() {
        return new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    public void addTwoItemsInCart() {
        LoginPage login = new LoginPage(driver);
        CartPage cart = new CartPage(driver);

        login.successLoginUser();

        driver.findElement(addBackpackToCart()).click();
        driver.findElement(addBikeLightToCart()).click();

        driver.findElement(cart.goToCartPage()).click();

        waiting().until(ExpectedConditions.numberOfElementsToBe(itemNames, 2));

        assertAll(
                () -> assertTrue(driver.findElements(itemNames)
                .get(0).getText().contains(info.getBackpackName())),
                () -> assertTrue(driver.findElements(itemNames)
                .get(1).getText().contains(info.getBikeLightName())),
                () ->assertTrue(driver.findElement(cart.cartButtonClassName())
                .getAttribute(info.getAriaLabel())
                .contentEquals(info.getTwoItemsInCart()))
        );

    }

    public By sortAtoZ() {
        return By.xpath(info.getSortingAtoZ());
    }

    public void sortingAtoZ() {
        driver.findElement(sortAtoZ()).click();
    }

    public By sortZtoA() {
        return By.xpath(info.getSortingZtoA());
    }

    public void sortingZtoA() {
        driver.findElement(sortZtoA()).click();
    }

    public By sortLoHi() {
        return By.xpath(info.getSortingLoHi());
    }

    public void sortingLoHi() {
        driver.findElement(sortLoHi()).click();
    }

    public By sortHiLo() {
        return By.xpath(info.getSortingHiLo());
    }

    public void sortingHiLo() {
        driver.findElement(sortHiLo()).click();
    }

    public List<Double> getPrices() {
        List<WebElement> elements = driver.findElements(By.xpath(info.getInventoryPriceList()));

        List<String> price = new ArrayList<>();

        for(WebElement element : elements) {
            price.add(element.getText());
        }

        List<Double> numericPrices = price.stream()
                .map(prices -> Double.parseDouble(prices.replace("$", "")))
                .toList();

        return numericPrices;
    }

    public List<String> getNames() {
        return driver
                .findElements(By.xpath(info.getInventoryNameList()))
                .stream()
                .map(WebElement::getText)
                .toList();
    }


}
