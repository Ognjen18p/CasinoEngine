package com.basis.pages;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RegistrationPage extends LoginPage {
    private TextField nameField;
    private TextField surnameField;
    private TextField emailField;
    private PasswordField confirmPasswordField;
    private Button createNewAccountButton;
    private Button alreadyHaveAccountButton;

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
        return nameField;
    }

    public TextField getSurnameField() {
        return surnameField;
    }

    @Override
    public void initializeElements() {
        titleLabel = new Label("CasinoEngine");
        errorMessageLabel = new Label("");
        footerLabel = new Label("© 2026 Casino Engine. All rights reserved.");

        nameField = new TextField();
        nameField.setPromptText("Name");

        surnameField = new TextField();
        surnameField.setPromptText("Surname");

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
                nameField,
                surnameField,
                usernameField,
                emailField,
                passwordField,
                confirmPasswordField,
                buttonBox,
                errorMessageLabel,
                footerLabel
        );
    }

    public RegistrationPage() {
        initializeElements();
    }
}
