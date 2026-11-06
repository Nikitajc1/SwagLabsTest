import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InventoryPage {

    private final Info info = new Info();
    private final WebDriver driver;
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriverWait waiting() {
        return new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    public void waitForElementDisappear(By locator) {
            waiting().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitForVisibility(By locator) {
        waiting().until(
                ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForClickable(By locator) {
        waiting().until(
                ExpectedConditions.elementToBeClickable(locator));
    }

    public By itemNames() {
        return By.className(info.getInventoryNameField());
    }

    public WebElement findNameField() {
        return driver.findElement(itemNames());
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

    public WebElement findRemoveForBackpack() {
       return driver.findElement(removeBackpackFromCart());
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

    public void addTwoItemsInCart() {
        LoginPage login = new LoginPage(driver);
        CartPage cart = new CartPage(driver);

        login.successLoginUser();

        driver.findElement(addBackpackToCart()).click();
        driver.findElement(addBikeLightToCart()).click();

        driver.findElement(cart.goToCartPage()).click();

        waiting().until(ExpectedConditions.numberOfElementsToBe(itemNames(), 2));
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

    public Double highestPrice(List<Double> prices) {

        return prices
                .stream()
                .max(Double::compareTo)
                .orElseThrow();
    }

    public List<String> getNames() {
        return driver
                .findElements(By.xpath(info.getInventoryNameList()))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public boolean isInventoryPageOpened() {
        return
                driver.getCurrentUrl().contains(info.getInventoryURL());
    }

}
