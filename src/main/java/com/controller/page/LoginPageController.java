package com.controller.page;

import com.application.configuration.GameSettings;
import com.basis.page.LoginPage;
import com.basis.person.Player;
import com.controller.Controller;
import com.database.page.LoginPageDAO;
import com.stylization.page.LoginPageStylization;
import javafx.scene.Scene;
import com.application.GameManager;

public class LoginPageController extends Controller {

    private LoginPage loginPage;
    private LoginPageDAO loginPageDAO;

    public LoginPageController() {
        initializeScene();
        setupEventHandlers();
    }

    @Override
    protected void initializeScene() {
        loginPage = new LoginPage();
        loginPageDAO = new LoginPageDAO();
        scene = new Scene(loginPage.getMainLayout(), GameSettings.getInstance().getWindowWidth(), GameSettings.getInstance().getWindowHeight());

        new LoginPageStylization(loginPage);
    }

    @Override
    protected void setupEventHandlers() {
        loginPage.getLoginButton().setOnAction(event -> handleLogin());

        loginPage.getCreateAccountButton().setOnAction(event -> handleCreateNew());
    }

    private void handleLogin() {
        String username = loginPage.getUsernameField().getText();
        String password = loginPage.getPasswordField().getText();

        Player player = loginPageDAO.findPlayer(username, password);
        if (player != null) {
            GameManager.getInstance().setCurrentPlayer(player);
            GameManager.getInstance().setCurrentController(new MenuPageController());
            return;
        }

        loginPage.showErrorMessage(loginPageDAO.getErrorMessage());
    }

    @Override
    public void showScene() {
        GameManager.getInstance().setMainScene(scene);
        GameManager.getInstance().getMainStage().setTitle("Casino Engine - Login");
    }

    private void handleCreateNew() {
        GameManager.getInstance().setCurrentController(new RegistrationPageController());
    }
}