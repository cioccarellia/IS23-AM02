package it.polimi.ingsw.model.bookshelf;

import it.polimi.ingsw.model.board.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static it.polimi.ingsw.costants.BookShelfCostants.*;

/**
 * Implements a 6 row, 5 column bookshelf of {@link it.polimi.ingsw.model.board.Tile}s.
 */
public class Bookshelf {

    private final Tile[][] bookshelfMatrix = new Tile[ROWS][COLUMNS];

    public Bookshelf() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                bookshelfMatrix[i][j] = null;
            }
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
        if (columnIndex < 0 || columnIndex >= 5) {
            throw new IllegalArgumentException("Received column index our of bounds. Expected [0,4], received %d".formatted(columnIndex));
        }

        if (selection.size() > 3) {
            throw new IllegalArgumentException("Received a selection of tiles with more than three elements");
        }


        // we find the first void on the designated column.
        // this is also the number of elements in a certain column.
        int firstEmptyShelfIndex = 0;

        while (bookshelfMatrix[firstEmptyShelfIndex][columnIndex] != null) {
            firstEmptyShelfIndex++;
        }


        // we check whether we have enough space on our column to insert
        // the selected items
        int freeSpace = ROWS - (firstEmptyShelfIndex);

        if (freeSpace < selection.size()) {
            throw new IllegalStateException("Selected tiles do not fit in designated column");
        }


        // we insert the elements one by one
        for (Tile tile : selection) {
            bookshelfMatrix[firstEmptyShelfIndex++][columnIndex] = tile;
        }
    }

    /**
     * Returns the shelf game matrix
     */
    public Tile[][] getShelfMatrix() {
        Tile[][] copy = new Tile[ROWS][COLUMNS];

        for (int i = 0; i < ROWS; i++) {
            System.arraycopy(bookshelfMatrix[i], 0, copy[i], 0, ROWS);
        }

        return copy;
    }

    /**
     * @param column the one the player chose to put his tiles in
     * @param amount how many tiles the player wants to insert
     * @return 1 if they fit, 0 if they don't
     */
    public boolean canFit(int column, int amount) {
        int i = ROWS - 1;
        while (bookshelfMatrix[i][column] != null) {
            i--;
        }

        return i + 1 >= amount;
    }

    /**
     *
     * @return 1 if the bookshelf is full, 0 if not
     */
    public boolean isFull() {
        int countNotEmptyRow = 0;
        for (int i = 0; i < ROWS; i++) {
            if (bookshelfMatrix[i][COLUMNS] != null) {
                countNotEmptyRow++;
            }
        }
        if (countNotEmptyRow == ROWS) return true;
        else return false;
    }

}
