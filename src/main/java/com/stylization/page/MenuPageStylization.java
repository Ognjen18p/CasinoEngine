package com.stylization.page;

import com.basis.page.MenuPage;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuPageStylization {
    private MenuPage menuPage;

    public MenuPageStylization(MenuPage menuPage) {
        this.menuPage = menuPage;
        styleMenuPage();
    }

    public void styleMenuPage() {
        styleTopBar();
        styleLeftPanel();
        styleCenterPanel();
        styleGameButtons();
        styleMainLayout();
    }

    private void styleTopBar() {
        menuPage.getTopBar().setPadding(new Insets(15, 20, 15, 20));
        menuPage.getTopBar().setStyle(
                "-fx-background-color: #1a1a1a;" +
                        "-fx-border-color: #333333;" +
                        "-fx-border-width: 0 0 2 0;"
        );

        menuPage.getBalanceLabel().setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #4CAF50;" +
                        "-fx-padding: 5 15 5 15;"
        );

        // Buttons styling
        String buttonStyle =
                "-fx-background-color: #4CAF50;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 8 20 8 20;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand;";

        String buttonHoverStyle =
                "-fx-background-color: #45a049;";

        menuPage.getDepositButton().setStyle(buttonStyle);
        menuPage.getDepositButton().setOnMouseEntered(e ->
                menuPage.getDepositButton().setStyle(buttonStyle + buttonHoverStyle));
        menuPage.getDepositButton().setOnMouseExited(e ->
                menuPage.getDepositButton().setStyle(buttonStyle));

        menuPage.getChatButton().setStyle(buttonStyle);
        menuPage.getChatButton().setOnMouseEntered(e ->
                menuPage.getChatButton().setStyle(buttonStyle + buttonHoverStyle));
        menuPage.getChatButton().setOnMouseExited(e ->
                menuPage.getChatButton().setStyle(buttonStyle));

        String logoutButtonStyle =
                "-fx-background-color: #f44336;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 8 20 8 20;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand;";

        String logoutHoverStyle =
                "-fx-background-color: #da190b;";

        menuPage.getLogoutButton().setStyle(logoutButtonStyle);
        menuPage.getLogoutButton().setOnMouseEntered(e ->
                menuPage.getLogoutButton().setStyle(logoutButtonStyle + logoutHoverStyle));
        menuPage.getLogoutButton().setOnMouseExited(e ->
                menuPage.getLogoutButton().setStyle(logoutButtonStyle));
    }

    private void styleLeftPanel() {
        menuPage.getLeftPanel().setPadding(new Insets(20));
        menuPage.getLeftPanel().setStyle(
                "-fx-background-color: #2c2c2c;" +
                        "-fx-border-color: #333333;" +
                        "-fx-border-width: 0 2 0 0;"
        );

        // Title label
        menuPage.getTitleLabel().setStyle(
                "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-padding: 0 0 10 0;"
        );

        // Game list view
        menuPage.getGameListView().setStyle(
                "-fx-background-color: #1a1a1a;" +
                        "-fx-border-color: #4CAF50;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );

        menuPage.getGameListView().setMinHeight(300);
    }

    private void styleCenterPanel() {
        menuPage.getCenterPanel().setPadding(new Insets(30));
        menuPage.getCenterPanel().setStyle(
                "-fx-background-color: #1a1a1a;"
        );

        // Error message label
        menuPage.getErrorMessageLabel().setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-text-fill: #ff6b6b;" +
                        "-fx-padding: 10 0 10 0;"
        );

    }

    private void styleGameButtons() {
        String imageButtonStyle =
                "-fx-background-color: linear-gradient(to bottom, #2c2c2c, #1a1a1a);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-border-color: #4CAF50;" +
                        "-fx-border-width: 3;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;";

        String imageButtonHoverStyle =
                "-fx-background-color: linear-gradient(to bottom, #4CAF50, #45a049);" +
                        "-fx-border-color: #66BB6A;";

        Image blackjackImage = new Image(getClass().getResource("/images/MenuImages/blackjack.png").toExternalForm());
        ImageView blackjackIcon = new ImageView(blackjackImage);
        blackjackIcon.setFitWidth(50);
        blackjackIcon.setFitHeight(70);
        menuPage.getBlackjackImageButton().setGraphic(blackjackIcon);
        menuPage.getBlackjackImageButton().setStyle(imageButtonStyle);
        menuPage.getBlackjackImageButton().setOnMouseEntered(e ->
                menuPage.getBlackjackImageButton().setStyle(imageButtonStyle + imageButtonHoverStyle));
        menuPage.getBlackjackImageButton().setOnMouseExited(e ->
                menuPage.getBlackjackImageButton().setStyle(imageButtonStyle));


        Image rouletteImage = new Image(getClass().getResource("/images/MenuImages/roulette.png").toExternalForm());
        ImageView rouletteIcon = new ImageView(rouletteImage);
        rouletteIcon.setFitWidth(50);
        rouletteIcon.setFitHeight(70);
        menuPage.getRouletteImageButton().setGraphic(rouletteIcon);
        menuPage.getRouletteImageButton().setStyle(imageButtonStyle);
        menuPage.getRouletteImageButton().setOnMouseEntered(e ->
                menuPage.getRouletteImageButton().setStyle(imageButtonStyle + imageButtonHoverStyle));
        menuPage.getRouletteImageButton().setOnMouseExited(e ->
                menuPage.getRouletteImageButton().setStyle(imageButtonStyle));


        Image slotImage = new Image(getClass().getResource("/images/MenuImages/slot.png").toExternalForm());
        ImageView slotIcon = new ImageView(slotImage);
        slotIcon.setFitHeight(50);
        slotIcon.setFitWidth(70);
        menuPage.getSlotImageButton().setGraphic(slotIcon);
        menuPage.getSlotImageButton().setStyle(imageButtonStyle);
        menuPage.getSlotImageButton().setOnMouseEntered(e ->
                menuPage.getSlotImageButton().setStyle(imageButtonStyle + imageButtonHoverStyle));
        menuPage.getSlotImageButton().setOnMouseExited(e ->
                menuPage.getSlotImageButton().setStyle(imageButtonStyle));
    }

    private void styleMainLayout() {
        menuPage.getBorderPane().setStyle(
                "-fx-background-color: #1a1a1a;"
        );
    }
}
