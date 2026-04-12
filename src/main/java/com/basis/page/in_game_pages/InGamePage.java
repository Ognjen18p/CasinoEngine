package com.basis.page.in_game_pages;

import com.basis.game.essentials.Vector2;
import com.basis.page.Page;
import com.manager.GameManager;
import javafx.scene.control.Button;

public class InGamePage extends Page {
    protected Button exitButton;
    protected Button backButton;

    public InGamePage() {
        super();
    }

    @Override
    protected void initializeElements() {
        exitButton = new Button("Exit");
        backButton = new Button("Back");



    }
}
