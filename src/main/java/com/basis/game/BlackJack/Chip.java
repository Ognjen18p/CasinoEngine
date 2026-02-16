package com.basis.game.BlackJack;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Chip {
    private final int value;
    private Button button;
    private ImageView image;

    public int getValue() {
        return value;
    }

    public Button getButton() {
        return button;
    }

    public ImageView getImage() {
        return image;
    }

    public Chip(int value, double xPos, double yPos, boolean isButton) {
        this.value = value;
        image = new ImageView(new Image(getClass().getResource("/images/BlackJackImages/Chips/chip" + value + ".png").toExternalForm()));
        image.setFitHeight(60);
        image.setFitWidth(60);

        if(isButton) {
            button = new Button();
            button.setGraphic(image);
            button.setTranslateX(xPos);
            button.setTranslateY(yPos);
        }
        else {
            image.setX(xPos);
            image.setY(yPos);
        }
    }
}

