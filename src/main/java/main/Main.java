package main;

import com.basis.pages.LoginPage;
import com.controller.pages.LoginPageController;
import com.stylization.pages.LoginPageStylization;
import com.stylization.pages.PageStylization;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create LoginPage
        LoginPage loginPage = new LoginPage();

        // Apply stylization
        LoginPageStylization.styleLoginPage(loginPage);

        // Create controller (handles events)
        LoginPageController controller = new LoginPageController(loginPage);

        // Create scene
        Scene scene = new Scene(loginPage.getMainLayout(), 800, 600);

        // Setup stage
        primaryStage.setTitle("Casino Engine - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}