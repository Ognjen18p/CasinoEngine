package com.basis.pages;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public abstract class Page {
    protected Label errorMessageLabel;
    protected Label footerLabel;
    protected Label titleLabel;
    protected VBox mainLayout;
    protected VBox buttonBox;

    public Label getErrorMessageLabel() {
        return errorMessageLabel;
    }

    public Label getFooterLabel() {
        return footerLabel;
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
