package com.hostaway.pages.advancedSearch;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.hostaway.common.BasePage;
import com.hostaway.pages.layouts.HeaderPageBlock;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class AdvancedSearchPage
        extends BasePage<AdvancedSearchPage>
        implements HeaderPageBlock {

    private final SelenideElement btnFilter = $x("//button[span[text()='Filter']]");
    private final SelenideElement btnHideMap = $x("//button[span[text()='Hide map']]");

    @Override
    @Step("Wait till `Advanced Search` page is loaded")
    public AdvancedSearchPage waitForContentLoaded() {
        btnFilter.should(Condition.appear, defaultContentLoadTimeout);
        btnHideMap.should(Condition.appear, defaultContentLoadTimeout);
        return this;
    }

    @Step("Switch to `Filters` popup by pressing button `Filter`")
    public FiltersPopupPage advancedSearchByFilter() {
        btnFilter.click();
        return Selenide.page(FiltersPopupPage.class)
                .waitForContentLoaded()
                .takePageScreenShot();
    }
}