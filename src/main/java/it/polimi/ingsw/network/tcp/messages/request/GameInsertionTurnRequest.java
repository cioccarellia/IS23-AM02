package it.polimi.ingsw.network.tcp.messages.request;

import it.polimi.ingsw.model.board.Tile;

import java.util.List;

public class GameInsertionTurnRequest extends Request {

    private final String username;
    private final List<Tile> tiles;
    private final int column;

    public GameInsertionTurnRequest(String username, List<Tile> tiles, int column) {
        this.username = username;
        this.tiles = tiles;
        this.column = column;
    }

    public String getUsername() {
        return username;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public int getColumn() {
        return column;
    }
}
