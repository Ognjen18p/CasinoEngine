package com.basis.game.machine_game.symbols;

public enum SymbolType implements SymbolInfo {
    SEVEN("SEVEN", "/images/SlotImages/7.png", 100),
    CASH("CASH", "/images/SlotImages/cash.png", 50),
    COIN("COIN", "/images/SlotImages/coin.png", 20),
    DIAMOND("DIAMOND", "/images/SlotImages/diamond.png", 75);

    private final String id;
    private final String imagePath;
    private final int value;

    SymbolType(final String id, final String imagePath, final int value) {
        this.id = id;
        this.imagePath = imagePath;
        this.value = value;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public int getValue() {
        return value;
    }
}
