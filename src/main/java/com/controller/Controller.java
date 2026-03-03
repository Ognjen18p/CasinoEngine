package com.controller;

import javafx.scene.Scene;
import main.GameManager;

public abstract class Controller {
    protected Scene scene;
    protected GameManager gameManager;
    protected abstract void setupEventHandlers();
    protected abstract void initializeScene();
    public abstract void showScene();
}

