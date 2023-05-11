package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.chat.ChatTextMessage;

import java.util.List;
import java.util.Set;

/**
 *
 */
public interface ViewEventHandler {

    void onSelection(Set<Coordinate> coordinates);

    void onInsertion(int column, List<Tile> tiles);

    void sendMessage(ChatTextMessage message);

    void quitGame();

    void keepAlive();

}