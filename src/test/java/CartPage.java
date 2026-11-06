import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {
    private final Info info = new Info();
    private final WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriverWait waiting() {
        return new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    public By goToCartPage() {
        return By.className(info.getCartButtonID());
    }

    public By cartButtonClassName() {
       return By.className(info.getCartButtonID());
    }

    public By itemNames() {
        return By.className(info.getInventoryNameField());
    }

    public String getItemName() {
        return driver.findElement(itemNames()).getText();
    }

    public List<WebElement> nameFields() {
        return driver.findElements(itemNames());
    }

    public WebElement findCartButton() {
        return driver.findElement(cartButtonClassName());
    }

    public void openCartPage() {
        driver.findElement(goToCartPage()).click();
    }

    public boolean isCartPageOpened() {
        return driver.getCurrentUrl().contains(info.getCartURL());
    }

    public By cartBadge() {
        return By.className(info.getCartBadge());
    }

    public WebElement findCartBadge() {
        return driver.findElement(cartBadge());
    }

    public By cartItems() {
        return By.xpath(info.getNumberOfItems());
    }

    public Double getPrice() {
        String price = driver.findElement(By.xpath(info.getInventoryPriceList()))
                .getText().replace("$", "");
        return Double.parseDouble(price);
    }

    public List<WebElement> findCartItems() {
        return driver.findElements(cartItems());
    }

    public int getNumberOfItems() {

        List<WebElement> items = findCartItems();

        return items.size();
    }

    public void waitForVisibility(By locator) {
        waiting().until(
                ExpectedConditions.visibilityOfElementLocated(locator));
    }

}
