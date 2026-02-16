package com.basis.game.BlackJack;

import com.basis.game.Game;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {
    public enum Rank {
        ACE(11),
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
        KING(10);

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

    public Card(Rank rank, Suit suit, double xPos, double yPos) {
        this.rank = rank;
        this.suit = suit;
        image = new ImageView(new Image(getClass().getResource("/images/BlackJackImages/Cards/" + suit.toString() + "/" + rank.toString() + "of" + suit.toString() + ".png").toExternalForm()));
        image.setX(xPos);
        image.setY(yPos);
        image.setFitHeight(80);
        image.setFitWidth(60);
    }

    public Card(ImageView image) {
        rank = null;
        suit = null;
        this.image = image;
        image.setFitHeight(80);
        image.setFitWidth(60);
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
