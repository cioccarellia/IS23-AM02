package it.polimi.ingsw.commons;

import it.polimi.ingsw.model.board.Tile;
import org.jetbrains.annotations.NotNull;

import static it.polimi.ingsw.costants.BookShelfConstants.COLUMNS;
import static it.polimi.ingsw.costants.BookShelfConstants.ROWS;

/**
 * Interface containing common properties and methods often used
 * in testing 6x5 {@link it.polimi.ingsw.model.bookshelf.Bookshelf} matrices.
 */
public interface ShelfMatrixTester {
    /**
     * Matrix filled with null values
     */
    Tile[][] nullMatrix = new Tile[ROWS][COLUMNS];

    /**
     * Returns a matrix filled with the passed {@link Tile} value
     */
    default Tile[][] fullOf(@NotNull Tile content) {
        Tile[][] fullMatrix = new Tile[ROWS][COLUMNS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                fullMatrix[i][j] = content;
            }
        }

        return fullMatrix;
    }
}
