package com.basis.game.table_game.blackjack;

import com.application.GameManager;
import com.basis.game.table_game.TableGame;
import com.application.utilities.Vector2;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class BlackJack extends TableGame {
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

    private final int BLACKJACK = 21;
    private int playerScore = 0;
    private int dealerScore = 0;
    private int playerFreeSlot;
    private int dealerFreeSlot;

    private Label placeYourBetsLabel;
    private Label playerScoreLabel;
    private Label dealerScoreLabel;
    private Button dealButton;
    private Button hitButton;
    private Button standButton;
    private PlayingCard dealersFlippedPlayingCard;
    private Deck deck;
    private ArrayList<PlayingCard> dealerSlots;
    private ArrayList<PlayingCard> playerSlots;
    private ArrayList<PlayingCard> playerCards;
    private ArrayList<PlayingCard> dealerCards;

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

    public Deck getDeck() {
        return deck;
    }

    public void setPlayerFreeSlot(int playerFreeSlot) {
        this.playerFreeSlot = playerFreeSlot;
    }

    public void setDealerFreeSlot(int dealerFreeSlot) {
        this.dealerFreeSlot = dealerFreeSlot;
    }


    public BlackJackWinState getBlackJackWinState() {
        return blackJackWinState;
    }

    public int getBLACKJACK() {
        return BLACKJACK;
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
        return dealButton;
    }

    public Button getHitButton() {
        return hitButton;
    }

    public Button getStandButton() {
        return standButton;
    }

    public PlayingCard getDealersFlippedCard() {
        return dealersFlippedPlayingCard;
    }

    public ArrayList<PlayingCard> getDealerSlots() {
        return dealerSlots;
    }

    public ArrayList<PlayingCard> getPlayerSlots() {
        return playerSlots;
    }

    public int getAndIncrementPlayerSlot() {
        int currentFreeSlot = playerFreeSlot;
        playerFreeSlot++;
        return currentFreeSlot;
    }

    public int getAndIncrementDealerSlot() {
        int currentFreeSlot = dealerFreeSlot;
        dealerFreeSlot++;
        return currentFreeSlot;
    }

    public ArrayList<PlayingCard> getPlayerCards() {
        return playerCards;
    }

    public ArrayList<PlayingCard> getDealerCards() {
        return dealerCards;
    }

    public Button getChipShopButton() {
        return chipShopButton;
    }

    @Override
    public void initializeElements() {
        super.initializeElements();

        fillCardSlots();
        playerCards = new ArrayList<>();
        dealerCards = new ArrayList<>();

        owningChips = FXCollections.observableArrayList();
        bettingChips = new ArrayList<>();

        bettingChips.add(new Chip(0, 100, 100, false));
        fillChipSlots(80, 100, 400);

        playerFreeSlot = 0;
        dealerFreeSlot = 0;

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

        totalWinLabel = new Label("Total win value: " + totalWins);
        totalWinLabel.setTranslateX(200);
        totalWinLabel.setTranslateY(250);

        lastWinLabel = new Label("Last win value: " + lastWin);
        lastWinLabel.setTranslateX(300);
        lastWinLabel.setTranslateY(350);

        betLabel = new Label(bet + " BET");
        betLabel.setTranslateX(100);
        betLabel.setTranslateY(350);

        balanceLabel = new Label(GameManager.getInstance().getCurrentPlayer().getBalance() + " BALANCE");
        balanceLabel.setTranslateX(60);
        balanceLabel.setTranslateY(50);

        depositButton.setTranslateX(200);
        depositButton.setTranslateY(50);
        depositButton.setPrefWidth(200);

        exitButton.setTranslateX(600);
        exitButton.setTranslateY(50);
        exitButton.setPrefWidth(200);

        chipShopButton = new Button("Chip Shop");
        chipShopButton.setTranslateX(800);
        chipShopButton.setTranslateY(50);

        dealButton = new Button("Deal");
        dealButton.setTranslateX(300);
        dealButton.setTranslateY(500);

        hitButton = new Button("Hit");
        hitButton.setTranslateX(300);
        hitButton.setTranslateY(400);

        standButton = new Button("Stand");
        standButton.setTranslateX(500);
        standButton.setTranslateY(400);

        hitButton.setVisible(false);
        standButton.setVisible(false);
        dealButton.setVisible(false);

        deck = new Deck(new Vector2(900, 100));
        deck.fill();

        dealersFlippedPlayingCard = new PlayingCard(new ImageView(new Image(getClass().getResource("/images/BlackJackImages/Cards/Flipped.png").toExternalForm())), null, null, -100, -100);

        mainPane.getChildren().addAll(depositButton, chipShopButton, balanceLabel, winLabel, totalWinLabel, lastWinLabel, betLabel, placeYourBetsLabel, playerScoreLabel, dealerScoreLabel,
                dealButton, hitButton, standButton,exitButton, bettingChips.getFirst().getImage());

        blackJackPhaseState = BlackJackPhaseState.DEAL;

        fillCardSlots();
        addCards();
    }

    public void fillCardSlots() {
        double padding = 350;

        dealerSlots = new ArrayList<>();
        playerSlots = new ArrayList<>();

        for (int nCard = 0; nCard < BLACKJACK; nCard++) {
            PlayingCard card = new PlayingCard(padding, 150);
            padding += 20;
            dealerSlots.add(card);
        }
        padding = 300;
        for (int nCard = 0; nCard < BLACKJACK; nCard++) {
            PlayingCard card = new PlayingCard(padding, 250);
            padding += 20;
            playerSlots.add(card);
        }
    }

    public void addCards(){
        deck.getCards().forEach((card) -> {
            mainPane.getChildren().add(card.getImage());
        });
    }

    public void addScorePlayer(PlayingCard card) {
        if (card.getRank() != null) {
            int currentScore = playerScore + card.getRank().getValue();
            if (currentScore > BLACKJACK) {
                for (PlayingCard nPlayingCard : playerSlots) {
                    if (nPlayingCard.getRank() == PlayingCard.Rank.ACE) {
                        setPlayerScore(currentScore - 10);
                        nPlayingCard.setRank(PlayingCard.Rank.ONE);
                        return;
                    }
                }
            }
            setPlayerScore(currentScore);
        }
    }

    public void addScoreDealer(PlayingCard card) {
        if (card.getRank() != null) {
            int currentScore = dealerScore + card.getRank().getValue();
            if (currentScore > BLACKJACK) {
                for (PlayingCard nPlayingCard : dealerSlots) {
                    if (nPlayingCard.getRank() == PlayingCard.Rank.ACE) {
                        setDealerScore(currentScore - 10);
                        nPlayingCard.setRank(PlayingCard.Rank.ONE);
                        return;
                    }
                }
            }
            setDealerScore(currentScore);
        }
    }

    public void buttonsHitStandActivation(boolean active) {
        hitButton.setDisable(!active);
        hitButton.setVisible(active);
        standButton.setDisable(!active);
        standButton.setVisible(active);
    }

    public void reset() {
        setPlayerScore(0);
        setDealerScore(0);

        lastBet = bet;
        bet = 0;

        dealersFlippedPlayingCard.getImage().setImage(new Image(getClass().getResource("/images/BlackJackImages/Cards/Flipped.png").toExternalForm()));

        dealerFreeSlot = 0;
        playerFreeSlot = 0;

        dealButton.setVisible(false);
        standButton.setVisible(false);
        hitButton.setVisible(false);

        blackJackWinState = BlackJack.BlackJackWinState.IN_PROGRESS;

        deck.fill();
        addCards();
    }
}


