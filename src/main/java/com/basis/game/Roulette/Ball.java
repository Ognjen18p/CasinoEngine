package com.basis.game.Roulette;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Map;

public class Ball {
    private final Roulette roulette;
    private final ImageView image;
    private Vector2 velocity;
    private Vector2 position;
    private Vector2 center;
    private final double friction;
    private final double gravity;
    private final double startRadius;
    private final double targetRadius;

    public Ball(Roulette roulette) {
        this.roulette = roulette;
        friction = 0.99;
        gravity = 9.8;
        startRadius = 170;
        targetRadius = 120;

        image = new ImageView(new Image(getClass().getResource("/images/RouletteImages/ball.png").toExternalForm()));
        image.setFitWidth(12);
        image.setFitHeight(12);

        roulette.getBallTrack().getChildren().add(image);
    }

    public void startBallMovement() {

        double randomStartAngle = Math.toRadians(Math.random() * 360);
        center = new Vector2(roulette.getTable().getLayoutX() + roulette.getTable().getWidth() / 2, roulette.getTable().getLayoutY() + roulette.getTable().getHeight() / 2);
        position = new Vector2(center.getX() + Math.cos(randomStartAngle) * startRadius, center.getY() + Math.sin(randomStartAngle) * startRadius);
        velocity = new Vector2(-Math.sin(randomStartAngle) * 1000, Math.cos(randomStartAngle) * 1000);
        AnimationTimer ballTimer = new AnimationTimer() {
            long previousTime = 0;

            @Override
            public void handle(long currentTime) {
                if (previousTime == 0) {
                    previousTime = currentTime;
                    return;
                }

                double deltaTime = (currentTime - previousTime) / 1_000_000_000.0;
                previousTime = currentTime;

                Vector2 toCenter = Vector2.subtract(center, position);
                double radius = toCenter.magnitude();
                Vector2 toCenterNormalized = Vector2.normalize(toCenter);

                double speed = velocity.magnitude();
                double centripetalForce = (speed * speed) / radius;
                double orbitalBalance = Math.max(0, radius - targetRadius);
                double totalForce = centripetalForce + orbitalBalance + radius * gravity * deltaTime;
                Vector2 forceVector = Vector2.multiply(toCenterNormalized, totalForce * deltaTime);

                velocity = Vector2.add(velocity, forceVector);
                velocity = Vector2.multiply(velocity, friction);
                position = Vector2.add(position, Vector2.multiply(velocity, deltaTime));

                image.setX(position.getX() - image.getFitWidth() / 2);
                image.setY(position.getY() - image.getFitHeight() / 2);

                roulette.getTable().setRotate(roulette.getTable().getRotate() + speed * deltaTime);

                if (radius <= targetRadius) {
                    stop();
                    snapBallToPocket();
                }
            }
        };
        ballTimer.start();
    }

    public void snapBallToPocket() {

        int closestPocket = 0;
        double closest = 99999;
        for (Map.Entry<Integer, ImageView> entry : roulette.getPockets().entrySet()) {
            Bounds bounds = entry.getValue().localToScene(entry.getValue().getBoundsInLocal());
            double pocketX = bounds.getCenterX();
            double pocketY = bounds.getCenterY();

            Bounds ballBounds = image.localToScene(image.getBoundsInLocal());
            double ballX = ballBounds.getCenterX();
            double ballY = ballBounds.getCenterY();
            double x = ballX - pocketX;
            double y = ballY - pocketY;
            double magnitude = Math.sqrt(x * x + y * y);

            if (magnitude < closest) {
                closestPocket = entry.getKey();
                closest = magnitude;
                System.out.println(ballX + " ball pos " + ballY);
                System.out.println(pocketX + " pocekt pos " + pocketY);

            }
        }

        image.setX(roulette.getPockets().get(closestPocket).getX());
        image.setY(roulette.getPockets().get(closestPocket).getY());

        System.out.println("Rezultat: " + closestPocket);
    }
}
