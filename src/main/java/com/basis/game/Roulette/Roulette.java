package com.basis.game.Roulette;

import com.basis.game.Game.Game;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Roulette extends Game {

    public enum FieldType {
        NUMBER, EVEN, ODD, RED, BLACK, FIRST_DOZEN, SECOND_DOZEN, THIRD_DOZEN,
        FIRST_COLUMN, SECOND_COLUMN, THIRD_COLUMN, FIRST_HALF, SECOND_HALF
    }

    public static final int[] WHEEL_NUMBERS = {
            0, 32, 15, 19, 4, 21, 2, 25, 17, 34,
            6, 27, 13, 36, 11, 30, 8, 23, 10, 5,
            24, 16, 33, 1, 20, 14, 31, 9, 22, 18,
            29, 7, 28, 12, 35, 3, 26
    };

    private Pane table;
    private Pane ballTrack;
    private Pane rouletteLayout;
    private ImageView wheelImage;
    private Button spinButton;
    private HashMap<Integer, ImageView> pockets;
    private HashMap<Button, FieldType> layoutFields;

    public ImageView getWheelImage() {
        return wheelImage;
    }

    public Pane getTable() {
        return table;
    }

    public Pane getBallTrack() {
        return ballTrack;
    }

    public HashMap<Integer, ImageView> getPockets() {
        return pockets;
    }

    public Button getSpinButton() {
        return spinButton;
    }

    public Roulette() {
        initializeElements();
    }

    @Override
    protected void initializeElements() {
        super.initializeElements();

        table = new Pane();
        table.setPrefWidth(400);
        table.setPrefHeight(400);

        ballTrack = new Pane();
        ballTrack.setPrefWidth(400);
        ballTrack.setPrefHeight(400);

        wheelImage = new ImageView();
        wheelImage.setImage(new Image(getClass().getResource("/images/RouletteImages/wheel.png").toExternalForm()));
        wheelImage.setFitWidth(400);
        wheelImage.setFitHeight(400);

        spinButton = new Button();
        spinButton.setText("Spin");
        spinButton.setLayoutX(200);
        spinButton.setLayoutY(100);

        table.getChildren().addAll(wheelImage, ballTrack);

        dropPockets();
        createLayout();

        mainPane.getChildren().addAll(table, spinButton);
    }

    private void dropPockets() {
        double centarX = ballTrack.getPrefWidth() / 2;
        double centarY = ballTrack.getPrefHeight() / 2;
        double radius = 110;

        pockets = new HashMap<>();

        for (int nPocket = 0; nPocket < WHEEL_NUMBERS.length; nPocket++) {
            double angle = 2 * Math.PI * nPocket / WHEEL_NUMBERS.length;
            double x = radius * Math.cos(angle) + centarX;
            double y = radius * Math.sin(angle) + centarY;

            ImageView pocket = new ImageView();

            pocket.setRotate(Math.toDegrees(angle));
            pocket.setFitWidth(20);
            pocket.setFitHeight(20);
            pocket.setX(x - 10);
            pocket.setY(y - 10);
            ballTrack.getChildren().add(pocket);
            pockets.put(WHEEL_NUMBERS[nPocket], pocket);
        }
    }

    private void createLayout() {
        Vector2 fieldSize = new Vector2(30, 40);
        VBox layoutBox = new VBox();
        layoutBox.setLayoutX(400);
        layoutBox.setLayoutY(400);
        layoutBox.setPrefSize(fieldSize.getX() * 14, fieldSize.getY() * 5);

        layoutFields = new HashMap<>();

        HBox mainHBox = new HBox();

        Button zeroButton = new Button("0");
        zeroButton.setPrefSize(fieldSize.getX(), fieldSize.getY() * 3);
        layoutFields.put(zeroButton, FieldType.NUMBER);
        mainHBox.getChildren().add(zeroButton);

        /// ----------- Numbers & 2 to 1: ----------- ///
        VBox fieldsVBox = new VBox();
        ArrayList<Integer> redNumbers = new ArrayList<>(List.of(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36));
        int number;
        for (int row = 3; row > 0; row--) {
            HBox rowHBox = new HBox();
            number = row;
            for (int column = 0; column < 12; column++) {
                Pane numberPane = new Pane();
                numberPane.setPrefSize(fieldSize.getX(), fieldSize.getY());
                Button numberButton = new Button();
                numberButton.setPrefSize(fieldSize.getX(), fieldSize.getY());
                Label numberLabel = new Label(Integer.toString(number));
                if (redNumbers.contains(number))
                    numberLabel.setBackground(Background.fill(Color.RED));
                else
                    numberLabel.setBackground(Background.fill(Color.BLACK));
                numberLabel.setPrefSize(fieldSize.getX(), fieldSize.getY());
                numberLabel.setStyle("fx-background-radius: 30%;");
                numberPane.getChildren().addAll(numberButton, numberLabel);
                rowHBox.getChildren().add(numberPane);
                number += 3;
                layoutFields.put(numberButton, FieldType.NUMBER);
            }
            Button twoToOneButton = new Button("2to1");
            twoToOneButton.setPrefSize(fieldSize.getX(), fieldSize.getY());
            rowHBox.getChildren().add(twoToOneButton);
            fieldsVBox.getChildren().add(rowHBox);
        }
        mainHBox.getChildren().add(fieldsVBox);

        layoutBox.getChildren().add(mainHBox);
        mainPane.getChildren().add(layoutBox);
    }
}
