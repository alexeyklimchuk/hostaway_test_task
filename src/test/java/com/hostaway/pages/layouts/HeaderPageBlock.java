package com.hostaway.pages.layouts;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.hostaway.pages.allListings.AllListingsPage;
import com.hostaway.pages.search.SearchPage;
import io.qameta.allure.Step;

public interface HeaderPageBlock {

    SelenideElement pageTest = Selenide.element("a[href='/']");
    SelenideElement pageAllListings = Selenide.element("a[href='/all-listings']");

    @Step("Switch to `Search` page")
    default SearchPage switchToSearchPage() {
        pageTest.click();
        return Selenide.page(SearchPage.class)
                .waitForContentLoaded();
    }

    @Step("Switch to `All listings` page")
    default AllListingsPage switchToAllListingsPage() {
        pageAllListings.click();
        return Selenide.page(AllListingsPage.class)
                .waitForContentLoaded();
    }
}