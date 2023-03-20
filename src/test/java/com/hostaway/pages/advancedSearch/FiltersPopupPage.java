package com.hostaway.pages.advancedSearch;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.hostaway.common.BasePage;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.util.*;

public class FiltersPopupPage
        extends BasePage<FiltersPopupPage> {

    private final SelenideElement popup = Selenide.element(".__modal");
    private final SelenideElement btnClosePopup = popup.$x(".//button[preceding-sibling::div[text()='Filters']]");
    private final SelenideElement btnClearAll = popup.$x(".//b[text()='Clear all']");
    private final SelenideElement btnApply = popup.$x(".//button[span[text()='Apply']]");
    private final SelenideElement fldPriceFrom = popup.$("input[placeholder='From']");
    private final SelenideElement fldPriceTo = popup.$("input[placeholder='To']");
    private final Map<Amenities, SelenideElement> amenities = getAmenities(Arrays.asList(Amenities.values()));
    private final Map<RoomsAndBeds, SelenideElement> roomsAndBeds = getRoomsAndBeds(
            Arrays.asList(RoomsAndBeds.BEDS, RoomsAndBeds.BEDROOMS, RoomsAndBeds.BATHROOMS)
    );

    @Override
    @Step("Wait till `Filters` popup is loaded")
    public FiltersPopupPage waitForContentLoaded() {
        popup.should(Condition.appear, defaultContentLoadTimeout);
        fldPriceFrom.should(Condition.appear, defaultTimeout);
        return this;
    }

    @Step("Assert elements are present on `Filters` popup")
    public FiltersPopupPage assertFiltersForm() {
        fldPriceFrom.should(Condition.visible);
        fldPriceTo.should(Condition.visible);
        btnApply.should(Condition.visible);
        btnClearAll.should(Condition.visible);
        amenities.values().forEach(element -> {
            element.should(Condition.visible);
        });
        roomsAndBeds.values().forEach(element -> {
            element.should(Condition.visible);
        });
        return this;
    }

    @Step("Assert `clear all` functionality on `Filters` popup")
    public FiltersPopupPage assertClearAll() {
        Random generator = new Random();
        Map.Entry[] entries = roomsAndBeds.entrySet().toArray(new Map.Entry[0]);
        RoomsAndBeds randomCategoryKey = (RoomsAndBeds) entries[generator.nextInt(entries.length)].getKey();
        entries = amenities.entrySet().toArray(new Map.Entry[0]);
        Amenities randomAmenityKey = (Amenities) entries[generator.nextInt(entries.length)].getKey();

        setCategoryValue(randomCategoryKey, 1);
        changeAmenitiesCheckboxState(randomAmenityKey, true);
        btnClearAll.click();
        getCategoryCounter(randomCategoryKey).shouldBe(Condition.text("0"));
        getAmenityCheckboxElement(randomAmenityKey).shouldNotBe(Condition.checked);
        return this;
    }

    @Step("Assert MIN ({min}) and MAX ({max}) values of categories on `Filters` popup")
    public FiltersPopupPage assertCategoriesLimitValues(int min, int max) {
        roomsAndBeds.forEach((category, element) -> {
            String desc = String.format("Assert category `%s` elements on `Filters` popup",
                    category.getName());

            setCategoryValue(category, max);
            Allure.step(desc, () -> {
                getCategoryCounter(category).shouldBe(Condition.text(String.valueOf(max)));
                getCategoryButtonMinus(category).shouldBe(Condition.enabled);
                getCategoryButtonPlus(category).shouldBe(Condition.disabled);
            });

            setCategoryValue(category, min);
            Allure.step(desc, () -> {
                getCategoryCounter(category).shouldBe(Condition.text(String.valueOf(min)));
                getCategoryButtonMinus(category).shouldBe(Condition.disabled);
                getCategoryButtonPlus(category).shouldBe(Condition.enabled);
            });
        });
        return this;
    }

    public FiltersPopupPage setCategoryValue(RoomsAndBeds category, int value) {
        String desc = String.format("Set value `%s` to category `%s` on `Filters` popup",
                value,
                category.getName());
        Allure.step(desc, () -> {
            int counterValue = Integer.parseInt(getCategoryCounter(category).text());
            if (counterValue > value) {
                SelenideElement btnMinus = getCategoryButtonMinus(category);
                for (int i = 0; i < (counterValue - value); i++) btnMinus.click();
            } else if (counterValue < value) {
                SelenideElement btnPlus = getCategoryButtonPlus(category);
                for (int i = 0; i < (value - counterValue); i++) btnPlus.click();
            }
        });
        return this;
    }

    public FiltersPopupPage changeAmenitiesCheckboxState(Amenities amenity, boolean state) {
        String desc = String.format("%s amenity `%s` on `Filters` popup",
                state ? "Select" : "Unselect",
                amenity.getName());
        Allure.step(desc, () -> {
            if (getAmenityCheckboxElement(amenity).isSelected() != state) amenities.get(amenity).click();
        });
        return this;
    }

    @Step("Close `Filters` popup without applying filters")
    public AdvancedSearchPage closeFiltersPopup() {
        btnClosePopup.click();
        popup.should(Condition.disappear);
        return Selenide.page(AdvancedSearchPage.class);
    }

    private Map<Amenities, SelenideElement> getAmenities(List<Amenities> names) {
        Map<Amenities, SelenideElement> checkboxes = new HashMap<>();
        names.forEach(name -> {
            checkboxes.put(
                    name,
                    popup.$x(String.format(".//label[input[@type='checkbox'] and span[text()='%s']]", name.getName()))
            );
        });
        return checkboxes;
    }

    private Map<RoomsAndBeds, SelenideElement> getRoomsAndBeds(List<RoomsAndBeds> names) {
        Map<RoomsAndBeds, SelenideElement> categories = new HashMap<>();
        names.forEach(name -> {
            categories.put(
                    name,
                    popup.$x(String.format(".//div[preceding-sibling::div[text()='%s']]", name.getName()))
            );
        });
        return categories;
    }

    private SelenideElement getCategoryCounter(RoomsAndBeds category) {
        return roomsAndBeds.get(category).$("span");
    }

    private SelenideElement getCategoryButtonMinus(RoomsAndBeds category) {
        return roomsAndBeds.get(category).$("button:first-child");
    }

    private SelenideElement getCategoryButtonPlus(RoomsAndBeds category) {
        return roomsAndBeds.get(category).$("button:last-child");
    }

    private SelenideElement getAmenityCheckboxElement(Amenities amenity) {
        return amenities.get(amenity).$("input");
    }
}