package com.basis.game.table_game;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Chip {
    private final int value;
    private boolean isSelected;
    private boolean isDragged;
    private boolean isPurchased;
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

    public boolean isDragged() {
        return isDragged;
    }

    public void setDragged(boolean dragged) {
        isDragged = dragged;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    public double getSize() {
        return image.getFitWidth();
    }

    public Chip(int value, double xPos, double yPos, double size, boolean isButton) {
        this.value = value;
        image = new ImageView(new Image(getClass().getResource("/images/TableGames/Chips/chip" + value + ".png").toExternalForm(), true));
        image.setFitWidth(size);
        image.setFitHeight(size);

        if (isButton) {
            button = new Button();
            button.setGraphic(image);
            button.setStyle("-fx-background-color: transparent;");
            button.setLayoutX(xPos);
            button.setLayoutY(yPos);
        } else {
            image.setLayoutX(xPos);
            image.setLayoutY(yPos);
        }
    }

    public Chip(int value, int size) {
        this.value = value;
        image = new ImageView(new Image(getClass().getResource("/images/TableGames/Chips/chip" + value + ".png").toExternalForm(), true));
        image.setFitWidth(size);
        image.setFitHeight(size);
    }
}

