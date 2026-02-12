package com.basis.pages;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoginPage extends Page{
    protected TextField usernameField;
    protected PasswordField passwordField;
    protected Button loginButton;
    protected Button createAccountButton;

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getCreateAccountButton() {
        return createAccountButton;
    }

    public void resetInputFields() {
        usernameField.clear();
        passwordField.clear();
        errorMessageLabel.setText("");
    }

    protected void initializeElements() {
        titleLabel = new Label("CasinoEngine");
        errorMessageLabel = new Label("");
        footerLabel = new Label("© 2026 Casino Engine. All rights reserved.");

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        loginButton = new Button("Login");
        createAccountButton = new Button("Create Account");

        mainLayout = new VBox(15);
        buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPrefSize(200, 200);

        buttonBox.getChildren().addAll(loginButton, createAccountButton);

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