package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.cell.Cell;
import it.polimi.ingsw.model.board.cell.CellPattern;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.utils.model.BoardUtils;
import org.apache.commons.lang.SerializationUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


/**
 * Implements the game board and its functionalities
 */
public class Board {

    private final int dimension = BoardConfiguration.getInstance().getDimension();
    private final Cell[][] matrix = new Cell[dimension][dimension];


    /**
     * Builds an empty board with different cells (dead, empty, with tile)
     */
    public Board() {
        var defaultBoardPattern = BoardConfiguration.getInstance().getMatrix();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                CellPattern defaultPattern = defaultBoardPattern[i][j];

                if (defaultPattern == null) {
                    matrix[i][j] = new Cell(CellPattern.NORMAL, false);
                } else {
                    matrix[i][j] = new Cell(defaultPattern, true);
                }
            }
        }
    }

    public Cell[][] getCellMatrix() {
        // deep cloning
        return (Cell[][]) SerializationUtils.clone(matrix);
    }

    /**
     * Returns the cell content at the given coordinates
     *
     * @param c are the coordinates at which we have to get the tile (cell's content)
     * @return the tile at the given coordinates
     */
    public Optional<Tile> getTileAt(Coordinate c) {
        Cell required = matrix[c.x()][c.y()];
        return required.getContent();
    }


    @TestOnly
    @VisibleForTesting
    public Cell getCellAt(Coordinate c) {
        return matrix[c.x()][c.y()];
    }


    /**
     * Removes the tile from the cell at the given coordinates
     *
     * @param c are the coordinates at which we have to remove the tile from the cell
     */
    public void removeTileAt(Coordinate c) {
        matrix[c.x()][c.y()].clear();
    }

    /**
     * Returns the board current setup
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public Tile[][] getTileMatrix() {
        Tile[][] tileMatrix = new Tile[dimension][dimension];

        for (int i = 0; i <= dimension; i++) {
            for (int j = 0; i <= dimension; i++) {
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
    public int countFreeEdges(Coordinate c) {
        return Arrays.stream(BoardUtils.Edge.values())
                .mapToInt(it -> BoardUtils.hasFreeEdge(this, c, it) ? 1 : 0)
                .sum();
    }

    /**
     * @param c are the coordinates of the cell we have to analyse
     * @return returns 1 if it has free edges, 0 if it doesn't
     */
    public boolean hasAtLeastOneFreeEdge(Coordinate c) {
        return countFreeEdges(c) > 0;
    }


    private CellPattern mapFromGameMode(@NotNull GameMode mode) {
        switch (mode) {
            case GAME_MODE_2_PLAYERS -> {
                return CellPattern.NORMAL;
            }
            case GAME_MODE_3_PLAYERS -> {
                return CellPattern.THREE_DOTS;
            }
            case GAME_MODE_4_PLAYERS -> {
                return CellPattern.FOUR_DOTS;
            }
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        }
    }

    /**
     * counts all the board's empty cells
     *
     * @param mode      is the highest accepted cell pattern: if the analised cell pattern is higher, then we do not
     *                  count it, if it's lower (or equal) and it is empty we count it as an acceptable cell
     * @return it returns the number of empty acceptable cells
     */
    public int countEmptyCells(GameMode mode) {
        CellPattern upperBound = mapFromGameMode(mode);

        int emptyCellsCounter = 0;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Cell c = matrix[i][j];

                if (c.isDead())
                    continue;

                boolean isAcceptable = c.getPattern().getPlayerCount() <= upperBound.getPlayerCount();

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
     * @param newElements   list of tiles to be inserted in the board matrix
     * @param mode          highest pattern of cell to be considered
     * @throws IllegalArgumentException if the list of new tiles has a greater size than how many
     *                                  empty spaces there are on the board
     * @see Board#countEmptyCells(GameMode)
     */
    public void fill(List<Tile> newElements, GameMode mode) {
        CellPattern upperBound = mapFromGameMode(mode);

        if (newElements.size() >= countEmptyCells(mode)) {
            throw new IllegalArgumentException("Impossible to fit the designated elements inside the board: not enough space");
        }

        // we use this to stop when the list is empty
        Iterator<Tile> iterator = newElements.iterator();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
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