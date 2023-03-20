package com.hostaway.pages.search;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.hostaway.common.BasePage;
import com.hostaway.pages.layouts.HeaderPageBlock;
import com.hostaway.pages.advancedSearch.AdvancedSearchPage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class SearchPage
        extends BasePage<SearchPage>
        implements HeaderPageBlock {

    private final SelenideElement btnSearch = $x("//button[span[text()='Search']]");

    @Override
    @Step("Wait till `Search` page is loaded")
    public SearchPage waitForContentLoaded() {
        btnSearch.should(Condition.appear, defaultContentLoadTimeout);
        return this;
    }

    @Step("Switch to `Advanced Search` page by pressing button `Search`")
    public AdvancedSearchPage searchByFilter() {
        btnSearch.click();
        return Selenide.page(AdvancedSearchPage.class)
                .waitForContentLoaded();
    }
}