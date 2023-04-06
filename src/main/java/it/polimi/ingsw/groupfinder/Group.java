package it.polimi.ingsw.groupfinder;

import it.polimi.ingsw.model.board.Tile;
import org.jetbrains.annotations.NotNull;

/**
 * A group is a contiguous sector of {@link Tile}s.
 * A tile is defined to be contiguous to another tile if they share at least one edge.
 *
 * @param type The specific tile composing the group
 * @param size The amount of tiles contained in the group
 */
public record Group(@NotNull Tile type, int size) {
}