package com.basis.page;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RegistrationPage extends Page {
    private TextField usernameField;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private Button createNewAccountButton;
    private Button alreadyHaveAccountButton;

    public RegistrationPage() {
        super();
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public PasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public Button getCreateNewAccountButton() {
        return createNewAccountButton;
    }

    public Button getAlreadyHaveAccountButton() {
        return alreadyHaveAccountButton;
    }

    public TextField getNameField() {
        return firstNameField;
    }

    public TextField getSurnameField() {
        return firstNameField;
    }

    @Override
    public void initializeElements() {
        titleLabel = new Label("CasinoEngine");
        errorMessageLabel = new Label("");
        footerLabel = new Label("© 2026 Casino Engine. All rights reserved.");

        firstNameField = new TextField();
        firstNameField.setPromptText("Name");

        lastNameField = new TextField();
        lastNameField.setPromptText("Surname");

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        emailField = new TextField();
        emailField.setPromptText("Email");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");

        createNewAccountButton = new Button("Create New");
        alreadyHaveAccountButton = new Button("Already Have Account");

        mainLayout = new VBox(15);
        buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPrefSize(200, 200);

        buttonBox.getChildren().addAll(createNewAccountButton, alreadyHaveAccountButton);

        mainLayout.getChildren().addAll(
                titleLabel,
                firstNameField,
                lastNameField,
                usernameField,
                emailField,
                passwordField,
                confirmPasswordField,
                buttonBox,
                errorMessageLabel,
                footerLabel
        );
    }

}
