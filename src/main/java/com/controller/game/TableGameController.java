package com.controller.game;

import com.basis.game.Game;
import com.basis.game.table_game.TableGame;
import com.basis.game.table_game.blackjack.Chip;
import com.controller.Controller;
import javafx.collections.ListChangeListener;

public abstract class TableGameController extends Controller {

    protected abstract void selectingChip(Chip chip);

    protected abstract void purchasedChip(TableGame game);

}
