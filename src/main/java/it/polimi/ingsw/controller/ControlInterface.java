package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.Tile;

import java.util.List;
import java.util.Set;

public interface ControlInterface {

    void onGameStarted();

    void onPlayerQuit();

    void onPLayerTileSelection(String username, Set<Tile> tiles);

    void onPlayerBookshelfTileInsertion(String username, int column, List<Tile> tiles);

    void onPlayerTokenUpdate(String username);

    void onTurnChange();

    void onLastTurn();

    void onGameEnded();
}
