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
}
