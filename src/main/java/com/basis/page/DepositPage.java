package com.basis.page;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DepositPage extends Page {
    private Button depositButton;
    private Button withdrawButton;

    private Label depositLabel;
    private Label withdrawLabel;

    private TextField depositAmountField;
    private TextField withdrawAmountField;

    private TextField cardNumberField;
    private TextField cardExpiryField;

    private GridPane cardFieldsPane;

    public Button getDepositButton() {
        return depositButton;
    }

    public Button getWithdrawButton() {
        return withdrawButton;
    }

    public Label getDepositLabel() {
        return depositLabel;
    }

    public Label getWithdrawLabel() {
        return withdrawLabel;
    }

    public TextField getDepositAmountField() {
        return depositAmountField;
    }

    public TextField getWithdrawAmountField() {
        return withdrawAmountField;
    }

    public DepositPage() {
        super();
    }

    @Override
    protected void initializeElements() {

        depositButton = new Button("Deposit");
        depositButton.setLayoutX(0);
        withdrawButton = new Button("Withdraw");

        depositLabel = new Label("Deposit Amount:");
        withdrawLabel = new Label("Withdraw Amount:");

        depositAmountField = new TextField();
        depositAmountField.setPromptText("Enter deposit amount");

        withdrawAmountField = new TextField();
        withdrawAmountField.setPromptText("Enter withdraw amount");
    }
}
