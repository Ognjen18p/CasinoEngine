package com.basis.game.BlackJack;

import com.basis.game.Game.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class BlackJack extends Game {
    public enum BlackJackWinState {
        IN_PROGRESS,
        PLAYER_BLACKJACK,
        DEALER_BLACKJACK,
        PUSH,
        PLAYER_WINS,
        DEALER_WINS
    }

    public enum BlackJackPhaseState {
        DEAL,
        HIT_STAND
    }

    private BlackJackWinState blackJackWinState = BlackJackWinState.IN_PROGRESS;
    private BlackJackPhaseState blackJackPhaseState = BlackJackPhaseState.DEAL;

    private final int NUMBER_OF_RANKS = 13;
    private final int NUMBER_OF_SUITS = 4;
    private final int NUMBER_OF_DECKS = 6;
    private final int BLACKJACK = 21;
    private int playerScore = 0;
    private int dealerScore = 0;
    private int firstInDeck;
    private int playerFreeSlot;
    private int dealerFreeSlot;

    private Label placeYourBetsLabel;
    private Label playerScoreLabel;
    private Label dealerScoreLabel;
    private Button deal;
    private Button hit;
    private Button stand;
    private Card dealersFlippedCard;
    private ArrayList<Card> deck;
    private ArrayList<Card> dealerSlots;
    private ArrayList<Card> playerSlots;
    private ArrayList<Card> playerCards;
    private ArrayList<Card> dealerCards;
    private Button chipShopButton;

    public BlackJack() {
        initializeElements();
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
        playerScoreLabel.setText(playerScore + "");
    }

    public void setDealerScore(int dealerScore) {
        this.dealerScore = dealerScore;
        dealerScoreLabel.setText(dealerScore + "");
    }

    public void setPlayerFreeSlot(int playerFreeSlot) {
        this.playerFreeSlot = playerFreeSlot;
    }

    public void setDealerFreeSlot(int dealerFreeSlot) {
        this.dealerFreeSlot = dealerFreeSlot;
    }

    public int getNUMBER_OF_RANKS() {
        return NUMBER_OF_RANKS;
    }

    public int getNUMBER_OF_SUITS() {
        return NUMBER_OF_SUITS;
    }

    public int getBLACKJACK() {
        return BLACKJACK;
    }

    public int getNUMBER_OF_DECKS() {
        return NUMBER_OF_DECKS;
    }

    public BlackJackWinState getBlackJackWinState() {
        return blackJackWinState;
    }

    public void setBlackJackWinState(BlackJackWinState blackJackWinState) {
        this.blackJackWinState = blackJackWinState;

    }

    public BlackJackPhaseState getBlackJackPhaseState() {
        return blackJackPhaseState;
    }

    public void setBlackJackPhaseState(BlackJackPhaseState blackJackPhaseState) {
        this.blackJackPhaseState = blackJackPhaseState;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getDealerScore() {
        return dealerScore;
    }

    public Label getPlaceYourBetsLabel() {
        return placeYourBetsLabel;
    }

    public Label getPlayerScoreLabel() {
        return playerScoreLabel;
    }

    public Label getDealerScoreLabel() {
        return dealerScoreLabel;
    }

    public Button getDealButton() {
        return deal;
    }

    public Button getHitButton() {
        return hit;
    }

    public Button getStandButton() {
        return stand;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public Card getDealersFlippedCard() {
        return dealersFlippedCard;
    }

    public ArrayList<Card> getDealerSlots() {
        return dealerSlots;
    }

    public ArrayList<Card> getPlayerSlots() {
        return playerSlots;
    }

    public int getPlayerFreeSlot() {
        return playerFreeSlot;
    }

    public int getDealerFreeSlot() {
        return dealerFreeSlot;
    }

    public int getFirstInDeck() {
        return firstInDeck;
    }

    public ArrayList<Card> getPlayerCards() {
        return playerCards;
    }

    public ArrayList<Card> getDealerCards() {
        return dealerCards;
    }

    public Button getChipShopButton() {
        return chipShopButton;
    }

    public void setFirstInDeck(int firstInDeck) {
        this.firstInDeck = firstInDeck;
    }

    private void fillChipsSlots() {
        double padding = 100;
        for (int bettingValue : Chip.CHIP_VALUES) {
            Chip emptyChip = new Chip(bettingValue, padding, 400, false);
            owningChips.add(emptyChip);
            padding += 80;
        }
    }

    public void fillDeck() {
        double deckPosX = 900;
        double deckPosY = 100;
        for (int nDeck = 0; nDeck < NUMBER_OF_DECKS; nDeck++) {
            for (int nRank = 1; nRank < NUMBER_OF_RANKS; nRank++) {
                for (int nSuit = 0; nSuit < NUMBER_OF_SUITS; nSuit++) {
                    Card newCard = new Card(Card.Rank.values()[nRank], Card.Suit.values()[nSuit], deckPosX, deckPosY);
                    newCard.getImage().setRotate(20);
                    deck.add(newCard);
                    mainPane.getChildren().add(newCard.getImage());
                }
            }
        }
    }

    public void fillCardSlots() {
        double padding = 350;

        dealerSlots = new ArrayList<>();
        playerSlots = new ArrayList<>();

        for (int nCard = 0; nCard < BLACKJACK; nCard++) {
            Card card = new Card(padding, 150);
            padding += 20;
            dealerSlots.add(card);
        }
        padding = 300;
        for (int nCard = 0; nCard < BLACKJACK; nCard++) {
            Card card = new Card(padding, 250);
            padding += 20;
            playerSlots.add(card);
        }
    }

    @Override
    public void initializeElements() {
        super.initializeElements();

        fillCardSlots();
        deck = new ArrayList<>();
        playerCards = new ArrayList<>();
        dealerCards = new ArrayList<>();

        owningChips = FXCollections.observableArrayList();
        bettingChips = new ArrayList<>();

        bettingChips.add(new Chip(0, 100, 100, false));
        fillChipsSlots();

        playerFreeSlot = 0;
        dealerFreeSlot = 0;
        firstInDeck = 0;

        placeYourBetsLabel = new Label("Place your bets please");
        placeYourBetsLabel.setTranslateX(500);
        placeYourBetsLabel.setTranslateY(30);

        playerScoreLabel = new Label("Player score: " + playerScore);
        playerScoreLabel.setTranslateX(300);
        playerScoreLabel.setTranslateY(170);

        dealerScoreLabel = new Label("Dealer score: " + dealerScore);
        dealerScoreLabel.setTranslateX(300);
        dealerScoreLabel.setTranslateY(70);

        winLabel = new Label("Win: " + win);
        winLabel.setTranslateX(120);
        winLabel.setTranslateY(500);

        totalWinLabel = new Label("Total win value: " + totalWin);
        totalWinLabel.setTranslateX(200);
        totalWinLabel.setTranslateY(250);

        lastWinLabel = new Label("Last win value: " + lastWin);
        lastWinLabel.setTranslateX(300);
        lastWinLabel.setTranslateY(350);

        betLabel = new Label(bet + " BET");
        betLabel.setTranslateX(100);
        betLabel.setTranslateY(350);

        balanceLabel = new Label(balance + " BALANCE");
        balanceLabel.setTranslateX(60);
        balanceLabel.setTranslateY(50);

        depositButton.setTranslateX(100);
        depositButton.setTranslateY(50);

        exitButton.setTranslateX(700);
        exitButton.setTranslateY(50);

        chipShopButton = new Button("Chip Shop");
        chipShopButton.setTranslateX(700);
        chipShopButton.setTranslateY(50);

        deal = new Button("Deal");
        deal.setTranslateX(300);
        deal.setTranslateY(500);

        hit = new Button("Hit");
        hit.setTranslateX(300);
        hit.setTranslateY(400);

        stand = new Button("Stand");
        stand.setTranslateX(500);
        stand.setTranslateY(400);

        hit.setVisible(false);
        stand.setVisible(false);
        deal.setVisible(false);

        dealersFlippedCard = new Card(new ImageView(new Image(getClass().getResource("/images/BlackJackImages/Cards/Flipped.png").toExternalForm())), null, null, -100, -100);

        mainPane.getChildren().addAll(depositButton, exitButton, chipShopButton, balanceLabel, winLabel, totalWinLabel, lastWinLabel, betLabel, placeYourBetsLabel, playerScoreLabel, dealerScoreLabel,
                deal, hit, stand, bettingChips.getFirst().getImage());

        blackJackPhaseState = BlackJackPhaseState.DEAL;
    }

}


