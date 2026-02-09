package com.basis.pages;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoginPage {
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerButton;
    private Label errorMessageLabel;
    private Label footerLabel;
    private Label titleLabel;
    private VBox mainLayout;
    private VBox buttonBox;

    public TextField getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(TextField usernameField) {
        this.usernameField = usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(Button loginButton) {
        this.loginButton = loginButton;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public void setRegisterButton(Button registerButton) {
        this.registerButton = registerButton;
    }

    public Label getErrorMessageLabel() {
        return errorMessageLabel;
    }

    public void setErrorMessageLabel(Label errorMessageLabel) {
        this.errorMessageLabel = errorMessageLabel;
    }

    public Label getFooterLabel() {
        return footerLabel;
    }

    public void setFooterLabel(Label footerLabel) {
        this.footerLabel = footerLabel;
    }

    public VBox getMainLayout() {
        return mainLayout;
    }

    public void setMainLayout(VBox mainLayout) {
        this.mainLayout = mainLayout;
    }

    public VBox getButtonBox() {
        return buttonBox;
    }

    public void setButtonBox(VBox buttonBox) {
        this.buttonBox = buttonBox;
    }

    public void showErrorMessage(String errorMessage) {
        this.errorMessageLabel.setText(errorMessage);
    }

    public void resetInputFields() {
        usernameField.clear();
        passwordField.clear();
        errorMessageLabel.setText("");
    }

    private void initializeElements() {
        titleLabel = new Label("CasinoEngine");
        errorMessageLabel = new Label("");
        footerLabel = new Label("© 2026 Casino Engine. All rights reserved.");

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        loginButton = new Button("Login");
        registerButton = new Button("Register");

        mainLayout = new VBox(15);
        buttonBox = new VBox(10);

        buttonBox.getChildren().addAll(loginButton, registerButton);

        mainLayout.getChildren().addAll(
                titleLabel,
                usernameField,
                passwordField,
                buttonBox,
                errorMessageLabel,
                footerLabel
        );
    }

    public LoginPage() {
        initializeElements();
    }

}