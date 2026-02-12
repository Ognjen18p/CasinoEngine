package com.controller.pages;

import com.basis.pages.RegistrationPage;
import com.basis.person.Person;
import com.basis.person.Player;
import com.basis.security.RegistrationValidator;
import com.stylization.pages.RegistrationPageStylization;
import javafx.scene.Scene;
import main.GameManager;
import java.util.Date;

public class RegistrationPageController extends PageController {

    private RegistrationPage registrationPage;

    public RegistrationPageController(GameManager gameManager) {
        this.gameManager = gameManager;
        initializePage();
        setupEventHandlers();
    }

    @Override
    protected void initializePage(){
        registrationPage = new RegistrationPage();
        scene = new Scene(registrationPage.getMainLayout(), 900, 700);
        RegistrationPageStylization registrationPageStylization = new RegistrationPageStylization(registrationPage);
    }

    @Override
    protected void setupEventHandlers() {
        registrationPage.getCreateNewAccountButton().setOnAction(event -> handleRegistration());

        registrationPage.getAlreadyHaveAccountButton().setOnAction(event -> handleBackToLogin());

        registrationPage.getConfirmPasswordField().setOnAction(event -> handleRegistration());
    }

    private void handleRegistration() {
        String name = registrationPage.getNameField().getText();
        String surname = registrationPage.getSurnameField().getText().trim();
        String email = registrationPage.getEmailField().getText().trim();
        String username = registrationPage.getUsernameField().getText().trim();
        String password = registrationPage.getPasswordField().getText();
        String confirmPassword = registrationPage.getConfirmPasswordField().getText();

        if (!validateAllFields(name, surname, email, username, password, confirmPassword))
            return;

        boolean success = createNewPlayer(name, surname, email, username, password);

        if (success) {
            registrationPage.showErrorMessage("");
            System.out.println("Registration successful! Welcome, " + name + "!");

            handleBackToLogin();

        } else
            registrationPage.showErrorMessage("Registration failed! Username or email already exists.");
    }

    private boolean validateAllFields(String name, String surname, String email,
                                      String username, String password, String confirmPassword) {

        RegistrationValidator validator = new RegistrationValidator();
        if (!validator.isNameValid(name)) {
            registrationPage.showErrorMessage(validator.getErrorMessage());
            return false;
        }

        if (!validator.isSurnameValid(surname)) {
            registrationPage.showErrorMessage(validator.getErrorMessage());
            return false;
        }

        if (!validator.isEmailValid(email)) {
            registrationPage.showErrorMessage(validator.getErrorMessage());
            return false;
        }

        if (!validator.isUsernameValid(username)) {
            registrationPage.showErrorMessage(validator.getErrorMessage());
            return false;
        }

        if (usernameExists(username)) {
            registrationPage.showErrorMessage("Username already exists!");
            return false;
        }

        if (emailExists(email)) {
            registrationPage.showErrorMessage("Email already registered!");
            return false;
        }

        if (!validator.isPasswordValid(password)) {
            registrationPage.showErrorMessage(validator.getErrorMessage());
            return false;
        }

        if (!validator.doPasswordMatch(password, confirmPassword)) {
            registrationPage.showErrorMessage(validator.getErrorMessage());
            return false;
        }

        return true;
    }

    private boolean createNewPlayer(String name, String surname, String email, String username, String password) {

        Player newPlayer = new Player(
                0,
                name,
                surname,
                email,
                username,
                password,
                Person.Role.PLAYER,
                new Date(),
                0.0f,
                0
        );

        System.out.println("New player created: " + newPlayer);

        return true;
    }

    @Override
    public void showPage() {
        gameManager.setMainScene(scene);
        gameManager.getMainStage().setTitle("Casino Engine - Register");
    }

    private void handleBackToLogin() {
        gameManager.getLoginPageController().showPage();
    }

    private boolean usernameExists(String username) {
        return username.equalsIgnoreCase("player1") ||
                username.equalsIgnoreCase("admin");
    }

    private boolean emailExists(String email) {
        return email.equalsIgnoreCase("player1@mail.com");
    }
}