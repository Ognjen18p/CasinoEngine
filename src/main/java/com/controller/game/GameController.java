package com.controller.game;

import com.application.GameManager;
import com.basis.game.Game;
import com.controller.Controller;
import com.controller.page.CashierPageController;
import com.controller.page.MenuPageController;
import com.database.person.PlayerDAO;
import com.database.transaction.TransactionDAO;

public abstract class GameController extends Controller {
    protected PlayerDAO playerDAO;
    protected TransactionDAO transactionDAO;

    public GameController() {
        playerDAO = new PlayerDAO();
        transactionDAO = new TransactionDAO();
    }

    protected void handleExit(Game game) {
        game.getExitButton().setOnAction(event -> {
            GameManager.getInstance().setCurrentController(new MenuPageController());
        });
    }

    protected void handleDeposit(Game game) {
        game.getDepositButton().setOnAction(event -> {
            GameManager.getInstance().setCurrentController(new CashierPageController());
        });
    }

}
