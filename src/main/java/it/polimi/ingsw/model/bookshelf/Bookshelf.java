package it.polimi.ingsw.model.bookshelf;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.List;

/**
 * Implements a 6 row, 5 column bookshelf of {@link it.polimi.ingsw.model.board.Tile}s.
 */
public class Bookshelf {

    private static final int rows = BookshelfConfiguration.getInstance().rows();
    private static final int cols = BookshelfConfiguration.getInstance().cols();
    private static final int maxSelectionSize = LogicConfiguration.getInstance().maxSelectionSize();

    private final Tile[][] bookshelfMatrix = new Tile[rows][cols];

    public Bookshelf() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                bookshelfMatrix[i][j] = null;
            }
        }
    }

    @TestOnly
    @VisibleForTesting
    public void fillUpBookShelf(Tile[][] matrix) {
        for (int i = 0; i < rows; i++) {
            if (cols >= 0) System.arraycopy(matrix[i], 0, bookshelfMatrix[i], 0, cols);
        }
    }

    /**
     * Inserts vertically a list of (at most) three tiles in the designated column, given that
     * it has enough space.
     * The order of insertion is the natural order of the list, i.e. the first element of the list
     * is the first one to be inserted.
     *
     * @param columnIndex specifies the index of the column
     *                    to perform insertion on (0 through 4).
     * @param selection   the list of (up to) three tiles to be inserted
     * @throws IllegalArgumentException if a wrong index or selection of tiles is received
     * @throws IllegalStateException    if the selected column has not the space to house the selected tiles
     */
    public void insert(int columnIndex, @NotNull List<Tile> selection) {
        if (columnIndex < 0 || columnIndex >= cols) {
            throw new IllegalArgumentException("Received column index out of bounds. Expected [0,4], received %d".formatted(columnIndex));
        }

        if (selection.size() > maxSelectionSize) {
            throw new IllegalArgumentException("Received a selection of tiles with more than three elements");
        }

        if (!canFit(columnIndex, selection.size())) {
            throw new IllegalStateException("Selected tiles do not fit in designated column");
        }

        // we find the first void on the designated column.
        // this is also the number of elements in a certain column.
        int lastEmptyShelfIndex = 0;

        while (bookshelfMatrix[lastEmptyShelfIndex][columnIndex] == null && lastEmptyShelfIndex + 1 < rows) {
            lastEmptyShelfIndex++;
        }
        
        if(lastEmptyShelfIndex != rows - 1)
            lastEmptyShelfIndex--;

        for (Tile tile : selection) {
            bookshelfMatrix[lastEmptyShelfIndex][columnIndex] = tile;
            lastEmptyShelfIndex--;

        }
    }

    /**
     * Returns the shelf game matrix
     */
    public Tile[][] getShelfMatrix() {
        Tile[][] copy = new Tile[rows][cols];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(bookshelfMatrix[i], 0, copy[i], 0, cols);
        }

        return copy;
    }

    /**
     * @param column the one the player chose to put his tiles in
     * @param amount how many tiles the player wants to insert
     * @return 1 if they fit, 0 if they don't
     */
    public boolean canFit(int column, int amount) {
        int i = rows - 1;
        while (bookshelfMatrix[i][column] != null && i > 0) {
            i--;
        }

        return i + 1 >= amount;
    }

    /**
     * @return 1 if the bookshelf is full, 0 if not
     */
    public boolean isFull() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (bookshelfMatrix[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

}
