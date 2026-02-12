package com.controller.pages;

import javafx.scene.Scene;
import main.GameManager;

public abstract class PageController {
    protected Scene scene;
    protected GameManager gameManager;
    protected abstract void setupEventHandlers();
    protected abstract void showPage();
    protected abstract void initializePage();
}

