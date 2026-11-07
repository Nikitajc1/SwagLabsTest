package uiHelpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.Map;

public class SetDriver {

    public WebDriver setDriver() {
        ChromeOptions options = new ChromeOptions();
        boolean headless = Boolean.getBoolean("headless");

        options.setExperimentalOption("prefs", Map.of(
                "profile.password_manager_leak_detection", false,
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false
        ));

        if (headless) {
            options.addArguments("--headless=new");
        }

        return new ChromeDriver(options);
    }
}
