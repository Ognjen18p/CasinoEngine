package com.basis.page;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public abstract class Page {
    protected Label errorMessageLabel;

    protected HBox headerBox;
    protected VBox contentBox;
    protected HBox footerBox;

    protected Label footerLabel;
    protected Label titleLabel;
    protected BorderPane mainLayout;

    protected Page(){
        initializeElements();
    }

    protected abstract void initializeElements();

    public Label getErrorMessageLabel() {
        return errorMessageLabel;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public BorderPane getMainLayout() {
        return mainLayout;
    }

    public VBox getContentBox() {
        return contentBox;
    }

    public void showErrorMessage(String errorMessage) {
        this.errorMessageLabel.setText(errorMessage);
    }

}
