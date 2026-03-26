package com.basis.game.machine_game.symbols;

public interface SymbolInfo {
    String getId();
    String getImagePath();
    int getProbability();
    int[] getPayouts();
}
