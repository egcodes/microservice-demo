package com.egcodes.advertisement.exception;

import com.egcodes.advertisement.enums.ValidationRule;

public class AdvertisementException extends RuntimeException {

    public AdvertisementException(ValidationRule rule) {
        super(rule.getError());
    }

}
