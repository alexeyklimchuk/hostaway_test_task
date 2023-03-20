package com.hostaway.pages.advancedSearch;

import com.codeborne.selenide.SelenideElement;

public enum RoomsAndBeds {
    BEDS("Beds"),
    BEDROOMS("Bedrooms"),
    BATHROOMS("Bathrooms");

    private final String name;
    RoomsAndBeds(String _name) { name = _name; }

    public String getName() { return name; }
}