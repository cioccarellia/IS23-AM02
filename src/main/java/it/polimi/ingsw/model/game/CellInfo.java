package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public record CellInfo(
        @NotNull Coordinate coordinate,
        @Nullable Tile tile
) implements Serializable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellInfo cellInfo = (CellInfo) o;

        if (!coordinate.equals(cellInfo.coordinate)) return false;
        return tile == cellInfo.tile;
    }

    @Override
    public int hashCode() {
        int result = coordinate.hashCode();
        result = 31 * result + (tile != null ? tile.hashCode() : 0);
        return result;
    }
}
