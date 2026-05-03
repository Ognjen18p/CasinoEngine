package com.basis.game.table_game.roulette;

import com.application.utilities.Vector2;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball {
    private final double friction;
    private final double gravity;
    private final double startRadius;
    private final double targetRadius;
    private final Roulette roulette;
    private final ImageView image;
    private final Vector2 size;
    private Vector2 velocity;
    private Vector2 position;
    private Vector2 center;
    private Runnable onStopped;

    public void setOnStopped(Runnable onStopped) {
        this.onStopped = onStopped;
    }

    public ImageView getImage() {
        return image;
    }

    public Vector2 getSize() {
        return size;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Ball(Roulette roulette, Vector2 size) {
        this.roulette = roulette;
        this.size = size;
        friction = 0.99;
        gravity = 9.81;
        startRadius = roulette.getWheel().getWheelSize().getX() / 2 - size.getX();
        targetRadius = roulette.getWheel().getWheelSize().getX() / 2 - size.getX() * 4;

        image = new ImageView(new Image(getClass().getResource("/images/RouletteImages/ball.png").toExternalForm()));
        image.setFitWidth(size.getX());
        image.setFitHeight(size.getY());

        roulette.getWheel().getBallTrack().getChildren().add(image);
    }

    public void startBallSpinning() {
        double randomStartAngle = Math.toRadians(Math.random() * 360);
        center = new Vector2(roulette.getWheel().getWheelSize().getX() / 2,
                                roulette.getWheel().getWheelSize().getY() / 2);
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
                double totalForce = centripetalForce + orbitalBalance + (radius * gravity * deltaTime);
                Vector2 forceVector = Vector2.multiply(toCenterNormalized, totalForce * deltaTime);

                velocity = Vector2.add(velocity, forceVector);
                velocity = Vector2.multiply(velocity, friction);
                position = Vector2.add(position, Vector2.multiply(velocity, deltaTime));

                image.setX(position.getX() - image.getFitWidth() / 2);
                image.setY(position.getY() - image.getFitHeight() / 2);

                roulette.getWheel().getTable().setRotate(roulette.getWheel().getTable().getRotate() + speed * deltaTime);

                if (radius <= targetRadius) {
                    stop();
                    onStopped.run();
                }
            }
        };
        ballTimer.start();
    }

}
