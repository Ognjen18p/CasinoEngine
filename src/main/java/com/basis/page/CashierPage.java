package com.basis.page;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CashierPage extends Page {
    private Label enterAmountLabel;
    private Button depositButton;
    private Button withdrawButton;
    private Button exitButton;
    private TextField amountField;

    public Label getEnterAmountLabel() {
        return enterAmountLabel;
    }

    public Button getDepositButton() {
        return depositButton;
    }

    public Button getWithdrawButton() {
        return withdrawButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

    public TextField getAmountField() {
        return amountField;
    }

    public CashierPage() {
        super();
    }

    @Override
    protected void initializeElements() {
        /// !!! Header !!!
        titleLabel = new Label("Cashier");
        headerBox = new HBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().add(titleLabel);

        /// !!! Content !!!
        enterAmountLabel = new Label("Enter amount:");

        amountField = new TextField();
        amountField.setMaxWidth(300);

        depositButton = new Button("Deposit");
        depositButton.setPrefWidth(140);

        withdrawButton = new Button("Withdraw");
        withdrawButton.setPrefWidth(140);

        HBox buttonBox = new HBox(20, depositButton, withdrawButton);
        buttonBox.setAlignment(Pos.CENTER);

        exitButton = new Button("Back");
        exitButton.setPrefWidth(140);

        errorMessageLabel = new Label("");

        contentBox = new VBox(15);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setMaxWidth(350);
        contentBox.getChildren().addAll(enterAmountLabel, amountField, buttonBox, exitButton, errorMessageLabel);

        /// !!! Footer !!!
        footerLabel = new Label("© 2026 Casino Engine. All rights reserved.");
        footerBox = new HBox(10);
        footerBox.setAlignment(Pos.CENTER);
        footerBox.getChildren().add(footerLabel);

        mainLayout = new BorderPane();
        mainLayout.setTop(headerBox);
        mainLayout.setCenter(contentBox);
        mainLayout.setBottom(footerBox);
    }
}
