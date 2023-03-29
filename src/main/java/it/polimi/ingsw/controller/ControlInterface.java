package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.Coordinates;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.game.Token;

import java.util.List;
import java.util.Set;

public interface ControlInterface {

    void onGameStarted();

    void onPlayerQuit();

    void onPLayerTileSelection(String username, Set<Coordinates> coordinates);

    void onPlayerBookshelfTileInsertion(String username, int column, List<Tile> tiles);

    void onPlayerTokenUpdate(String username);

    void onPlayerTokenUpdate(String username, Token token);

    void onTurnChange();

    void onLastTurn();

    void onGameEnded();
}
