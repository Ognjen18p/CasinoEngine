package com.basis.pages;

import javafx.scene.layout.Pane;

public abstract class Page {
    protected Pane root;

    protected abstract Pane createRoot();
}
