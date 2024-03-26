package com.jackvisser.seleniumdemo.model;

public record User(
        String firstName,
        String lastName,
        String username,
        UserRole role,
        String email,
        String cellPhone
) {}
