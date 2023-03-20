package com.hostaway.tests;

import com.hostaway.common.BaseUiTest;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Epic("EPIC name")
@Feature("FEATURE name")
@Severity(SeverityLevel.NORMAL)
@Execution(ExecutionMode.CONCURRENT)
public class TestTaskTest extends BaseUiTest {

    @Test
    @Story("Filters")
    @DisplayName("Check Filters form")
    void checkFiltersForm() {
        portalPage.searchByFilter()
                .advancedSearchByFilter()
                .assertFiltersForm()
                .assertCategoriesLimitValues(0, 10)
                .assertClearAll()
                .closeFiltersPopup();
    }

    @Test
    @Story("All properties")
    @DisplayName("Check all properties is displayed on `All listings` page")
    void checkAllListingsPage() {
        portalPage.switchToAllListingsPage()
                .assertAmountOfListings();
    }
}