package uiTests;

import io.qameta.allure.Allure;
import io.qameta.allure.AttachmentOptions;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import uiHelpers.DriverHolder;

import java.io.ByteArrayInputStream;

public class ScreenshotExtension
        implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(
            ExtensionContext context) throws Exception {

        if (context.getExecutionException().isPresent()) {

            WebDriver driver = DriverHolder.getDriver();
            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);

            Allure.attachment(
                    "Screenshot on failure",
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    AttachmentOptions.withFileExtension(".png")
            );


        }
    }
}