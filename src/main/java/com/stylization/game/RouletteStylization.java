package com.stylization.game;

import com.basis.game.table_game.roulette.Field;
import com.basis.game.table_game.roulette.Roulette;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class RouletteStylization {
    private final Roulette roulette;

    private static final String GREEN_DARK = "#0B5E3A";
    private static final String GREEN_FIELD = "#1a7a4a";
    private static final String GREEN_HOVER = "#25a060";
    private static final String RED_FIELD = "#c0392b";
    private static final String RED_HOVER = "#e74c3c";
    private static final String BLACK_FIELD = "#1a1a1a";
    private static final String BLACK_HOVER = "#333333";
    private static final String GOLD = "#FFD700";
    private static final String GOLD_DARK = "#FFA500";
    private static final String SPLIT_COLOR = "rgba(255,255,255,0.15)";
    private static final String SPLIT_HOVER = "rgba(255,255,255,0.45)";

    public RouletteStylization(Roulette roulette) {
        this.roulette = roulette;
        style();
    }

    private void style() {
        styleMainPane();
        styleLabels();
        styleButtons();
        styleLayoutFields();
    }

    private void styleMainPane() {
        roulette.getMainPane().setBackground(new Background(
                new BackgroundFill(Color.web(GREEN_DARK), CornerRadii.EMPTY, Insets.EMPTY)
        ));
    }

    private void styleLabels() {
        String labelStyle =
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + GOLD + ";" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 8, 0, 0, 2);";

        roulette.getWinLabel().setStyle(labelStyle);
        roulette.getTotalWinLabel().setStyle(labelStyle);
        roulette.getLastWinLabel().setStyle(labelStyle);
        roulette.getBetLabel().setStyle(labelStyle);
        roulette.getBalanceLabel().setStyle(labelStyle);
    }

    private void styleButtons() {
        String base =
                "-fx-background-color: linear-gradient(to bottom, " + GOLD + ", " + GOLD_DARK + ");" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 28 10 28;" +
                        "-fx-background-radius: 8;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 6, 0, 0, 2);";

        String hover =
                "-fx-background-color: linear-gradient(to bottom, " + GOLD_DARK + ", #e67e00);";

        styleActionButton(roulette.getSpinButton(), 120, 45, base, hover);
        styleActionButton(roulette.getChipShopButton(), 110, 38, base, hover);
        styleActionButton(roulette.getDepositButton(), 90, 38, base, hover);
        styleActionButton(roulette.getExitButton(), 70, 38, base, hover);
    }

    private void styleActionButton(Button btn, double w, double h, String base, String hover) {
        btn.setPrefWidth(w);
        btn.setPrefHeight(h);
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(base + hover));
        btn.setOnMouseExited(e -> btn.setStyle(base));
    }

    private void styleLayoutFields() {
        roulette.getLayout().getLayoutFields().forEach(field -> {
            Button btn = field.getButton();
            if (btn == null) return;

            switch (field.getFieldType()) {
                case ZERO -> styleFieldButton(btn, GREEN_FIELD, GREEN_HOVER,
                        "-fx-text-fill: white; -fx-font-weight: bold;");

                case NUMBER -> {
                    boolean isRed = Field.RED_NUMBERS.contains(
                            field.getWinValues().isEmpty() ? -1 : field.getWinValues().getFirst());
                    String bg = isRed ? RED_FIELD : BLACK_FIELD;
                    String bgH = isRed ? RED_HOVER : BLACK_HOVER;
                    styleFieldButton(btn, bg, bgH,
                            "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px;");
                }

                case FIRST_DOZEN, SECOND_DOZEN, THIRD_DOZEN,
                     FIRST_HALF, SECOND_HALF,
                     FIRST_COLUMN, SECOND_COLUMN, THIRD_COLUMN,
                     EVEN, ODD, RED, BLACK -> {
                    styleSplitButton(btn, field, GREEN_FIELD, GREEN_HOVER,
                            "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px;");

                }

                case SPLIT, CORNER, STREET, SIX_LINE ->
                        styleSplitButton(btn, field, SPLIT_COLOR, SPLIT_HOVER, "-fx-background-radius: 50%;");

            }
        });
    }

    private void styleFieldButton(Button btn, String bg, String bgHover, String extra) {
        String base =
                "-fx-background-color: " + bg + ";" +
                        "-fx-border-color: rgba(255,255,255,0.25);" +
                        "-fx-border-width: 1;" +
                        "-fx-cursor: hand;" +
                        extra;
        String hover =
                "-fx-background-color: " + bgHover + ";" +
                        "-fx-border-color: " + GOLD + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-cursor: hand;" +
                        extra;
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(base));
    }

    private void styleSplitButton(Button btn, Field field, String bg, String bgHover, String extra) {
        String base =
                "-fx-background-color: " + bg + ";" +
                        "-fx-border-color: rgba(255,255,255,0.25);" +
                        "-fx-border-width: 1;" +
                        "-fx-cursor: hand;" +
                        extra;
        String hover =
                "-fx-background-color: " + bgHover + ";" +
                        "-fx-border-color: " + GOLD + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-cursor: hand;" +
                        extra;

        btn.setStyle(base);

        btn.setOnMouseEntered(e -> {
            btn.setStyle(hover);
            // Highlightuj samo connected fieldove ovog specificnog fielda
            if (field.getConnectedFields() == null) return;
            for (Field connected : field.getConnectedFields()) {
                if (connected == null || connected.getButton() == null) continue;
                // Sacuvaj originalni stil i primijeni hover
                connected.getButton().setUserData(connected.getButton().getStyle());
                connected.getButton().setStyle(
                        connected.getButton().getStyle() +
                                "-fx-border-color: " + GOLD + ";" +
                                "-fx-border-width: 2;"
                );
            }
        });

        btn.setOnMouseExited(e -> {
            btn.setStyle(base);
            // Vrati originalni stil svakog connected fielda
            if (field.getConnectedFields() == null) return;
            for (Field connected : field.getConnectedFields()) {
                if (connected == null || connected.getButton() == null) continue;
                Object savedStyle = connected.getButton().getUserData();
                if (savedStyle != null)
                    connected.getButton().setStyle(savedStyle.toString());
            }
        });
    }
}