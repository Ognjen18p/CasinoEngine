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
        LoginPage loginPage = new LoginPage();
        LoginPageStylization.styleLoginPage(loginPage);
        LoginPageController controller = new LoginPageController(loginPage);

        Scene scene = new Scene(loginPage.getMainLayout(), 800, 600);

        primaryStage.setTitle("Casino Engine - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}