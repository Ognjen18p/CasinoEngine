package com.controller.page;

import com.application.configuration.CasinoConfiguration;
import com.application.configuration.GameSettings;
import com.application.security.LoginAttemptSecurity;
import com.basis.page.LoginPage;
import com.basis.person.Player;
import com.basis.session.Session;
import com.controller.Controller;
import com.database.page.LoginPageDAO;
import com.database.session.SessionDAO;
import com.stylization.page.LoginPageStylization;
import javafx.scene.Scene;
import com.application.GameManager;

public class LoginPageController extends Controller {

    private LoginPage loginPage;
    private LoginPageDAO loginPageDAO;
    private LoginAttemptSecurity loginAttemptSecurity;
    private SessionDAO sessionDAO;

    public LoginPageController() {
        initializeScene();
        setupEventHandlers();
        activeSession();
    }

    @Override
    protected void initializeScene() {
        loginPage = new LoginPage();
        loginPageDAO = new LoginPageDAO();
        sessionDAO = new SessionDAO();
        loginAttemptSecurity = new LoginAttemptSecurity();
        scene = new Scene(loginPage.getMainLayout(), GameSettings.getInstance().getWindowWidth(), GameSettings.getInstance().getWindowHeight());

        new LoginPageStylization(loginPage);
    }

    @Override
    protected void setupEventHandlers() {
        loginPage.getLoginButton().setOnAction(event -> handleLogin());

        loginPage.getCreateAccountButton().setOnAction(event -> handleCreateNew());

    }

    private void handleReturnActiveSession(Session session) {
        loginPage.getReturnToPreviousSessionButton().setOnAction(event -> {
            Player player = loginPageDAO.getPlayer(session.getPersonId());
            if (player != null) {
                GameManager.getInstance().setCurrentPlayer(player);
                GameManager.getInstance().navigateTo(new MenuPageController());
                loginPage.getPreviousSessionStage().close();
            }
        });
        loginPage.getKeepToLoginButton().setOnAction(event -> {
            loginPage.getPreviousSessionStage().close();
        });
    }

    private void activeSession() {
        Session session = sessionDAO.checkSession();
        if (session != null) {
            loginPage.getPreviousSessionStage().show();
            handleReturnActiveSession(session);
        }
    }

        private void handleLogin () {
            String username = loginPage.getUsernameField().getText();
            String password = loginPage.getPasswordField().getText();

            if (loginAttemptSecurity.isLocked(username)) {
                loginPage.showErrorMessage(loginAttemptSecurity.getErrorMessage());
                return;
            }
            if (loginPageDAO.getErrorMessage().equals("Wrong password")) {
                loginAttemptSecurity.recordLoginAttempt(username);
                loginPage.showErrorMessage(loginPageDAO.getErrorMessage());
                return;
            }
            Player player = loginPageDAO.findPlayer(username, password);
            if (player != null) {
                GameManager.getInstance().setCurrentPlayer(player);
                GameManager.getInstance().navigateTo(new MenuPageController());
                sessionDAO.createSession(new Session(player.getId(), System.currentTimeMillis(), System.currentTimeMillis() + CasinoConfiguration.SESSION_DURATION,
                        sessionDAO.getOrCreateUUIDToken(), Session.State.ACTIVE));
            }
        }

        @Override
        public void showScene () {
            GameManager.getInstance().setMainScene(scene);
            GameManager.getInstance().getMainStage().setTitle("Casino Engine - Login");
        }

        private void handleCreateNew () {
            GameManager.getInstance().navigateTo(new RegistrationPageController());
        }
    }