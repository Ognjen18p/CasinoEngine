package com.stylization.pages;

import com.basis.pages.LoginPage;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class LoginPageStylization {
    public static void styleLoginPage(LoginPage loginPage) {
        VBox mainLayout = loginPage.getMainLayout();

        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPrefSize(800, 600);
        mainLayout.setSpacing(20);
        mainLayout.setStyle("-fx-background-color: grey;"); // dark background

        loginPage.getTitleLabel().setStyle(
                "-fx-font-size: 36px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255,215,0,0.8), 10, 0, 0, 0);"
        );

        String inputStyle =
                "-fx-background-color: #16213e;" +
                        "-fx-text-fill: white;" +
                        "-fx-prompt-text-fill: #888;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;" +
                        "-fx-padding: 10;" +
                        "-fx-font-size: 14px;";

        loginPage.getUsernameField().setStyle(inputStyle);
        loginPage.getPasswordField().setStyle(inputStyle);

        String buttonStyle =
                "-fx-background-color: #e94560;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 30 10 30;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand;";

        String buttonHoverStyle =
                "-fx-background-color: #d63447;" +
                        "-fx-scale-x: 1.05;" +
                        "-fx-scale-y: 1.05;";

        loginPage.getLoginButton().setStyle(buttonStyle);
        loginPage.getRegisterButton().setStyle(
                buttonStyle.replace("#e94560", "#533483")
        );

        loginPage.getLoginButton().setOnMouseEntered(e ->
                loginPage.getLoginButton().setStyle(buttonStyle + buttonHoverStyle)
        );
        loginPage.getLoginButton().setOnMouseExited(e ->
                loginPage.getLoginButton().setStyle(buttonStyle)
        );

        loginPage.getRegisterButton().setOnMouseEntered(e ->
                loginPage.getRegisterButton().setStyle(
                        buttonStyle.replace("#e94560", "#533483") + buttonHoverStyle
                )
        );
        loginPage.getRegisterButton().setOnMouseExited(e ->
                loginPage.getRegisterButton().setStyle(
                        buttonStyle.replace("#e94560", "#533483")
                )
        );

        loginPage.getButtonBox().setAlignment(Pos.CENTER);
        loginPage.getButtonBox().setSpacing(15);

        loginPage.getErrorMessageLabel().setStyle(
                "-fx-text-fill: #ff4757;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;"
        );

        loginPage.getFooterLabel().setStyle(
                "-fx-text-fill: #666;" +
                        "-fx-font-size: 12px;"
        );
    }
}
