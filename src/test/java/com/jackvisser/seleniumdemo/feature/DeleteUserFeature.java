package com.jackvisser.seleniumdemo.feature;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class DeleteUserFeature {
    //GIVEN a user with username {username} is present in the user table
    //WHEN I delete {username}
    //THEN I should not see {username} in the table
    public static Stream<Arguments> scenarios() {
        return Stream.of( //username
                Arguments.of("novak")
        );
    }
}
