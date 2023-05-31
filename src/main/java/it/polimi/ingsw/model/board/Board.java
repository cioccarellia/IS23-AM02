package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.cell.Cell;
import it.polimi.ingsw.model.board.cell.CellPattern;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import it.polimi.ingsw.model.game.CellInfo;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.utils.model.BoardUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;

import static it.polimi.ingsw.model.board.cell.CellPattern.*;


/**
 * Implements the game board and its functionalities
 */
public class Board implements Serializable {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(Board.class);

    // configuration parameters
    private final int dimension = BoardConfiguration.getInstance().getDimension();

    /**
     * Matrix of {@link Cell}s, containing tiles.
     */
    private final Cell[][] matrix = new Cell[dimension][dimension];


    /**
     * Builds an empty board with different cells (dead, empty, with tile)
     */
    public Board() {
        var defaultBoardPattern = BoardConfiguration.getInstance().getMatrix();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                CellPattern defaultPattern = defaultBoardPattern[i][j];

                if (defaultPattern == null) {
                    matrix[i][j] = new Cell(NORMAL, false);
                } else {
                    matrix[i][j] = new Cell(defaultPattern, true);
                }
            }
        }

        logger.info("Board initialized");
    }

    public void setTile(CellInfo cellInfo) {
        int x = cellInfo.coordinate().x();
        int y = cellInfo.coordinate().y();

        Cell c = matrix[x][y];

        if (c.isEmpty()) {
            matrix[x][y].setContent(cellInfo.tile());
        }
    }

    /**
     * @return the cell matrix of the board
     */
    public Cell[][] getCellMatrix() {
        return matrix;
    }

    /**
     * @param c are the coordinates at which we have to get the tile (cell's content)
     * @return the cell content (tile or null) at the given coordinates
     */
    public Optional<Tile> getTileAt(@NotNull Coordinate c) {
        Cell required = matrix[c.x()][c.y()];
        return required.getContent();
    }


    /**
     * @param c are the coordinates at which we have to get the cell
     * @return the cell at the given coordinates
     */
    @TestOnly
    public Cell getCellAt(@NotNull Coordinate c) {
        logger.warn("calling test method getCellAt({})", c);
        return matrix[c.x()][c.y()];
    }


    /**
     * Removes the tile from the cell at the given coordinates
     *
     * @param c are the coordinates at which we have to remove the tile from the cell
     */
    public void removeTileAt(@NotNull Coordinate c) {
        matrix[c.x()][c.y()].clear();
    }

    /**
     * Returns the board current setup
     */
    public Tile[][] getTileMatrix() {
        Tile[][] tileMatrix = new Tile[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
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
    public int countFreeEdges(@NotNull Coordinate c) {
        return Arrays.stream(BoardUtils.Edge.values()).mapToInt(it -> BoardUtils.hasFreeEdge(this, c, it) ? 1 : 0).sum();
    }

    /**
     * @return if the board needs filling: you must refill the board when, at the end of a turn, on the board there are
     * only tiles without any other adjacent tile, meaning that the next player would have to take only one tile in
     * their turn.
     */
    public boolean needsRefilling() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (getTileMatrix()[i][j] == null) {
                    continue;
                }
                if (countFreeEdges(new Coordinate(i, j)) < 4)
                    return false;
            }
        }
        return true;
    }

    /**
     * @param c are the coordinates of the cell we have to analyse
     * @return returns 1 if it has free edges, 0 if it doesn't
     */
    public boolean hasAtLeastOneFreeEdge(@NotNull Coordinate c) {
        return countFreeEdges(c) > 0;
    }


    /**
     * @param mode the game mode, meaning how many players are playing the game
     * @return the cell pattern corresponding to the number of players
     */
    private CellPattern mapFromGameMode(@NotNull GameMode mode) {
        switch (mode) {
            case GAME_MODE_2_PLAYERS -> {
                return NORMAL;
            }
            case GAME_MODE_3_PLAYERS -> {
                return THREE_DOTS;
            }
            case GAME_MODE_4_PLAYERS -> {
                return FOUR_DOTS;
            }
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        }
    }

    /**
     * @return the map of tile type and amount of that specific tile that have been removed from the board before
     * refilling it
     */
    public Map<Tile, Integer> removeRemainingTiles() {
        Map<Tile, Integer> removedTiles = new HashMap<>();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Cell c = matrix[i][j];

                if (c.isDead() || c.isEmpty() || c.getContent().isPresent()) continue;

                Tile tileType = c.getContent().get();

                int removedTileAmount = removedTiles.get(tileType);

                removedTiles.put(tileType, removedTileAmount + 1);
            }
        }

        return removedTiles;
    }

    /**
     * Counts all the board's empty cells
     *
     * @param mode is the highest accepted cell pattern: if the analized cell pattern is higher, then we do not
     *             count it, if it's lower (or equal) and it is empty we count it as an acceptable cell
     * @return it returns the number of empty acceptable cells
     */
    public int countEmptyCells(@NotNull GameMode mode) {
        CellPattern upperBound = mapFromGameMode(mode);

        int emptyCellsCounter = 0;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Cell c = matrix[i][j];

                if (c.isDead()) continue;

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
     * @param newElements list of tiles to be inserted in the board matrix
     * @param mode        highest pattern of cell to be considered
     * @throws IllegalArgumentException if the list of new tiles has a greater size than how many
     *                                  empty spaces there are on the board
     * @see Board#countEmptyCells(GameMode)
     */
    public void fill(@NotNull List<Tile> newElements, GameMode mode) {
        CellPattern upperBound = mapFromGameMode(mode);

        if (newElements.size() > countEmptyCells(mode)) {
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

    @Override
    public String toString() {
        return "Board{" +
                "dimension=" + dimension +
                ", matrix=" + Arrays.toString(matrix) +
                '}';
    }
}