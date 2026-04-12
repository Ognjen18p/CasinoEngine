package main;

import com.controller.page.LoginPageController;
import com.manager.GameManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage mainStage) {
        GameManager.getInstance().initializeElements(mainStage);
        new LoginPageController().showScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}