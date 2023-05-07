package it.polimi.ingsw.net.tcp.messages.request;

import it.polimi.ingsw.model.board.Coordinate;

import java.util.Set;

public class GameSelectionTurnRequest extends Request {

    private final String username;
    private final Set<Coordinate> selection;

    public GameSelectionTurnRequest(String username, Set<Coordinate> selection) {
        this.username = username;
        this.selection = selection;
    }

    public String getUsername() {
        return username;
    }

    public Set<Coordinate> getSelection() {
        return selection;
    }
}
