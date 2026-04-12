package com.basis.page;

import com.basis.game.essentials.GameSettings;
import com.basis.game.essentials.Vector2;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public abstract class Page {
    protected Label errorMessageLabel;
    protected Label footerLabel;
    protected Label titleLabel;
    protected VBox mainLayout;
    protected VBox buttonBox;

    protected abstract void initializeElements();

    public Page() {
        initializeElements();
    }

    public Label getErrorMessageLabel() {
        return errorMessageLabel;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public VBox getMainLayout() {
        return mainLayout;
    }

    public VBox getButtonBox() {
        return buttonBox;
    }

    public void showErrorMessage(String errorMessage) {
        this.errorMessageLabel.setText(errorMessage);
    }

}
