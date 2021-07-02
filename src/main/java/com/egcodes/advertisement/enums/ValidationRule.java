package com.egcodes.advertisement.enums;

import lombok.Getter;

@Getter
public enum ValidationRule {

    TITLE_NOT_ALLOWED("There are words that are not allowed in the title"),
    CATEGORY_NOT_ALLOWED("There is no such category"),
    UPDATE_DUPLICATE_ADS_NOT_ALLOWED("Duplicate records cannot be updated"),
    ADVERTISEMENT_NOT_FOUND("Advertisement not found"),
    ADVERTISEMENT_STATUS_NOT_SUITABLE("Status of the advertisement is not suitable for approval or deactivation");

    private final String error;

    ValidationRule(String error) {
        this.error = error;
    }
}
