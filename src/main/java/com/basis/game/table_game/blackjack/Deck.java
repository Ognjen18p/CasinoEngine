package com.basis.game.table_game.blackjack;

import com.application.utilities.Vector2;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Deck {
    private final int NUMBER_OF_RANKS = 13;
    private final int NUMBER_OF_SUITS = 4;
    private final int NUMBER_OF_DECKS = 6;
    private final Vector2 position;
    private int firstInDeck = 0;

    private final ArrayList<PlayingCard> cards = new ArrayList<>();

    public Deck(Vector2 position) {
        this.position = position;
    }

    public int getNUMBER_OF_RANKS() {
        return NUMBER_OF_RANKS;
    }

    public int getNUMBER_OF_SUITS() {
        return NUMBER_OF_SUITS;
    }

    public int getNUMBER_OF_DECKS() {
        return NUMBER_OF_DECKS;
    }

    public ArrayList<PlayingCard> fill() {
        cards.clear();
        firstInDeck = 0;
        for (int nDeck = 0; nDeck < NUMBER_OF_DECKS; nDeck++) {
            for (int nRank = 1; nRank < NUMBER_OF_RANKS; nRank++) {
                for (int nSuit = 0; nSuit < NUMBER_OF_SUITS; nSuit++) {
                    PlayingCard newCard = new PlayingCard(PlayingCard.Rank.values()[nRank], PlayingCard.Suit.values()[nSuit], position.getX(), position.getY());
                    newCard.getImage().setRotate(20);
                    cards.add(newCard);
                }
            }
        }
        shuffle();
        return cards;
    }

    public void shuffle() {
        for (int nCard = cards.size() - 1; nCard > 0; nCard--) {
            PlayingCard swapCard = cards.get(nCard);
            int randomPos = ThreadLocalRandom.current().nextInt(0, cards.size());
            cards.set(nCard, cards.get(randomPos));
            cards.set(randomPos, swapCard);
        }
    }

    public PlayingCard drawCard() {
        return cards.get(firstInDeck++);
    }

    public ArrayList<PlayingCard> getCards() {
        return cards;
    }
}