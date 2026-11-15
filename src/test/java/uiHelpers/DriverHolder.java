package uiHelpers;

import org.openqa.selenium.WebDriver;

public class DriverHolder {

    private static final ThreadLocal<WebDriver> driver =
            new ThreadLocal<>();

    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void removeDriver() {
        driver.remove();
    }
}