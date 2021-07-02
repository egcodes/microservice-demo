package com.egcodes.advertisement.enums;

public enum Category {
    PROPERTY("Emlak"),
    VEHICLE("Vasıta"),
    SHOPPING("Alışveriş"),
    OTHER("Diğer");

    private String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

}