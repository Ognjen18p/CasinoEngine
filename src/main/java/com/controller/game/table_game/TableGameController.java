package com.controller.game.table_game;

import com.basis.game.table_game.TableGame;
import com.basis.game.table_game.blackjack.Chip;
import com.controller.Controller;

public abstract class TableGameController extends Controller {

    protected abstract void selectingChip(Chip chip);

    protected abstract void purchasedChip(TableGame game);

}
