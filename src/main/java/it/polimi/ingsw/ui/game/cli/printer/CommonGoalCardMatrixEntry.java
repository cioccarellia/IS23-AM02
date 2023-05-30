package it.polimi.ingsw.ui.game.cli.printer;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.game.goal.CommonGoalCardStatus;

record CommonGoalCardMatrixEntry(
        Tile[][] bookshelf,
        CommonGoalCardStatus status
) {
}
