package com.controller.page;

import com.application.GameManager;
import com.application.configuration.CasinoConfiguration;
import com.application.configuration.GameSettings;
import com.basis.page.CashierPage;
import com.basis.transaction.Transaction;
import com.controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.TextFormatter;

public class CashierPageController extends Controller {
    private CashierPage cashierPage;

    public CashierPageController() {
        initializeScene();
        setupEventHandlers();
    }

    @Override
    protected void setupEventHandlers() {
        handleAmountField();
        handleDeposit();
        handleWithdraw();
        handleExit();
    }

    @Override
    protected void initializeScene() {
        cashierPage = new CashierPage();

        scene = new Scene(cashierPage.getMainLayout(), GameSettings.getInstance().getWindowWidth(), GameSettings.getInstance().getWindowHeight());
    }

    @Override
    public void showScene() {
        GameManager.getInstance().setMainScene(scene);
        GameManager.getInstance().getMainStage().setTitle("Casino Engine - Cashier");
    }

    private void handleDeposit() {
        cashierPage.getDepositButton().setOnAction(event -> {
            if (Integer.parseInt(cashierPage.getAmountField().getText()) > CasinoConfiguration.MAX_DEPOSIT) {
                cashierPage.showErrorMessage("Maximum deposit allowed is " + CasinoConfiguration.MAX_DEPOSIT);
                return;
            }
            if (Integer.parseInt(cashierPage.getAmountField().getText()) < CasinoConfiguration.MIN_DEPOSIT) {
                cashierPage.showErrorMessage("Minimum deposit amount is: " + CasinoConfiguration.MIN_DEPOSIT);
                return;
            }
            GameManager.getInstance().navigateTo(new PaymentPageController(Double.parseDouble(cashierPage.getAmountField().getText()),
                    Transaction.Type.DEPOSIT));
        });
    }

    private void handleWithdraw() {
        cashierPage.getWithdrawButton().setOnAction(event -> {
            if (Integer.parseInt(cashierPage.getAmountField().getText()) > CasinoConfiguration.MAX_WITHDRAW) {
                cashierPage.showErrorMessage("Maximum withdraw allowed is " + CasinoConfiguration.MAX_WITHDRAW);
                return;
            }
            if (Integer.parseInt(cashierPage.getAmountField().getText()) < CasinoConfiguration.MIN_WITHDRAW) {
                cashierPage.showErrorMessage("Minimum withdraw amount is: " + CasinoConfiguration.MIN_WITHDRAW);
                return;
            }
            GameManager.getInstance().navigateTo(new PaymentPageController(Double.parseDouble(cashierPage.getAmountField().getText()),
                    Transaction.Type.WITHDRAW));
        });
    }

    private void handleExit() {
        cashierPage.getExitButton().setOnAction(event -> {
            GameManager.getInstance().navigateTo(new MenuPageController());
        });
    }

    private void handleAmountField(){
        cashierPage.getAmountField().setTextFormatter(new TextFormatter<>(input -> {
            if(input.getControlNewText().matches("[0-9]*"))
                return input;
            return null;
        }));
    }
}
