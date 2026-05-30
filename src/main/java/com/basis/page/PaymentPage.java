package com.basis.page;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class PaymentPage extends Page {
    private Label cardHolderLabel;
    private Label cardNumberLabel;
    private Label cvvLabel;
    private Label expiryLabel;
    private Button confirmButton;
    private Button backButton;
    private TextField cardHolderName;
    private TextField cardNumberField;
    private TextField cvvField;
    private ComboBox<Integer> monthExpiryField;
    private ComboBox<Integer> yearExpiryField;

    public Label getCardHolderLabel() {
        return cardHolderLabel;
    }

    public Label getCardNumberLabel() {
        return cardNumberLabel;
    }

    public Label getCvvLabel() {
        return cvvLabel;
    }

    public Label getExpiryLabel() {
        return expiryLabel;
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public TextField getCardHolderNameField() {
        return cardHolderName;
    }

    public TextField getCardNumberField() {
        return cardNumberField;
    }

    public TextField getCvvField() {
        return cvvField;
    }

    public ComboBox<Integer> getMonthExpiryField() {
        return monthExpiryField;
    }

    public ComboBox<Integer> getYearExpiryField() {
        return yearExpiryField;
    }

    public PaymentPage() {
        super();
    }

    @Override
    public void initializeElements() {
        /// !!! Header !!!
        titleLabel = new Label("Payment");
        headerBox = new HBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().add(titleLabel);

        /// !!! Content !!!
        cardHolderLabel = new Label("Cardholder Name:");
        cardHolderName = new TextField();
        cardHolderName.setPromptText("Name Lastname");
        cardHolderName.setMaxWidth(300);

        cardNumberLabel = new Label("Card Number:");
        cardNumberField = new TextField();
        cardNumberField.setPromptText("1234 5678 9123 4567");
        cardNumberField.setMaxWidth(300);

        cvvLabel = new Label("CVV:");
        cvvField = new TextField();
        cvvField.setPromptText("123");
        cvvField.setMaxWidth(300);

        expiryLabel = new Label("Expiry Date:");

        monthExpiryField = new ComboBox<>();
        monthExpiryField.setMaxWidth(300);
        for (int month = 1; month <= 12; month++)
            monthExpiryField.getItems().add(month);
        monthExpiryField.setValue(LocalDate.now().getMonth().getValue());

        yearExpiryField = new ComboBox<>();
        yearExpiryField.setMaxWidth(300);
        for (int year = LocalDate.now().getYear() - 10; year <= LocalDate.now().getYear() + 10; year++)
            yearExpiryField.getItems().add(year);
        yearExpiryField.setValue(LocalDate.now().getYear());

        confirmButton = new Button("Confirm");
        confirmButton.setPrefWidth(300);

        backButton = new Button("Back");
        backButton.setPrefWidth(300);

        errorMessageLabel = new Label("");

        contentBox = new VBox(10);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setMaxWidth(350);
        contentBox.getChildren().addAll(
                cardHolderLabel, cardHolderName,
                cardNumberLabel, cardNumberField,
                cvvLabel, cvvField,
                expiryLabel,
                monthExpiryField,
                yearExpiryField,
                errorMessageLabel,
                confirmButton,
                backButton
        );

        /// !!! Footer !!!
        footerLabel = new Label("© 2026 Casino Engine. All rights reserved.");
        footerBox = new HBox(10);
        footerBox.setAlignment(Pos.CENTER);
        footerBox.getChildren().add(footerLabel);

        /// !!! Main !!!
        mainLayout = new BorderPane();
        mainLayout.setTop(headerBox);
        mainLayout.setCenter(contentBox);
        mainLayout.setBottom(footerBox);
    }

}
