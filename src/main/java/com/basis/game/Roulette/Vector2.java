package com.basis.game.Roulette;

public class Vector2 {
    private double x, y;

    public Vector2() {
        x = 0.0;
        y = 0.0;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    /// ADD ///

    public static Vector2 add(Vector2 vector, Vector2 vector2) {
        return new Vector2(vector.getX() + vector2.getX(), vector.getY() + vector2.getY());
    }

    public void add(Vector2 vector) {
        add(this, vector);
    }


    /// SUB ///

    public static Vector2 subtract(Vector2 vector1, Vector2 vector2) {
        return new Vector2(vector1.getX() - vector2.getX(), vector1.getY() - vector2.getY());
    }

    public void subtract(Vector2 vector) {
        subtract(this, vector);
    }


    /// MULTIPLY ///
    public static Vector2 multiply(Vector2 vector, double scalar) {
        return new Vector2(vector.getX() * scalar, vector.getY() * scalar);
    }

    public void multiply(double scalar) {
        multiply(this, scalar);
    }

    public static Vector2 multiply(Vector2 vector1, Vector2 vector2) {
        return new Vector2(vector1.getX() * vector2.getX(), vector1.getY() * vector2.getY());
    }

    public void multiply(Vector2 vector) {
        multiply(vector, this);
    }


    /// DIVIDE ///

    public static Vector2 divide(Vector2 vector, double scalar) {
        return new Vector2(vector.getX() / scalar, vector.getY() / scalar);
    }

    public static Vector2 divide(double scalar, Vector2 vector) {
        return new Vector2(scalar / vector.getX(), scalar / vector.getY());
    }

    public void divide(double scalar, boolean firstScalar) {
        if (firstScalar)
            divide(this, scalar);
        else
            divide(scalar, this);
    }

    public static Vector2 divide(Vector2 vector1, Vector2 vector2) {
        return new Vector2(vector1.getX() / vector2.getX(), vector1.getY() / vector2.getY());
    }

    public void divide(Vector2 vector) {
        divide(this, vector);
    }


    /// MAGNITUDE ///

    public static double magnitude(Vector2 vector) {
        return Math.sqrt(Math.pow(vector.getX(), 2) + Math.pow(vector.getY(), 2));
    }

    public double magnitude() {
        return magnitude(this);
    }


    /// NORMALIZE ///

    public static Vector2 normalize(Vector2 vector) {
        return new Vector2(vector.getX() / magnitude(vector), vector.getY() / magnitude(vector));
    }

    public Vector2 normalize() {
        return normalize(this);
    }
}
