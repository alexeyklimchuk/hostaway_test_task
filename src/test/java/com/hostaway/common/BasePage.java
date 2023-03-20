package com.hostaway.common;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import static com.codeborne.selenide.Selenide.$;

@SuppressWarnings({"unchecked", "UnusedReturnValue"})
public abstract class BasePage<PAGE> {

    public final Duration defaultTimeout = Duration.ofSeconds(5);
    public final Duration defaultContentLoadTimeout = Duration.ofSeconds(30);
    protected WebDriver driver;

    public BasePage() {
        driver = WebDriverRunner.getWebDriver();
    }

    protected abstract PAGE waitForContentLoaded();

    @Step("Wait loading sign disappeared")
    protected PAGE waitLoadingSignDisappear(Duration appearTimeout, Duration disappearTimeout) {
        SelenideElement loadingSign = $(".sc-tsGVs.hebieQ");
        Instant iterationStartedAt = Instant.now();
        do {
            if (loadingSign.has(Condition.visible)) break;
        } while(Instant.now().minus(appearTimeout).isBefore(iterationStartedAt));
        loadingSign.should(Condition.disappear, disappearTimeout);
        return (PAGE) this;
    }

    public PAGE takePageScreenShot() {
        screenshot();
        return (PAGE) this;
    }

    @Attachment(type = "image/png")
    private byte[] screenshot() {
        File file = Screenshots.takeScreenShotAsFile();
        try {
            assert file != null;
            BufferedImage image = ImageIO.read(file);
            ByteArrayOutputStream arrayOs = new ByteArrayOutputStream();
            BufferedOutputStream os = new BufferedOutputStream(arrayOs);
            image.flush();
            ImageIO.write(image, "png", os);
            return arrayOs.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}