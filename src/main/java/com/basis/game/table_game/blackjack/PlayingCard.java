package com.basis.game.table_game.blackjack;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayingCard {
    public enum Rank {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(10),
        QUEEN(10),
        KING(10),
        ACE(11);

        private final int value;

        Rank(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES;
    }

    private Rank rank;
    private Suit suit;
    private ImageView image;

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public PlayingCard(Rank rank, Suit suit, double xPos, double yPos) {
        this.rank = rank;
        this.suit = suit;
        image = new ImageView(new Image(getClass().getResource("/images/TableGames/BlackJackImages/Cards/" + suit.toString() + "/" + rank.toString() + "of" + suit.toString() + ".png").toExternalForm()));
        image.setLayoutX(xPos);
        image.setLayoutY(yPos);
        image.setFitHeight(80);
        image.setFitWidth(60);
    }

    public PlayingCard(double xPos, double yPos) {
        rank = null;
        suit = null;
        image = new ImageView();
        image.setLayoutX(xPos);
        image.setLayoutY(yPos);
    }

    public PlayingCard(ImageView image, Rank rank, Suit suit, double xPos, double yPos) {
        this.image = image;
        this.image.setLayoutX(xPos);
        this.image.setLayoutY(yPos);
        this.image.setFitHeight(80);
        this.image.setFitWidth(60);
        this.rank = rank;
        this.suit = suit;
    }

    public boolean moveToPosition(double xPos, double yPos, double moveSpeed, double deltaTime) {
        double directionX = xPos - image.getLayoutX();
        double directionY = yPos - image.getLayoutY();

        double magnitude = Math.sqrt(directionX * directionX + directionY * directionY);

        if (magnitude > 1) {
            double directionXNormalized = directionX / magnitude;
            double directionYNormalized = directionY / magnitude;

            image.setX(image.getX() + directionXNormalized * moveSpeed * deltaTime);
            image.setY(image.getY() + directionYNormalized * moveSpeed * deltaTime);
            return false;
        }
        return true;
    }
}
