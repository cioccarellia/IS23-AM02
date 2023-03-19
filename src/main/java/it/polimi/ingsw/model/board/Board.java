package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.cell.Cell;
import it.polimi.ingsw.model.board.cell.CellPattern;
import it.polimi.ingsw.utils.BoardUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static it.polimi.ingsw.costants.BoardConstants.BOARD_DIMENSION;
import static it.polimi.ingsw.costants.BoardConstants.DFU_BOARD_MATRIX;


/**
 * Implements the game board and its
 */
public class Board {
    private final Cell[][] matrix = new Cell[BOARD_DIMENSION][BOARD_DIMENSION];

    /**
     * Builds an empty board with different cells (dead, empty, with tile)
     */
    public Board() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                CellPattern defaultPattern = DFU_BOARD_MATRIX[i][j];

                if (defaultPattern == null) {
                    matrix[i][j] = new Cell(CellPattern.NORMAL, false);
                } else {
                    matrix[i][j] = new Cell(defaultPattern, true);
                }
            }
        }
    }

    /**
     * Returns the cell content at the given coordinates
     *
     * @param c are the coordinates at which we have to get the tile (cell's content)
     * @return the tile at the given coordinates
     */
    public Optional<Tile> getTileAt(Coordinates c) {
        Cell required = matrix[c.x()][c.y()];
        return required.getContent();
    }

    /**
     * Removes the tile from the cell at the given coordinates
     *
     * @param c are the coordinates at which we have to remove the tile from the cell
     */
    public void removeTileAt(Coordinates c) {
        matrix[c.x()][c.y()].clear();
    }

    /**
     * Returns the board current setup
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public Tile[][] getTileMatrix() {
        Tile[][] tileMatrix = new Tile[BOARD_DIMENSION][BOARD_DIMENSION];

        for (int i = 0; i <= BOARD_DIMENSION; i++) {
            for (int j = 0; i <= BOARD_DIMENSION; i++) {
                Cell c = matrix[i][j];

                if (c.isDead()) {
                    continue;
                }

                if (c.isEmpty()) {
                    // no content
                    tileMatrix[i][j] = null;
                } else {
                    // contains a tile
                    tileMatrix[i][j] = c.getContent().get();
                }
            }
        }

        return tileMatrix;
    }

    /**
     * @param c are the coordinates of the cell we have to analyse
     * @return returns the free edges of the tile at the given coordinates
     */
    public int countFreeEdges(Coordinates c) {
        return Arrays.stream(BoardUtils.Edge.values())
                .mapToInt(it -> BoardUtils.hasFreeEdge(this, c, it) ? 1 : 0)
                .sum();
    }

    /**
     * @param c are the coordinates of the cell we have to analyse
     * @return returns 1 if it has free edges, 0 if it doesn't
     */
    public boolean hasAtLeastOneFreeEdge(Coordinates c) {
        return countFreeEdges(c) > 0;
    }

    /**
     * counts all the board's empty cells
     *
     * @param upperbound is the highest accepted cell pattern: if the analised cell pattern is higher, then we do not
     *                   count it, if it's lower (or equal) and it is empty we count it as an acceptable cell
     * @return it returns the number of empty acceptable cells
     */
    public int countEmptyCells(CellPattern upperbound) {
        int emptyCellsCounter = 0;

        for (int i = 0; i < BOARD_DIMENSION; i++) {
            for (int j = 0; j < BOARD_DIMENSION; j++) {
                Cell c = matrix[i][j];

                if (c.isDead())
                    continue;

                boolean isAcceptable = c.getPattern().getPlayerCount() <= upperbound.getPlayerCount();

                if (isAcceptable && c.isEmpty()) {
                    emptyCellsCounter++;
                }
            }
        }

        return emptyCellsCounter;
    }

    /**
     * Fills the board with the provided list of {@link it.polimi.ingsw.model.board.Tile}s.
     * Runs under the assumption that the given tile list contains the same number of elements as
     * there are empty spaces in the board.
     *
     * @param newElements list of tiles to be inserted in the board matrix
     * @param upperBound  highest pattern of cell to be considered
     * @throws IllegalArgumentException if the list of new tiles has a greater size than how many
     *                                  empty spaces there are on the board
     * @see Board#countEmptyCells(CellPattern)
     */
    public void fill(List<Tile> newElements, CellPattern upperBound) {
        if (newElements.size() >= countEmptyCells(upperBound)) {
            throw new IllegalArgumentException("Impossible to fit the designated elements inside the board: not enough space");
        }

        // we use this to stop when the list is empty
        Iterator<Tile> iterator = newElements.iterator();

        for (int i = 0; i < BOARD_DIMENSION; i++) {
            for (int j = 0; j < BOARD_DIMENSION; j++) {
                Cell c = matrix[i][j];

                if (c.isDead()) {
                    continue;
                }

                boolean isAcceptable = c.getPattern().getPlayerCount() <= upperBound.getPlayerCount();

                if (c.isEmpty() && isAcceptable) {
                    // Cell should be filled
                    if (iterator.hasNext()) {
                        // we have a matching element
                        c.setContent(iterator.next());
                    } else {
                        // we are out of elements
                        return;
                    }
                }
            }
        }
    }
}