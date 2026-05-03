package com.basis.page;

import com.application.GameManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RegistrationPage extends Page {
    private Label nameLabel;
    private Label lastnameLabel;
    private Label usernameLabel;
    private Label emailLabel;
    private Label passwordLabel;
    private Label confirmPasswordLabel;
    private Label birthdayLabel;
    private Label verificationErrorLabel;
    private TextField usernameField;
    private TextField nameField;
    private TextField lastnameField;
    private TextField emailField;
    private TextField codeField;
    private DatePicker birthdayField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private Button verifyAccountButton;
    private Button resendCodeButton;
    private Button changeFieldsButton;
    private Button createNewAccountButton;
    private Button alreadyHaveAccountButton;
    private VBox verificationBox;
    private Stage verificationStage;
    private Scene verificationScene;


    public RegistrationPage() {
        super();
    }

    public TextField getCodeField() {
        return codeField;
    }

    public VBox getVerificationBox() {
        return verificationBox;
    }

    public Scene getVerificationScene() {
        return verificationScene;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public DatePicker getBirthdayField() {
        return birthdayField;
    }

    public Button getVerifyAccountButton() {
        return verifyAccountButton;
    }

    public Button getResendCodeButton() {
        return resendCodeButton;
    }

    public Button getChangeFieldsButton() {
        return changeFieldsButton;
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
        return nameField;
    }

    public TextField getLastnameField() {
        return lastnameField;
    }

    public Stage getVerificationStage() {
        return verificationStage;
    }

    public void showVerificationErrorMessage(String message) {
        verificationErrorLabel.setText(message);
    }

    @Override
    public void initializeElements() {

        /// !!! Header !!!
        titleLabel = new Label("CasinoEngine");
        headerBox = new HBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().addAll(titleLabel);

        /// !!! Content !!!
        nameLabel = new Label("Name:");
        nameField = new TextField();
        nameField.setPromptText("Name");

        lastnameLabel = new Label("Lastname:");
        lastnameField = new TextField();
        lastnameField.setPromptText("Lastname");

        usernameLabel = new Label("Username:");
        usernameField = new TextField();
        usernameField.setPromptText("Username");

        emailLabel = new Label("Email:");
        emailField = new TextField();
        emailField.setPromptText("Email");

        passwordLabel = new Label("Password:");
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");

        birthdayLabel = new Label("Date of Birth:");
        birthdayField = new DatePicker();
        birthdayField.setMaxWidth(350);
        birthdayField.setPromptText("Date of birthday");

        createNewAccountButton = new Button("Create New");
        createNewAccountButton.setMaxWidth(350);
        alreadyHaveAccountButton = new Button("Already Have Account");
        alreadyHaveAccountButton.setPrefWidth(350);

        errorMessageLabel = new Label("");

        contentBox = new VBox(10);
        contentBox.setAlignment(Pos.CENTER_LEFT);
        contentBox.setMaxWidth(350);
        contentBox.getChildren().addAll(
                nameLabel,
                nameField,
                lastnameLabel,
                lastnameField,
                usernameLabel,
                usernameField,
                emailLabel,
                emailField,
                passwordLabel,
                passwordField,
                confirmPasswordLabel,
                confirmPasswordField,
                birthdayLabel,
                birthdayField,
                errorMessageLabel,
                createNewAccountButton,
                alreadyHaveAccountButton
        );

        /// !!! Verification !!!
        codeField = new TextField();
        codeField.setPromptText("Code");

        verifyAccountButton = new Button("Verify Account");
        verifyAccountButton.setPrefWidth(150);
        changeFieldsButton = new Button("Change Data");
        changeFieldsButton.setPrefWidth(150);
        resendCodeButton = new Button("Resend Code");
        resendCodeButton.setPrefWidth(150);

        verificationErrorLabel = new Label("");

        verificationBox = new VBox(10);
        verificationBox.setAlignment(Pos.CENTER);
        verificationBox.getChildren().addAll(codeField, verificationErrorLabel, resendCodeButton, verifyAccountButton, changeFieldsButton);

        verificationStage = new Stage();
        verificationStage.initModality(Modality.APPLICATION_MODAL);
        verificationStage.initOwner(GameManager.getInstance().getMainStage());
        verificationStage.setResizable(false);

        verificationScene = new Scene(verificationBox, 400, 400);
        verificationStage.setScene(verificationScene);
        verificationStage.setTitle("Verify Email");

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