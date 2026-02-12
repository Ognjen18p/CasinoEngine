package com.stylization.pages;

import com.basis.pages.LoginPage;
import com.basis.pages.RegistrationPage;

public class RegistrationPageStylization {
    private RegistrationPage registrationPage;

    public RegistrationPageStylization(RegistrationPage registrationPage) {
        this.registrationPage = registrationPage;
        styleRegistrationPage();
    }
    public void styleRegistrationPage() {
        registrationPage.getCreateAccountButton();
    }
}
