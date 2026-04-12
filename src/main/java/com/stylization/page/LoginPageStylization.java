package com.stylization.page;

import com.basis.page.LoginPage;

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
