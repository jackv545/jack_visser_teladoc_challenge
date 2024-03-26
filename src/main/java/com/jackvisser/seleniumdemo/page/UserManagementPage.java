package com.jackvisser.seleniumdemo.page;

import com.jackvisser.seleniumdemo.comonent.UserRow;
import com.jackvisser.seleniumdemo.model.User;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class UserManagementPage {
    private static final String USER_MANAGEMENT_PAGE_TITLE = "Protractor practice website - WebTables";

    private final WebDriver driver;

    private final By addUserBy = By.xpath("//button[contains(text(), 'Add User')]");
    private final By firstNameBy = By.name("FirstName");
    private final By lastNameBy = By.name("LastName");
    private final By userNameBy = By.name("UserName");
    private final By roleDropdownBy = By.name("RoleId");
    private final By emailBy = By.name("Email");
    private final By cellPhoneBy = By.name("Mobilephone");
    private final By saveButtonBy = By.cssSelector(".modal .btn-success");

    private final By genericRowBy = By.cssSelector("table[table-title='Smart Table example'] tr");

    private final By deleteConfirmationButton = By.cssSelector(".modal-footer .btn-primary");

    public UserManagementPage(WebDriver driver) {
        this.driver = driver;

        if (!driver.getTitle().equals(USER_MANAGEMENT_PAGE_TITLE)) {
            throw new IllegalStateException("This is not the table page. Current page is: " + driver.getTitle());
        }
    }

    public void addUser(User user) {
        driver.findElement(addUserBy).click();
        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.presenceOfElementLocated(firstNameBy));

        driver.findElement(firstNameBy).sendKeys(user.firstName());
        driver.findElement(lastNameBy).sendKeys(user.lastName());
        driver.findElement(userNameBy).sendKeys(user.username());

        new Select(driver.findElement(roleDropdownBy))
                .selectByVisibleText(user.role().getUiLabel());

        driver.findElement(emailBy).sendKeys(user.email());
        driver.findElement(cellPhoneBy).sendKeys(user.cellPhone());

        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.elementToBeClickable(saveButtonBy));

        driver.findElement(saveButtonBy).click();
        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.presenceOfElementLocated(getLocatorForUser(user.username())));
    }

    public Optional<UserRow> getUserRow(String username) {
        return driver.findElements(getLocatorForUser(username)).stream()
                .map(UserRow::new)
                .findFirst();
    }

    public List<UserRow> getUserRows() {
        return driver.findElements(genericRowBy).stream()
                .map(UserRow::new)
                .toList();
    }

    public void confirmDeleteUser() {
        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.presenceOfElementLocated(deleteConfirmationButton));

        driver.findElement(deleteConfirmationButton).click();
    }

    private By getLocatorForUser(String username) {
        return By.xpath("//td[text() = '%s']//ancestor::tr".formatted(username));
    }
}
