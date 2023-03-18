package it.polimi.ingsw.model.player.selection;

import it.polimi.ingsw.model.board.Tile;

import java.util.Set;

/**
 * Represents the current player selection of tiles from the board
 * */
public class PlayerTileSelection {
    /**
     * The selection is kept in a set, since order does not matter
     * */
    private Set<Tile> selectedTiles;

    public PlayerTileSelection() {
        selectedTiles = null;
    }

    public Set<Tile> getSelectedTiles() {
        return selectedTiles;
    }

    public void setSelectedTiles(Set<Tile> selectedTiles) {
        this.selectedTiles = selectedTiles;
    }
}
