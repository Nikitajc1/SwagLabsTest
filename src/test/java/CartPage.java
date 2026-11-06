import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    private final Info info = new Info();
    private final WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public By goToCartPage() {
        return By.className(info.getCartButtonID());
    }

    public By cartButtonClassName() {
       return By.className(info.getCartButtonID());
    }

    public void openCartPage() {
        driver.findElement(goToCartPage()).click();
    }

    public boolean isCartPageOpened() {
        return driver.getCurrentUrl().contains(info.getCartURL());
    }
}
