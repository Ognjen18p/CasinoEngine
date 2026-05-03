package com.controller.page;

import com.application.configuration.CasinoConfiguration;
import com.application.configuration.GameSettings;
import com.basis.page.DepositPage;
import com.controller.Controller;
import com.application.GameManager;
import javafx.scene.Scene;

public class DepositPageController extends Controller {
    private DepositPage depositPage;

    public DepositPageController() {
        initializeScene();
        setupEventHandlers();
    }

    @Override
    protected void setupEventHandlers() {
        handleDeposit();
        handleWithdraw();
    }

    @Override
    protected void initializeScene() {
        depositPage = new DepositPage();

        scene = new Scene(depositPage.getMainLayout(), GameSettings.getInstance().getWindowWidth(), GameSettings.getInstance().getWindowHeight());

    }

    @Override
    public void showScene() {

    }

    private void handleDeposit() {
        depositPage.getDepositButton().setOnAction(event -> {
            if (Integer.parseInt(depositPage.getDepositAmountField().getText()) > CasinoConfiguration.MAX_DEPOSIT) {
                depositPage.showErrorMessage("Maximum deposit allowed is " + CasinoConfiguration.MAX_DEPOSIT);
                return;
            }
            if (Integer.parseInt(depositPage.getDepositAmountField().getText()) < CasinoConfiguration.MIN_DEPOSIT) {
                depositPage.showErrorMessage("Minimum deposit amount is: " + CasinoConfiguration.MIN_DEPOSIT);
                return;
            }

        });
    }

    private void handleWithdraw() {
        depositPage.getWithdrawButton().setOnAction(event -> {
            if (Integer.parseInt(depositPage.getWithdrawAmountField().getText()) > CasinoConfiguration.MAX_WITHDRAW) {
                depositPage.showErrorMessage("Maximum withdraw allowed is " + CasinoConfiguration.MAX_WITHDRAW);
                return;
            }
            if (Integer.parseInt(depositPage.getWithdrawAmountField().getText()) < CasinoConfiguration.MIN_WITHDRAW) {
                depositPage.showErrorMessage("Minimum withdraw amount is: " + CasinoConfiguration.MIN_WITHDRAW);
                return;
            }
        });
    }
}
