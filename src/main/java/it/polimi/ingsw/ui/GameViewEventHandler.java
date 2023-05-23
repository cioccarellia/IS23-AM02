package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.chat.ChatTextMessage;

import java.util.List;
import java.util.Set;

/**
 * Describes the hooks that can be implemented on any client-side component (in this case, the {@link it.polimi.ingsw.controller.client.ClientController})
 * that wants to be aware of view events (generated in {@link UiGateway}).
 * The methods defined therein are to be called only following a user (manual or automatic) action.
 */
public interface GameViewEventHandler {

    /**
     * User has completed selection and is sending coordinates to server
     */
    void onViewSelection(Set<Coordinate> coordinates);

    /**
     * User has completed insertion and is sending ordered tiles and column
     */
    void onViewInsertion(int column, List<Tile> tiles);

    /**
     * User has sent a chat message
     */
    void onViewSendMessage(ChatTextMessage message);

    /**
     * User has quit the game
     */
    void onViewQuitGame();
}