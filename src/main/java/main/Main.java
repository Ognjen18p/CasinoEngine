package main;

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
        // Kreiranje UI elemenata
        Label label = new Label("Dobrodošao u JavaFX!");
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button button = new Button("Klikni me");
        button.setOnAction(e -> label.setText("Kliknuto!"));

        // Layout
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(label, button);

        // Scena i Stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("JavaFX Maven App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}