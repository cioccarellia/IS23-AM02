package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.cell.Cell;
import it.polimi.ingsw.model.board.cell.CellPattern;
import it.polimi.ingsw.utils.BoardHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Board {
    private Cell[][] matrix;

    /**
     * Builds an empty board with different cells (dead, empty, full)
     */

    public Board() {

    }

    /**
     * @param c
     * @return
     */
    public Optional<Tile> getTileAt(Coordinates c) {
        Cell required = matrix[c.getX()][c.getY()];
        return required.getContent();
    }

    /**
     * @param c
     */
    public void removeTileAt(Coordinates c) {
        matrix[c.getX()][c.getY()] = null;
    }

    // TODO

    /**
     * @return
     */
    public Tile[][] getTileMatrix() {
        Tile[][] tileMatrix = new Tile[9][9];

        for (int i = 0; i <= 9; i++) {
            for (int j = 0; i <= 9; i++) {
                if (matrix[i][j].isDead()) {
                    continue;
                }

                if (matrix[i][j].isEmpty()) {
                    tileMatrix[i][j] = null;
                } else {
                    tileMatrix[i][j] = matrix[i][j].getContent().get();
                }

            }
        }

        return tileMatrix;
    }

    /**
     * @param c
     * @return
     */
    // da considerare le eccezioni delle carte sui lati della board
    public int countFreeEdges(Coordinates c) {
        return Arrays.stream(BoardHelper.Edge.values())
                .mapToInt(it -> BoardHelper.hasFreeEdge(this, c, it) ? 1 : 0)
                .sum();
    }

    public boolean hasAtLeastOneFreeEdge(Coordinates c) {
        return countFreeEdges(c) > 0;
    }

    /**
     * @param Upperbound
     * @return
     */
    public int countEmptyCells(CellPattern Upperbound) {
        // TODO
        return 0;
    }

    /**
     * @param newElements
     * @param numPlayers
     */
    public void fill(List<Tile> newElements, int numPlayers) {
        // TODO
    }

}