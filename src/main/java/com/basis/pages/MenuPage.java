package com.basis.pages;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MenuPage extends Page {
    private Label balanceLabel;
    private Button logoutButton;
    private Button depositButton;
    private Button chatButton;
    private HBox topBar;

    private ListView<String> gameListView;
    private VBox gameImagesBox;

    private Button blackjackImageButton;
    private Button rouletteImageButton;
    private Button slotImageButton;

    private BorderPane borderPane;
    private VBox leftPanel;
    private VBox centerPanel;

    public Label getBalanceLabel() {
        return balanceLabel;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }

    public Button getDepositButton() {
        return depositButton;
    }

    public Button getChatButton() {
        return chatButton;
    }

    public ListView<String> getGameListView() {
        return gameListView;
    }

    public Button getBlackjackImageButton() {
        return blackjackImageButton;
    }

    public Button getRouletteImageButton() {
        return rouletteImageButton;
    }

    public Button getSlotImageButton() {
        return slotImageButton;
    }

    public HBox getTopBar() {
        return topBar;
    }

    public VBox getLeftPanel() {
        return leftPanel;
    }

    public VBox getCenterPanel() {
        return centerPanel;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void updateBalance(float balance) {
        balanceLabel.setText(String.format("Balance: $%.2f", balance));
    }

    protected void initializeElements() {
        balanceLabel = new Label("Balance: $0.00");
        logoutButton = new Button("Logout");
        depositButton = new Button("Deposit");
        chatButton = new Button("Chat with Agent");

        topBar = new HBox(15);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.getChildren().addAll(balanceLabel, depositButton, chatButton, logoutButton);

        gameListView = new ListView<>();
        gameListView.getItems().addAll("BlackJack", "Roulette", "Slot");
        gameListView.setPrefWidth(200);

        titleLabel = new Label("Select Game");
        leftPanel = new VBox(15);
        leftPanel.setAlignment(Pos.TOP_CENTER);
        leftPanel.getChildren().addAll(titleLabel, gameListView);
        leftPanel.setPrefWidth(250);


        blackjackImageButton = new Button("BlackJack");
        blackjackImageButton.setPrefSize(250, 200);
        blackjackImageButton.setContentDisplay(ContentDisplay.BOTTOM);

        rouletteImageButton = new Button("Roulette");
        rouletteImageButton.setPrefSize(250, 200);
        rouletteImageButton.setContentDisplay(ContentDisplay.BOTTOM);

        slotImageButton = new Button("Slot");
        slotImageButton.setPrefSize(250, 200);
        slotImageButton.setContentDisplay(ContentDisplay.BOTTOM);

        gameImagesBox = new VBox(20);
        gameImagesBox.setAlignment(Pos.CENTER);

        gameImagesBox.getChildren().addAll(blackjackImageButton, rouletteImageButton, slotImageButton);

        centerPanel = new VBox(30);
        centerPanel.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome to Casino Engine!");
        welcomeLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        errorMessageLabel = new Label("");
        footerLabel = new Label("© 2026 Casino Engine. All rights reserved.");

        centerPanel.getChildren().addAll(welcomeLabel, gameImagesBox, errorMessageLabel, footerLabel);

        borderPane = new BorderPane();
        borderPane.setTop(topBar);
        borderPane.setLeft(leftPanel);
        borderPane.setCenter(centerPanel);

        mainLayout = new VBox();
        mainLayout.getChildren().add(borderPane);
    }

    public MenuPage() {
        initializeElements();
    }

}
