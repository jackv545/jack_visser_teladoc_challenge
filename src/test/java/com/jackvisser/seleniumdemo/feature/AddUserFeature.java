package com.jackvisser.seleniumdemo.feature;

import com.jackvisser.seleniumdemo.model.UserRole;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class AddUserFeature {
    //GIVEN I am on the user management page
    //WHEN I add a {userRole} user
    //THEN I see the user with the provided information in the table
    public static Stream<Arguments> scenarios() {
        return Stream.of( //userRole
                Arguments.of(UserRole.SALES),
                Arguments.of(UserRole.CUSTOMER),
                Arguments.of(UserRole.ADMIN)
        );
    }
}
