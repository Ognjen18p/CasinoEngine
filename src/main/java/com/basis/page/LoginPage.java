package com.basis.page;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;

public class LoginPage extends Page {
    private Label usernameLabel;
    private Label passwordLabel;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button createAccountButton;

    public LoginPage() {
        super();
    }

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


    @Override
    public void initializeElements() {

        /// !!! Header !!!
        titleLabel = new Label("CasinoEngine");
        headerBox = new HBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().addAll(titleLabel);


        /// !!! Content !!!
        usernameLabel = new Label("Username:");
        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordLabel = new Label("Password:");
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        errorMessageLabel = new Label(" ");

        loginButton = new Button("Login");
        loginButton.setMaxWidth(350);
        createAccountButton = new Button("Create Account");
        createAccountButton.setMaxWidth(350);

        contentBox= new VBox(10);
        contentBox.setAlignment(Pos.CENTER_LEFT);
        contentBox.setMaxWidth(350);
        contentBox.getChildren().addAll(
                usernameLabel,
                usernameField,
                passwordLabel,
                passwordField,
                errorMessageLabel,
                loginButton,
                createAccountButton);


        /// !!! Footer !!!
        footerLabel = new Label("© 2026 Casino Engine. All rights reserved.");
        footerBox = new HBox(10);
        footerBox.setAlignment(Pos.CENTER);
        footerBox.getChildren().addAll(footerLabel);

        /// !!! Main root !!!
        mainLayout = new BorderPane();
        mainLayout.setTop(headerBox);
        mainLayout.setCenter(contentBox);
        mainLayout.setBottom(footerBox);

    }

}