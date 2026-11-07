package uiHelpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final Info info = new Info();
    private final WebDriver driver;
    private final By errorLoginXpath = By.xpath(info.getErrorXpath());

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriverWait waiting() {
        return new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    public void successLoginUser() {
        driver.get(info.getSite());

        driver.findElement(By.id(info.getLogin()))
                .sendKeys(info.getUserName());
        driver.findElement(By.id(info.getPassField()))
                .sendKeys(info.getPassValue());
        driver.findElement(By.id(info.getLoginButton()))
                .click();

        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(info.getBackpackAddButton())));
    }

    public void failedLogin(String username, String password) {
        driver.get(info.getSite());

        driver.findElement(By.id(info.getLogin())).sendKeys(username);
        driver.findElement(By.id(info.getPassField())).sendKeys(password);

        driver.findElement(By.id(info.getLoginButton())).click();
    }

    public String getErrorText() {
        return driver.findElement(errorLoginXpath).getText();
    }

    public By xpathOfErrorLogin() {
        return By.xpath(info.getErrorXpath());
    }

    public WebElement findErrorLogin() {
        return driver.findElement(xpathOfErrorLogin());
    }

    public void waitForError() {
        waiting().until(
                ExpectedConditions.visibilityOfElementLocated(xpathOfErrorLogin())
        );
    }

}
