package com.jackvisser.seleniumdemo.model;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum UserRole {
    SALES("Sales Team"),
    CUSTOMER("Customer"),
    ADMIN("Admin");

    private final String uiLabel;

    UserRole(String uiLabel) {
        this.uiLabel = uiLabel;
    }

    public String getUiLabel() {
        return uiLabel;
    }

    public static UserRole ofUiLabel(String label) {
        return Arrays.stream(values())
                .filter(role -> role.uiLabel.equals(label))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("UserRole not found for UI label '%s'".formatted(label)));
    }
}
