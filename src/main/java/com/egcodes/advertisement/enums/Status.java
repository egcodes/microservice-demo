package com.egcodes.advertisement.enums;

public enum Status {
    WAITING_FOR_APPROVAL("Onay Bekliyor"),
    ACTIVE("Aktif"),
    DEACTIVATE("Deaktif"),
    DUPLICATE("Mükerrer");

    private String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

}