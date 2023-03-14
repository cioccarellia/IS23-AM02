package it.polimi.ingsw.model.bookshelf;

import it.polimi.ingsw.model.board.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Implements a 6 row, 5 column bookshelf of {@link it.polimi.ingsw.model.board.Tile}s.
 */
public class Bookshelf {

    private static final int rows = 6;
    private static final int columns = 5;

    private final Tile[][] bookshelfMatrix = new Tile[rows][columns];

    public Bookshelf() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
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
        int freeSpace = rows - (firstEmptyShelfIndex);

        if (freeSpace < selection.size()) {
            throw new IllegalStateException("Selected tiles do not fit in designated column");
        }


        // we insert the elements one by one
        for (Tile tile : selection) {
            bookshelfMatrix[firstEmptyShelfIndex++][columnIndex] = tile;
        }
    }


    public Tile[][] getShelfMatrix() {
        // fixme deep copy
        return bookshelfMatrix.clone();
    }


}
