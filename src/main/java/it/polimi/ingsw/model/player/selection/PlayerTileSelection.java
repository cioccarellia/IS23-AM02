package it.polimi.ingsw.model.player.selection;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.game.CellInfo;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the current player selection of tiles from the board
 */
public class PlayerTileSelection implements Serializable {
    /**
     * The selection is kept in a set, since order does not matter
     */
    private List<CellInfo> selectedTiles;

    public PlayerTileSelection() {
        selectedTiles = null;
    }

    public PlayerTileSelection(List<CellInfo> tiles) {
        this.selectedTiles = tiles;
    }

    public List<Tile> getSelectedTiles() {
        return selectedTiles.stream().map(CellInfo::tile).collect(Collectors.toList());
    }

    public void setSelectedTiles(List<CellInfo> selectedTiles) {
        this.selectedTiles = selectedTiles;
    }

    public List<CellInfo> getSelection() {
        return selectedTiles;
    }


    /**
     * Checks whether the given list of tiles contains the same elements (and the same number of elements)
     * as this selection.
     */
    public boolean selectionEquals(List<Tile> tiles) {
        return tiles.containsAll(getSelectedTiles()) && getSelectedTiles().containsAll(tiles);
    }
}
