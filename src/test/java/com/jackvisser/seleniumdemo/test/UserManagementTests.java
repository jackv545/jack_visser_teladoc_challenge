package com.jackvisser.seleniumdemo.test;

import com.jackvisser.seleniumdemo.comonent.UserRow;
import com.jackvisser.seleniumdemo.framework.TestConfig;
import com.jackvisser.seleniumdemo.model.User;
import com.jackvisser.seleniumdemo.model.UserRole;
import com.jackvisser.seleniumdemo.page.UserManagementPage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = TestConfig.class)
public class UserManagementTests {
    @Autowired
    private TestConfig testConfig;

    private WebDriver driver;
    private UserManagementPage userManagementPage;

    @BeforeAll
    static void setupBrowser() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.get(testConfig.getDemoPageHost());
        userManagementPage = new UserManagementPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @ParameterizedTest
    @MethodSource("com.jackvisser.seleniumdemo.feature.AddUserFeature#scenarios")
    void testAddUser(UserRole userRole) {
        User newUser = new User(RandomStringUtils.randomAlphabetic(8),
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphanumeric(6),
                userRole,
                "%s@example.com".formatted(RandomStringUtils.randomAlphabetic(8)),
                RandomStringUtils.randomNumeric(10));

        userManagementPage.addUser(newUser);

        UserRow userRow = userManagementPage.getUserRow(newUser.username())
                .orElseThrow(() -> new AssertionError(
                        "Expected added user with username '%s' to be present".formatted(newUser.username())));

        User actualUserInfo = userRow.getUserInfo();
        assertEquals(newUser, actualUserInfo, "Added user info");
    }

    @ParameterizedTest
    @MethodSource("com.jackvisser.seleniumdemo.feature.DeleteUserFeature#scenarios")
    void testDeleteUser(String username) {
        int numberOfUsersPriorToDelete = userManagementPage.getUserRows().size();

        UserRow userRow = userManagementPage.getUserRow(username)
                .orElseThrow(() -> new NoSuchElementException("User with username '%s' not found".formatted(username)));

        userRow.clickDeleteButton();
        userManagementPage.confirmDeleteUser();

        assertFalse(userManagementPage.getUserRow(username).isPresent(),
                "Is user with username '%s' present".formatted(username));
        assertEquals(numberOfUsersPriorToDelete - 1, userManagementPage.getUserRows().size(), "Remaining user count");
    }
}
