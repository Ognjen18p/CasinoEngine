package com.basis.game.table_game.roulette;

import com.application.utilities.Vector2;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Field {

    public enum FieldType {
        ZERO, NUMBER, SPLIT, STREET, CORNER, SIX_LINE, FIRST_DOZEN, SECOND_DOZEN, THIRD_DOZEN,
        FIRST_COLUMN, SECOND_COLUMN, THIRD_COLUMN, FIRST_HALF, SECOND_HALF, EVEN, ODD, RED, BLACK
    }

    public static final ArrayList<Integer> RED_NUMBERS = new ArrayList<>(
            List.of(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36)
    );
    public static final ArrayList<Integer> BLACK_NUMBERS = new ArrayList<>(
            List.of(2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35)
    );
    private boolean isSelected;
    private int winMultiplier;
    private ArrayList<Integer> winValues;

    private final FieldType fieldType;
    private final Vector2 position;
    private final Vector2 size;
    private ArrayList<Field> connectedFields;

    private Button button;
    private StackPane stackPane;

    public boolean isSelected() {
        return isSelected;
    }

    public int getWinMultiplier() {
        return winMultiplier;
    }

    public ArrayList<Integer> getWinValues() {
        return winValues;
    }

    public ArrayList<Field> getConnectedFields() {
        return connectedFields;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getSize() {
        return size;
    }

    public Field(FieldType fieldType, ArrayList<Field> connectedFields, Vector2 position) {
        this.fieldType = fieldType;
        this.connectedFields = connectedFields;
        this.position = position;
        size = new Vector2(25, 32);
        winValues = new ArrayList<>();
        connectedBet();
        connectedValues();
    }

    public Field(FieldType fieldType, ArrayList<Field> connectedFields, Vector2 size, Vector2 position) {
        this.size = size;
        this.fieldType = fieldType;
        this.connectedFields = connectedFields;
        this.position = position;
        winValues = new ArrayList<>();
        changeType();
        connectedValues();
    }

    public Field(FieldType fieldType, Vector2 size, Vector2 position) {
        this.fieldType = fieldType;
        this.size = size;
        this.position = position;
        winValues = new ArrayList<>();
        changeType();
    }

    private void changeType() {
        switch (fieldType) {
            case ZERO:
                zero();
                break;
            case FIRST_DOZEN:
                dozen("1st 12");
                break;
            case SECOND_DOZEN:
                dozen("2nd 12");
                break;
            case THIRD_DOZEN:
                dozen("3rd 12");
                break;
            case FIRST_COLUMN, SECOND_COLUMN, THIRD_COLUMN:
                twoToOne();
                break;
            case FIRST_HALF:
                half("1-18");
                break;
            case SECOND_HALF:
                half("19-36");
                break;
            case EVEN:
                half("Even");
                break;
            case ODD:
                half("Odd");
                break;
            case RED:
                colorBet(Color.RED);
                break;
            case BLACK:
                colorBet(Color.BLACK);
                break;
        }
    }

    private void connectedValues() {
        for (Field field : connectedFields) {
            winValues.addAll(field.getWinValues());
        }
    }

    private void zero() {
        button = new Button();
        button.getStyleClass().add("number");
        button.setLayoutX(position.getX());
        button.setLayoutY(position.getY());
        button.setPrefSize(size.getX(), size.getY() * 3);
        Text text = new Text("0");
        text.setFill(Color.WHITE);
        text.setRotate(-90);
        button.setGraphic(text);
        winValues.add(0);
        winMultiplier = 36;
    }

    public void number(int number) {
        button = new Button();
        button.getStyleClass().add("number");
        button.setPrefSize(size.getX(), size.getY());

        Label numberLabel = new Label(Integer.toString(number));
        numberLabel.setAlignment(Pos.CENTER);
        numberLabel.setTextFill(Color.WHITE);
        numberLabel.getStyleClass().add(RED_NUMBERS.contains(number) ? "red-number" : "black-number");
        numberLabel.setRotate(-90);
        numberLabel.setPrefSize(size.getY(), size.getX());
        numberLabel.setScaleY(0.8);
        numberLabel.setMouseTransparent(true);

        stackPane = new StackPane();
        stackPane.setPrefSize(size.getX(), size.getY());
        stackPane.setLayoutX(position.getX());
        stackPane.setLayoutY(position.getY());
        stackPane.getChildren().addAll(button, numberLabel);
        winValues.add(number);
        winMultiplier = 36;
    }

    private void dozen(String labelText) {
        button = new Button(labelText);
        button.getStyleClass().add("outside-bet");
        button.setLayoutX(position.getX());
        button.setLayoutY(position.getY());
        button.setPrefSize(size.getX() * 4, size.getY());
        winMultiplier = 3;
    }

    private void twoToOne() {
        button = new Button();
        button.getStyleClass().add("outside-bet");
        button.setLayoutX(position.getX());
        button.setLayoutY(position.getY());
        button.setPrefSize(size.getX(), size.getY());
        Text twoToOneText = new Text("2to1");
        twoToOneText.setFill(Color.WHITE);
        twoToOneText.setRotate(-90);
        button.setGraphic(twoToOneText);
        winMultiplier = 3;
    }

    private void half(String labelText) {
        button = new Button(labelText);
        button.getStyleClass().add("outside-bet");
        button.setLayoutX(position.getX());
        button.setLayoutY(position.getY());
        button.setPrefSize(size.getX() * 2, size.getY());
        winMultiplier = 2;
    }

    private void colorBet(Color color) {
        button = new Button();
        button.getStyleClass().add("outside-bet");
        button.setPrefSize(size.getX() * 2, size.getY());

        Polygon rhomb = new Polygon(0.0, -15.0, 25.0, 0.0, 0.0, 15.0, -25.0, 0.0);
        rhomb.setFill(color);
        rhomb.setMouseTransparent(true);

        stackPane = new StackPane();
        stackPane.setPrefSize(size.getX() * 2, size.getY());
        stackPane.setLayoutX(position.getX());
        stackPane.setLayoutY(position.getY());
        stackPane.getChildren().addAll(button, rhomb);
        winMultiplier = 2;
    }

    private void connectedBet() {
        button = new Button();
        button.getStyleClass().add("split-bet");
        button.setLayoutX(position.getX());
        button.setLayoutY(position.getY());
        button.setPrefSize(size.getX(), size.getY());
        switch (fieldType) {
            case SPLIT -> winMultiplier = 18;
            case STREET -> winMultiplier = 12;
            case CORNER -> winMultiplier = 9;
            case SIX_LINE -> winMultiplier = 6;
        }
    }

    public Button getButton() {
        return button;
    }

    public Node getPane() {
        return stackPane != null ? stackPane : button;
    }

    public FieldType getFieldType() {
        return fieldType;
    }
}