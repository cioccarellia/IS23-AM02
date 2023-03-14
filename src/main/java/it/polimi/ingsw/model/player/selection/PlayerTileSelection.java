package it.polimi.ingsw.model.player.selection;

import it.polimi.ingsw.model.board.Tile;

import java.util.Set;

public class PlayerTileSelection {
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
