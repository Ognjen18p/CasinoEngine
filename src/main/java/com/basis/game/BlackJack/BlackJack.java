package com.basis.game.BlackJack;

import com.basis.game.Game;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import main.GameManager;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class BlackJack extends Game {
    private final int NUMBER_OF_RANKS = 13;
    private final int NUMBER_OF_SUITS = 4;
    private int playerScore = 0;
    private int dealerScore = 0;
    private int firstInDeck;

    private Label placeYourBetsLabel;
    private Button deal;
    private Button hit;
    private Button stand;
    private ArrayList<Chip> bettingChips;
    private ArrayList<Card> deck;
    private Card dealersFlippedCard;
    private ArrayList<Card> dealerSlots;
    private ArrayList<Card> playerSlots;
    private int playerFreeSlot;
    private int dealerFreeSlot;

    public BlackJack() {
        initializeElements();
    }

    @Override
    protected void initializeElements() {
        super.initializeElements();

        dealerSlots = new ArrayList<>();
        double padding = 350;
        for(int nCard = 0; nCard < 21; nCard++) {
            Card card = new Card(new ImageView(new Image(getClass().getResource("/images/BlackJackImages/Cards/Flipped.png").toExternalForm())));
            card.getImage().setX(padding);
            card.getImage().setY(250);
            padding+=50;
            dealerSlots.add(card);
            mainPane.getChildren().add(dealerSlots.getLast().getImage());
        }
        playerSlots = new ArrayList<>();
        padding = 300;
        for(int nCard = 0; nCard < 21; nCard++) {
            Card card = new Card(new ImageView(new Image(getClass().getResource("/images/BlackJackImages/Cards/Flipped.png").toExternalForm())));
            card.getImage().setX(padding);
            card.getImage().setY(150);
            padding+=50;
            playerSlots.add(card);
            mainPane.getChildren().add(playerSlots.getLast().getImage());
        }
        deck = new ArrayList<>();
        bettingChips = new ArrayList<>();

        playerFreeSlot = 0;
        dealerFreeSlot = 0;
        firstInDeck = 0;

        placeYourBetsLabel = new Label("Place your bets please");
        placeYourBetsLabel.setTranslateX(500);
        placeYourBetsLabel.setTranslateY(70);

        totalWinLabel.setTranslateX(200);
        totalWinLabel.setTranslateY(250);

        lastWinLabel.setTranslateX(300);
        lastWinLabel.setTranslateY(250);

        depositButton.setTranslateX(100);
        depositButton.setTranslateY(50);

        exitButton.setTranslateX(700);
        exitButton.setTranslateY(50);

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

        dealersFlippedCard = new Card(new ImageView(new Image(getClass().getResource("/images/BlackJackImages/Cards/Flipped.png").toExternalForm())));

        fillBettingChips();

        mainPane.getChildren().addAll(placeYourBetsLabel, deal, hit, stand);
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public void setDealerScore(int dealerScore) {
        this.dealerScore = dealerScore;
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

    public int getPlayerScore() {
        return playerScore;
    }

    public int getDealerScore() {
        return dealerScore;
    }

    public Label getPlaceYourBetsLabel() {
        return placeYourBetsLabel;
    }

    public Button getDeal() {
        return deal;
    }

    public Button getHit() {
        return hit;
    }

    public Button getStand() {
        return stand;
    }

    public ArrayList<Chip> getBettingChips() {
        return bettingChips;
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

    public void setFirstInDeck(int firstInDeck) {
        this.firstInDeck = firstInDeck;
    }

    private void fillBettingChips() {
        int[] bettingValues = {10, 20, 50, 100, 500};

        double padding = 100;
        for (int bettingValue : bettingValues) {
            Chip nChip = new Chip(bettingValue, padding, 400, true);
            getBettingChips().add(nChip);
            getMainPane().getChildren().add(getBettingChips().getLast().getButton());
            padding += 80;
        }
    }

    public void fillDeck() {
        double deckPosX = 1000;
        double deckPosY = 100;
        for (int nDeck = 0; nDeck < 6; nDeck++) {
            for (int nRank = 0; nRank < 13; nRank++) {
                for (int nSuit = 0; nSuit < 4; nSuit++) {
                    Card newCard = new Card(Card.Rank.values()[nRank], Card.Suit.values()[nSuit], deckPosX, deckPosY);
                    newCard.getImage().setRotate(20);
                    getDeck().add(newCard);
                    getMainPane().getChildren().add(getDeck().getLast().getImage());
                }
            }
        }
    }
}


