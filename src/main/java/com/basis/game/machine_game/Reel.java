package com.basis.game.machine_game;

import com.application.utilities.Vector2;
import com.basis.game.machine_game.symbols.Symbol;
import com.basis.game.machine_game.symbols.SymbolType;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Reel {
    private final int numberOfCells;
    private final int width;
    private final int offset;
    private final Vector2 position;
    private Pane pane;
    private Pane winningPane;
    private boolean stopped;

    private ArrayList<Pane> symbolPanes;
    private ArrayList<Symbol> winningSymbols;

    public ArrayList<Pane> getSymbols() {
        return symbolPanes;
    }

    public ArrayList<Symbol> getWinningSymbols() {
        return winningSymbols;
    }

    public Pane getPane() {
        return pane;
    }

    public Reel(int numberOfCells, int width, int offset, Vector2 position) {
        this.numberOfCells = numberOfCells;
        this.position = position;
        this.width = width;
        this.offset = offset;
        initialize();
    }

    private void initialize() {
        pane = new Pane();
        symbolPanes = new ArrayList<>();
        winningSymbols = new ArrayList<>();
        reelsCreation();
    }

    private SymbolType getRandomSymbolType() {
        Random rand = new Random();
        int random = rand.nextInt(101);
        int cumulation = 0;
        for (SymbolType type : SymbolType.values()) {
            cumulation += type.getProbability();
            if(random <= cumulation) return type;
        }
        return SymbolType.SHURIKEN;
    }

    private void reelsCreation() {
        for (int nPane = 0; nPane < 3; nPane++) {
            Pane symbolPane = new Pane();
            for (int nSymbol = 0; nSymbol < numberOfCells; nSymbol++) {
                Vector2 nPosition = new Vector2(0, nSymbol * width + offset);
                Symbol symbol = new Symbol(getRandomSymbolType(), nPosition, new Vector2(width));
                symbolPane.getChildren().add(symbol.getSymbolImage());
                if (nPane == 2) {
                    winningSymbols.add(symbol);
                    winningPane = symbolPane;
                }
            }
            symbolPane.setLayoutX(position.getX());
            symbolPane.setLayoutY(position.getY() - nPane * (numberOfCells * width) + offset);
            symbolPanes.add(symbolPane);
            pane.getChildren().add(symbolPane);
        }
    }

    public void replacement() {
        winningSymbols.clear();
        symbolPanes.clear();
        pane.getChildren().clear();
        reelsCreation();
    }

    public boolean reelSpin(double deltaTime, double velocity, boolean stopSpin) {
        for (int nPane = 0; nPane < symbolPanes.size(); nPane++) {
            Pane symbolPane = symbolPanes.get(nPane);
            double positionY = symbolPane.getLayoutY();
            double bottomBound = position.getY() + numberOfCells * width;
            double topBound = position.getY();

            if (positionY >= topBound && positionY <= topBound + (double) width / 3 && stopSpin && winningPane == symbolPane) {
                symbolPane.setLayoutY(topBound);
                for(Pane otherPane : symbolPanes) {
                    if(!otherPane.equals(symbolPane))
                        otherPane.setVisible(false);
                }
                return true;
            }
            if (positionY >= bottomBound) {
                symbolPanes.sort(Comparator.comparingDouble(Node::getLayoutY));
                double newY = symbolPanes.getFirst().getLayoutY() - (numberOfCells * width);
                symbolPane.setLayoutY(newY);
                positionY = newY;
            }
            double newPosition = positionY + velocity * deltaTime;
            symbolPane.setLayoutY(newPosition);
            symbolPane.setVisible(!(positionY < -(double)(numberOfCells * width) / 2) && !(positionY >= bottomBound));
        }
        return false;
    }
}