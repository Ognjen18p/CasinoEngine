package com.controller.page;

import com.application.configuration.GameSettings;
import com.basis.game.Game;
import com.basis.page.RegistrationPage;
import com.basis.person.Player;
import com.controller.security.InputValidator;
import com.controller.Controller;
import com.application.GameManager;
import com.controller.security.email_service.EmailService;
import com.controller.security.email_service.VerificatonService;
import com.database.page.RegisterPageDAO;
import com.stylization.page.RegistrationPageStylization;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.util.Date;

public class RegistrationPageController extends Controller {

    private RegistrationPage registrationPage;
    private RegisterPageDAO registerPageDAO;
    private InputValidator inputValidator;

    public RegistrationPageController() {
        initializeScene();
        setupEventHandlers();
    }

    @Override
    protected void initializeScene() {
        registrationPage = new RegistrationPage();
        registerPageDAO = new RegisterPageDAO();
        inputValidator = new InputValidator();
        scene = new Scene(registrationPage.getMainLayout(), GameSettings.getInstance().getWindowWidth(), GameSettings.getInstance().getWindowHeight());
        new RegistrationPageStylization(registrationPage);
    }

    @Override
    protected void setupEventHandlers() {
        registrationPage.getCreateNewAccountButton().setOnAction(event -> handleRegistration());

        registrationPage.getAlreadyHaveAccountButton().setOnAction(event -> handleBackToLogin());

        registrationPage.getChangeFieldsButton().setOnAction(event -> handleChangeData());
    }

    private void handleRegistration() {
        String name = registrationPage.getNameField().getText();
        String lastname = registrationPage.getLastnameField().getText();
        String email = registrationPage.getEmailField().getText();
        String username = registrationPage.getUsernameField().getText();
        String password = registrationPage.getPasswordField().getText();
        String confirmPassword = registrationPage.getConfirmPasswordField().getText();
        LocalDate date = registrationPage.getBirthdayField().getValue();

        if (!inputValidator.isNameValid(name)) {
            registrationPage.showErrorMessage(inputValidator.getErrorMessage());
            return;
        }
        if (!inputValidator.isLastnameValid(lastname)) {
            registrationPage.showErrorMessage(inputValidator.getErrorMessage());
            return;
        }

        if (!inputValidator.isUsernameValid(username)) {
            registrationPage.showErrorMessage(inputValidator.getErrorMessage());
            return;
        }
        if (registerPageDAO.usernameExists(username)) {
            registrationPage.showErrorMessage(registerPageDAO.getErrorMessage());
            return;
        }

        if (!inputValidator.isEmailValid(email)) {
            registrationPage.showErrorMessage(inputValidator.getErrorMessage());
            return;
        }
        if (registerPageDAO.emailExists(email)) {
            registrationPage.showErrorMessage(registerPageDAO.getErrorMessage());
            return;
        }

        if (!inputValidator.isPasswordValid(password)) {
            registrationPage.showErrorMessage(inputValidator.getErrorMessage());
            return;
        }
        if (!inputValidator.arePasswordsEqual(password, confirmPassword)) {
            registrationPage.showErrorMessage(inputValidator.getErrorMessage());
            return;
        }
        if (!inputValidator.isAgeValid(date)) {
            registrationPage.showErrorMessage(inputValidator.getErrorMessage());
            return;
        }

        Player newPlayer = new Player(0, name, lastname, email, username, password, new Date(), 0.0f, 0);
        EmailService.getInstance().sendVerificationEmail(email, VerificatonService.getInstance().generateToken(email));
        registrationPage.getVerificationStage().show();

        handleVerifyEmail(email, newPlayer);
        handleResendCode(email);
    }

    private void handleVerifyEmail(String email, Player newPlayer) {
        registrationPage.getVerifyAccountButton().setOnAction(event -> {
            String code = registrationPage.getCodeField().getText();

            if (!VerificatonService.getInstance().verifyToken(email, code)) {
                registrationPage.showVerificationErrorMessage(VerificatonService.getInstance().getErrorMessage());
                registrationPage.showErrorMessage(VerificatonService.getInstance().getErrorMessage());
                return;
            }
            if (!registerPageDAO.createPlayer(newPlayer)) {
                registrationPage.getVerificationStage().close();
                registrationPage.showVerificationErrorMessage(registerPageDAO.getErrorMessage());
                registrationPage.showErrorMessage(registerPageDAO.getErrorMessage());
                return;
            }
            registrationPage.getVerificationStage().close();
            GameManager.getInstance().setCurrentPlayer(newPlayer);
            GameManager.getInstance().setCurrentController(new LoginPageController());
        });
    }

    private void handleResendCode(String email) {
        registrationPage.getResendCodeButton().setOnAction(event ->
                EmailService.getInstance().sendVerificationEmail(email, VerificatonService.getInstance().generateToken(email)));
    }

    private void handleChangeData() {
        registrationPage.getVerificationStage().close();
    }

    @Override
    public void showScene() {
        GameManager.getInstance().setMainScene(scene);
        GameManager.getInstance().getMainStage().setTitle("Casino Engine - Register");
    }

    private void handleBackToLogin() {
        GameManager.getInstance().setCurrentController(new LoginPageController());
    }

}