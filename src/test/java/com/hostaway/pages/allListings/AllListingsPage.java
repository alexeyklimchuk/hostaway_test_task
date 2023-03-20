package com.hostaway.pages.allListings;

import com.codeborne.selenide.*;
import com.hostaway.common.BasePage;
import com.hostaway.pages.layouts.HeaderPageBlock;
import io.qameta.allure.Step;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebElement;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static junit.framework.TestCase.assertEquals;

public class AllListingsPage
        extends BasePage<AllListingsPage>
        implements HeaderPageBlock {

    private final SelenideElement btnAll = $x("//div[h2[text()='Properties']]//span[@class]");

    @Override
    @Step("Wait till `All listings` page is loaded")
    public AllListingsPage waitForContentLoaded() {
        btnAll.should(Condition.appear, defaultContentLoadTimeout)
                .shouldNotHave(Condition.exactText("All"), defaultContentLoadTimeout);
        return this;
    }

    @Step("Assert amount of listings equals to 'All' label on `All Listings` page")
    public AllListingsPage assertAmountOfListings() {
        int expAmount = getPropertiesAmount();
        int actAmount = getDisplayedProperties().size();
        do {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
            waitLoadingSignDisappear(Duration.ofSeconds(2), defaultContentLoadTimeout);
            int currentAmount = getDisplayedProperties().size();
            if (actAmount == currentAmount) break;
            actAmount = currentAmount;
        } while (true);
        assertEquals("Amount of listings", expAmount, actAmount);
        return this;
    }

    private int getPropertiesAmount() {
        return Integer.parseInt(btnAll.text().replaceAll("\\D+",""));
    }

    private @NotNull ElementsCollection getDisplayedProperties() {
        List<RemoteWebElement> elements = (List<RemoteWebElement>)((JavascriptExecutor) driver).executeScript(
                "return document.querySelectorAll(\"a[href^='/listings/']\")"
        );
        return $$(elements);
    }
}