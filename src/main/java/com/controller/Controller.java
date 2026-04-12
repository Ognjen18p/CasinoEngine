package com.controller;

import javafx.scene.Scene;
import com.manager.GameManager;

public abstract class Controller {
    protected Scene scene;
    protected abstract void setupEventHandlers();
    protected abstract void initializeScene();
    public abstract void showScene();
}

