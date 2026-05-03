package com.controller;

import javafx.scene.Scene;

public abstract class Controller {
    protected Scene scene;
    protected abstract void setupEventHandlers();
    protected abstract void initializeScene();
    public abstract void showScene();
}

