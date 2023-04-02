package it.polimi.ingsw.commons;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import org.jetbrains.annotations.NotNull;


/**
 * Interface containing common properties and methods often used
 * in testing 6x5 {@link it.polimi.ingsw.model.bookshelf.Bookshelf} matrices.
 */
public interface ShelfMatrixTester {

    int rows = BookshelfConfiguration.getInstance().rows();
    int cols = BookshelfConfiguration.getInstance().cols();

    /**
     * Matrix filled with null values
     */
    Tile[][] nullMatrix = new Tile[rows][cols];

    /**
     * Returns a matrix filled with the passed {@link Tile} value
     */
    default Tile[][] generateFullMatrixOf(@NotNull Tile content) {
        Tile[][] fullMatrix = new Tile[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                fullMatrix[i][j] = content;
            }
        }

        return fullMatrix;
    }
}
