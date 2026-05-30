package main;

import com.controller.page.LoginPageController;
import com.application.GameManager;
import com.database.DatabaseManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage mainStage) {
        DatabaseManager.initialize("jdbc:mysql://localhost:3306/casino_engine", "root", "");
        GameManager.getInstance().initializeElements(mainStage);
        new LoginPageController().showScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}