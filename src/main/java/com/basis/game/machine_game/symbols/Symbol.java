package com.basis.game.machine_game.symbols;

import com.application.utilities.Vector2;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

public class Symbol {

    private static final HashMap<String, Image> IMAGE_CACHE = new HashMap<>();

    private SymbolInfo symbolInfo;
    private ImageView symbolImage;
    private Vector2 position;
    private Vector2 size;

    private boolean stop;

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public void setSymbolInfo(SymbolInfo symbolInfo) {
        this.symbolInfo = symbolInfo;
        imageSet();
    }

    public SymbolInfo getSymbolInfo() {
        return symbolInfo;
    }

    public ImageView getSymbolImage() {
        return symbolImage;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        symbolImage.setLayoutX(position.getX());
        symbolImage.setLayoutY(position.getY());
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
        symbolImage.setFitWidth(size.getX());
        symbolImage.setFitHeight(size.getY());
    }

    public Symbol(SymbolInfo symbolInfo, Vector2 position, Vector2 size) {
        this.symbolInfo = symbolInfo;
        this.position = position;
        this.size = size;

        symbolImage = new ImageView();
        symbolImage.setFitWidth(size.getX());
        symbolImage.setFitHeight(size.getY());
        imageSet();
    }


    private void imageSet() {
        String path = symbolInfo.getImagePath();
        IMAGE_CACHE.computeIfAbsent(path,otherPath -> new Image(getClass().getResource(otherPath).toExternalForm()));
        symbolImage.setImage(IMAGE_CACHE.get(path));
        symbolImage.setLayoutX(position.getX());
        symbolImage.setLayoutY(position.getY());
    }

}
