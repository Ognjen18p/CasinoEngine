package com.basis.game.machine_game;

import com.basis.game.essentials.Vector2;
import com.basis.game.machine_game.symbols.SymbolInfo;

public class Cell {
    private final Vector2 position;
    private SymbolInfo lockedSymbol;

    public Vector2 getPosition() {
        return position;
    }

    public SymbolInfo getLockedSymbol() {
        return lockedSymbol;
    }

    public void lockNewSymbol(SymbolInfo symbol) {
        lockedSymbol = symbol;
    }

    public Cell(Vector2 position) {
        this.position = position;
        lockedSymbol = null;
    }
}