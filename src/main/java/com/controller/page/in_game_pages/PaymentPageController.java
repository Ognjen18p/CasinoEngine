package com.controller.page.in_game_pages;

import com.basis.game.essentials.GameSettings;
import com.basis.page.in_game_pages.PaymentPage;
import com.controller.Controller;
import com.manager.GameManager;
import javafx.scene.Scene;

public class PaymentPageController extends Controller {
    private PaymentPage paymentPage;

    @Override
    protected void setupEventHandlers() {

    }

    @Override
    protected void initializeScene() {
        paymentPage = new PaymentPage();
        scene = new Scene(paymentPage.getMainLayout(), GameSettings.getInstance().getWindowWidth(),  GameSettings.getInstance().getWindowHeight());
    }

    @Override
    public void showScene() {

    }
}
