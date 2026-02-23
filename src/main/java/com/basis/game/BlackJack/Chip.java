package com.basis.game.BlackJack;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Chip {
    public static final int[] BETTING_VALUES = {5, 10, 50, 100, 500};

    private final int value;
    private boolean isSelected;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Chip(int value, double xPos, double yPos, boolean isButton) {
        this.value = value;
        image = new ImageView(new Image(getClass().getResource("/images/BlackJackImages/Chips/chip" + value + ".png").toExternalForm(), true));
        image.setFitHeight(60);
        image.setFitWidth(60);

        if (isButton) {
            button = new Button();
            button.setGraphic(image);
            button.setStyle("-fx-background-color: transparent;");
            button.setLayoutX(xPos);
            button.setLayoutY(yPos);
        } else {
            image.setX(xPos);
            image.setY(yPos);
        }
    }

    public Chip(int value){
        this.value = value;
        image = new ImageView(new Image(getClass().getResource("/images/BlackJackImages/Chips/chip" + value + ".png").toExternalForm(), true));
    }
}

