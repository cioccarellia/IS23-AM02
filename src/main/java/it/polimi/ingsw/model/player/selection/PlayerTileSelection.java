package it.polimi.ingsw.model.player.selection;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import javafx.util.Pair;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the current player selection of tiles from the board
 */
public class PlayerTileSelection {
    /**
     * The selection is kept in a set, since order does not matter
     */
    private List<Pair<Coordinate, Tile>> selectedTiles;

    public PlayerTileSelection() {
        selectedTiles = null;
    }

    public PlayerTileSelection(List<Pair<Coordinate, Tile>> tiles) {
        this.selectedTiles = tiles;
    }

    public List<Tile> getSelectedTiles() {
        return selectedTiles.stream().map(Pair::getValue).collect(Collectors.toList());
    }

    public void setSelectedTiles(List<Pair<Coordinate, Tile>> selectedTiles) {
        this.selectedTiles = selectedTiles;
    }

    /**
     * Checks whether the given list of tiles contains the same elements (and the same number of elements)
     * as this selection.
     */
    public boolean selectionEquals(List<Tile> tiles) {
        return tiles.containsAll(getSelectedTiles()) && getSelectedTiles().containsAll(tiles);
    }
}
