package com.stylization.pages;

import com.basis.pages.LoginPage;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class LoginPageStylization {
    private LoginPage loginPage;

    public LoginPageStylization(LoginPage loginPage) {
        this.loginPage = loginPage;
        styleLoginPage();
    }
    public void styleLoginPage() {
        loginPage.getCreateAccountButton();
    }
}
