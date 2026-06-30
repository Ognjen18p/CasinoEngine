package com.basis.page;

import com.application.GameManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;

public class LoginPage extends Page {
    private Label usernameLabel;
    private Label passwordLabel;
    private Label previousSessionLabel;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button createAccountButton;
    private Button returnToPreviousSessionButton;
    private Button keepToLoginButton;
    private VBox previousSessionBox;
    private Stage previousSessionStage;
    private Scene previousSessionScene;

    public VBox getPreviousSessionBox() {
        return previousSessionBox;
    }

    public Stage getPreviousSessionStage() {
        return previousSessionStage;
    }

    public Scene getPreviousSessionScene() {
        return previousSessionScene;
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public Label getPasswordLabel() {
        return passwordLabel;
    }

    public Label getPreviousSessionLabel() {
        return previousSessionLabel;
    }

    public Button getReturnToPreviousSessionButton() {
        return returnToPreviousSessionButton;
    }

    public Button getKeepToLoginButton() {
        return keepToLoginButton;
    }

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

        previousSessionLabel = new Label("Previous active session return?");
        returnToPreviousSessionButton = new Button("return");
        keepToLoginButton = new Button("keep login in");

        previousSessionBox = new VBox(10);
        previousSessionBox.setAlignment(Pos.CENTER);
        previousSessionBox.getChildren().addAll(previousSessionLabel,returnToPreviousSessionButton,keepToLoginButton);

        previousSessionStage = new Stage();
        previousSessionStage.initModality(Modality.APPLICATION_MODAL);
        previousSessionStage.initOwner(GameManager.getInstance().getMainStage());
        previousSessionStage.setResizable(false);

        previousSessionScene = new Scene(previousSessionBox, 400, 400);
        previousSessionStage.setScene(previousSessionScene);
        previousSessionStage.setTitle("Return");

        loginButton = new Button("Login");
        loginButton.setMaxWidth(350);
        createAccountButton = new Button("Create Account");
        createAccountButton.setMaxWidth(350);

        contentBox = new VBox(10);
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