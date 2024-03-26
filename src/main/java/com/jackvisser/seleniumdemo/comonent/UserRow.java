package com.jackvisser.seleniumdemo.comonent;

import com.jackvisser.seleniumdemo.model.User;
import com.jackvisser.seleniumdemo.model.UserRole;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class UserRow {
    private final WebElement root;

    private final By deleteButtonBy = By.xpath(".//i[contains(@class, 'remove')]//ancestor::button");

    public UserRow(WebElement root) {
        this.root = root;
    }

    public User getUserInfo() {
        return new User(getTextByColumnIndex(1),
                getTextByColumnIndex(2),
                getTextByColumnIndex(3),
                UserRole.ofUiLabel(getTextByColumnIndex(6)),
                getTextByColumnIndex(7),
                getTextByColumnIndex(8));
    }

    public void clickDeleteButton() {
        root.findElement(deleteButtonBy).click();
    }

    private String getTextByColumnIndex(int index) { // index starts at 1, index 4 is hidden
        return root.findElement(By.xpath("./td[%d]".formatted(index))).getText();
    }
}
