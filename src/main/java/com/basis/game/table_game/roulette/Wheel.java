package com.basis.game.table_game.roulette;

import com.basis.game.essentials.Vector2;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class Wheel {

    private final int[] WHEEL_NUMBERS = {
            0, 32, 15, 19, 4, 21, 2, 25, 17, 34,
            6, 27, 13, 36, 11, 30, 8, 23, 10, 5,
            24, 16, 33, 1, 20, 14, 31, 9, 22, 18,
            29, 7, 28, 12, 35, 3, 26
    };

    private Vector2 wheelSize;
    private Pane wheelTable;
    private Pane ballTrack;
    private HashMap<Integer, ImageView> pockets;
    private ImageView wheelImage;

    public ImageView getWheelImage() {
        return wheelImage;
    }

    public Pane getTable() {
        return wheelTable;
    }

    public Pane getBallTrack() {
        return ballTrack;
    }

    public HashMap<Integer, ImageView> getPockets() {
        return pockets;
    }

    public Vector2 getWheelSize() {
        return wheelSize;
    }

    public Wheel(Vector2 wheelSize) {
        this.wheelSize = wheelSize;

        wheelTable = new Pane();
        wheelTable.setPrefSize(wheelSize.getX(), wheelSize.getY());

        ballTrack = new Pane();
        ballTrack.setPrefSize(wheelSize.getX(), wheelSize.getY());

        wheelImage = new ImageView();
        wheelImage.setImage(new Image(getClass().getResource("/images/RouletteImages/wheel.png").toExternalForm()));
        wheelImage.setFitWidth(wheelSize.getX());
        wheelImage.setFitHeight(wheelSize.getY());

        wheelTable.getChildren().addAll(wheelImage, ballTrack);

        dropPockets();
    }

    private void dropPockets() {
        double centerX = wheelSize.getX() / 2;
        double centerY = wheelSize.getY() / 2;
        double radius = wheelSize.getX() / 4 + wheelSize.getY() / 40;

        pockets = new HashMap<>();

        for (int nPocket = 0; nPocket < WHEEL_NUMBERS.length; nPocket++) {
            double angle = 2 * Math.PI * nPocket / WHEEL_NUMBERS.length;
            double x = radius * Math.cos(angle) + centerX;
            double y = radius * Math.sin(angle) + centerY;

            ImageView pocket = new ImageView();

            pocket.setRotate(Math.toDegrees(angle));
            pocket.setFitWidth(wheelSize.getX() / 20);
            pocket.setFitHeight(wheelSize.getY() / 20);
            pocket.setX(x - wheelSize.getX() / 40);
            pocket.setY(y - wheelSize.getY() / 40);
            ballTrack.getChildren().add(pocket);
            pockets.put(WHEEL_NUMBERS[nPocket], pocket);
        }
    }
}
