package com.basis.game.table_game.roulette;

import com.basis.game.essentials.Vector2;
import javafx.scene.layout.Pane;

import java.util.*;

public class Layout {

    private final Pane mainPane = new Pane();
    private final ArrayList<Field> layoutFields = new ArrayList<>();
    private final ArrayList<Field> numberFields = new ArrayList<>();
    private Field zero = null;
    private Vector2 fieldSize;
    private final Vector2 position;

    public Vector2 getFieldSize() {
        return fieldSize;
    }

    public void setFieldSize(Vector2 fieldSize) {
        this.fieldSize = fieldSize;
    }

    public ArrayList<Field> getLayoutFields() {
        return layoutFields;
    }

    public Pane getMainPane() {
        return mainPane;
    }

    public Layout(Vector2 fieldSize, Vector2 position) {
        this.fieldSize = fieldSize;
        this.position = position;
        initialize();
    }

    private void initialize() {
        mainPane.setLayoutX(position.getX());
        mainPane.setLayoutY(position.getY());

        addZero();
        addNumbers();
        addOutsideBets();
        addConnectedFields();

    }

    private void addZero() {
        zero = new Field(Field.FieldType.ZERO, fieldSize,
                new Vector2(-fieldSize.getX(), 0));
        layoutFields.add(zero);
        mainPane.getChildren().add(zero.getPane());
    }

    private void addNumbers() {
        for (int row = 0; row < 3; row++) {
            int number = 3 - row;
            for (int column = 0; column < 12; column++) {
                Vector2 nPosition = new Vector2(fieldSize.getX() * column, fieldSize.getY() * row);
                Field numberField = new Field(Field.FieldType.NUMBER, fieldSize, nPosition);
                numberField.number(number);
                layoutFields.add(numberField);
                numberFields.add(numberField);
                mainPane.getChildren().add(numberField.getPane());
                number += 3;
            }
        }
    }

    private void addOutsideBets() {
        /// DOZEN
        Field.FieldType[] dozenTypes = {Field.FieldType.FIRST_DOZEN, Field.FieldType.SECOND_DOZEN, Field.FieldType.THIRD_DOZEN};
        for (int nDozen = 0; nDozen < dozenTypes.length; nDozen++) {
            Vector2 nPosition = new Vector2(fieldSize.getX() * 4 * nDozen, fieldSize.getY() * 3);
            ArrayList<Field> connected = new ArrayList<>();
            for (int n = nDozen * 12 + 1; n <= 12 * (nDozen + 1); n++) {
                for (Field numberField : numberFields) {
                    if (numberField.getWinValues().getFirst() == n) {
                        connected.add(numberField);
                    }
                }
            }
            Field dozen = new Field(dozenTypes[nDozen], connected, fieldSize, nPosition);
            layoutFields.add(dozen);
            mainPane.getChildren().add(dozen.getButton());
        }

        /// HALFS
        Field.FieldType[] halfTypes = {
                Field.FieldType.FIRST_HALF, Field.FieldType.EVEN,
                Field.FieldType.RED, Field.FieldType.BLACK,
                Field.FieldType.ODD, Field.FieldType.SECOND_HALF
        };
        for (int nHalf = 0; nHalf < halfTypes.length; nHalf++) {
            Vector2 nPosition = new Vector2(fieldSize.getX() * 2 * nHalf, fieldSize.getY() * 4);
            ArrayList<Field> connected = new ArrayList<>();
            connected = switch (halfTypes[nHalf]) {
                case FIRST_HALF -> connectedNumbers(1, 19, 1);
                case SECOND_HALF -> connectedNumbers(19, 37, 1);
                case RED -> connectedNumbers(Field.RED_NUMBERS);
                case BLACK -> connectedNumbers(Field.BLACK_NUMBERS);
                case ODD -> connectedNumbers(1, 36, 2);
                case EVEN -> connectedNumbers(2, 37, 2);
                default -> connected;
            };
            Field half = new Field(halfTypes[nHalf], connected, fieldSize, nPosition);
            layoutFields.add(half);
            mainPane.getChildren().add(half.getPane());
        }

        /// COLUMNS
        Field.FieldType[] columnTypes = {
                Field.FieldType.FIRST_COLUMN,
                Field.FieldType.SECOND_COLUMN,
                Field.FieldType.THIRD_COLUMN
        };
        for (int nColumn = 0; nColumn < columnTypes.length; nColumn++) {
            Vector2 nPosition = new Vector2(fieldSize.getX() * 12, fieldSize.getY() * nColumn);
            ArrayList<Field> connected;
            connected = connectedNumbers(3 - nColumn, 37 - nColumn, 3);
            Field column = new Field(columnTypes[nColumn], connected, fieldSize, nPosition);
            layoutFields.add(column);
            mainPane.getChildren().add(column.getPane());
        }
    }

    private ArrayList<Field> connectedNumbers(int from, int to, int increment) {
        ArrayList<Field> connected = new ArrayList<>();
        for (int n = from; n < to; n += increment) {
            for (Field numberField : numberFields) {
                if (numberField.getWinValues().getFirst() == n)
                    connected.add(numberField);
            }
        }
        return connected;
    }

    private ArrayList<Field> connectedNumbers(ArrayList<Integer> in) {
        ArrayList<Field> connected = new ArrayList<>();
        for (int n : in) {
            for (Field numberField : numberFields) {
                if (numberField.getWinValues().getFirst() == n)
                    connected.add(numberField);
            }
        }
        return connected;
    }

    private void addConnectedFields() {

        int currentNumber = 0;
        for (int rowNumber = 0; rowNumber < 3; rowNumber++) {
            for (int columnNumber = 0; columnNumber < 12; columnNumber++) {
                Vector2 numberPosition = new Vector2(numberFields.get(currentNumber).getPosition().getX(), numberFields.get(currentNumber).getPosition().getY());

                /// SixLine & Street
                if (rowNumber != 1) {
                    ArrayList<Field> streetList;
                    ArrayList<Field> sixLineList = null;
                    Vector2 streetPosition;
                    Vector2 sixLinePosition;
                    if (rowNumber == 0) {
                        streetList = new ArrayList<>(List.of(numberFields.get(currentNumber), numberFields.get(currentNumber + 12), numberFields.get(currentNumber + 24)));
                        streetPosition = new Vector2(numberPosition.getX() + fieldSize.getX() / 2 - fieldSize.getX() / 3, numberPosition.getY() - fieldSize.getY() / 3);

                        if (columnNumber == 0)
                            sixLineList = new ArrayList<>(List.of(numberFields.get(currentNumber), numberFields.get(currentNumber + 12), numberFields.get(currentNumber + 24), zero));
                        else
                            sixLineList = new ArrayList<>(List.of(numberFields.get(currentNumber), numberFields.get(currentNumber - 1),
                                    numberFields.get(currentNumber + 11), numberFields.get(currentNumber + 12),
                                    numberFields.get(currentNumber + 23), numberFields.get(currentNumber + 24)));
                        sixLinePosition = new Vector2(numberPosition.getX() - fieldSize.getX() / 3, numberPosition.getY() - fieldSize.getY() / 3);
                    } else {
                        streetList = new ArrayList<>(List.of(numberFields.get(currentNumber), numberFields.get(currentNumber - 12), numberFields.get(currentNumber - 24)));
                        streetPosition = new Vector2(numberPosition.getX() + fieldSize.getX() / 2 - fieldSize.getX() / 3, numberPosition.getY() + fieldSize.getY() - fieldSize.getY() / 3);

                        if (columnNumber == 0)
                            sixLineList = new ArrayList<>(List.of(numberFields.get(currentNumber), numberFields.get(currentNumber - 12), numberFields.get(currentNumber - 24), zero));
                        else
                            sixLineList = new ArrayList<>(List.of(numberFields.get(currentNumber), numberFields.get(currentNumber - 1),
                                    numberFields.get(currentNumber - 12), numberFields.get(currentNumber - 13),
                                    numberFields.get(currentNumber - 24), numberFields.get(currentNumber - 25)));
                        sixLinePosition = new Vector2(numberPosition.getX() - fieldSize.getX() / 3, numberPosition.getY() + fieldSize.getY() - fieldSize.getY() / 3);
                    }
                    Field street = new Field(Field.FieldType.STREET, streetList, streetPosition);
                    Field sixLine = new Field(Field.FieldType.SIX_LINE, sixLineList, sixLinePosition);
                    layoutFields.add(sixLine);
                    mainPane.getChildren().add(sixLine.getPane());
                    layoutFields.add(street);
                    mainPane.getChildren().add(street.getPane());
                }

                /// Corner
                if (columnNumber < 11 && rowNumber < 2) {
                    if (columnNumber == 0) {
                        ArrayList<Field> cornerListZero = new ArrayList<>(List.of(numberFields.get(currentNumber), numberFields.get(currentNumber + 12), zero));
                        Vector2 positionZero = new Vector2(numberPosition.getX() - fieldSize.getX() / 3, numberPosition.getY() + fieldSize.getY() - fieldSize.getY() / 3);
                        Field cornerZero = new Field(Field.FieldType.SPLIT, cornerListZero, positionZero);
                        layoutFields.add(cornerZero);
                        mainPane.getChildren().add(cornerZero.getPane());
                    }
                    ArrayList<Field> cornerList = new ArrayList<>(List.of(numberFields.get(currentNumber), numberFields.get(currentNumber + 1),
                            numberFields.get(currentNumber + 12), numberFields.get(currentNumber + 13)));
                    Vector2 position = new Vector2(numberPosition.getX() + fieldSize.getX() - fieldSize.getX() / 3, numberPosition.getY() + fieldSize.getY() - fieldSize.getY() / 3);
                    Field corner = new Field(Field.FieldType.CORNER, cornerList, position);
                    layoutFields.add(corner);
                    mainPane.getChildren().add(corner.getPane());
                }

                /// Split
                if (columnNumber < 11) {
                    if (columnNumber == 0) {
                        ArrayList<Field> splitRightListZero = new ArrayList<>(List.of(numberFields.get(currentNumber), zero));
                        Vector2 positionZero = new Vector2(numberPosition.getX() - fieldSize.getX() / 3, numberPosition.getY() + fieldSize.getY() / 2 - fieldSize.getY() / 3);
                        Field splitRight = new Field(Field.FieldType.SPLIT, splitRightListZero, positionZero);
                        layoutFields.add(splitRight);
                        mainPane.getChildren().add(splitRight.getPane());
                    }
                    ArrayList<Field> splitRightList = new ArrayList<>(List.of(numberFields.get(currentNumber), numberFields.get(currentNumber + 1)));
                    Vector2 position = new Vector2(numberPosition.getX() + fieldSize.getX() - fieldSize.getX() / 3, numberPosition.getY() + fieldSize.getY() / 2 - fieldSize.getY() / 3);
                    Field splitRight = new Field(Field.FieldType.SPLIT, splitRightList, position);
                    layoutFields.add(splitRight);
                    mainPane.getChildren().add(splitRight.getPane());
                }
                if (rowNumber < 2) {
                    ArrayList<Field> splitDownList = new ArrayList<>(List.of(numberFields.get(currentNumber), numberFields.get(currentNumber + 12)));
                    Vector2 position = new Vector2(numberPosition.getX() + fieldSize.getX() / 2 - fieldSize.getX() / 3, numberPosition.getY() + fieldSize.getY() - fieldSize.getY() / 3);
                    Field splitDown = new Field(Field.FieldType.SPLIT, splitDownList, position);
                    layoutFields.add(splitDown);
                    mainPane.getChildren().add(splitDown.getPane());
                }
                currentNumber++;
            }
        }
    }

}