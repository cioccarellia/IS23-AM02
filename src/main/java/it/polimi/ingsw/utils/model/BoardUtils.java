package it.polimi.ingsw.utils.model;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Coordinate;

/**
 * Utility class for {@link Board}
 */
public class BoardUtils {

    /**
     * Given a coordinate (which identifies a cell), checks whether a specific edge of that cell is free
     *
     * @param board      game board
     * @param coordinate position of referenced cell
     * @param edge       direction to check the free edge in
     * @return whether the selected cell has a free edge in that direction
     */
    public static boolean hasFreeEdge(final Board board, Coordinate coordinate, Edge edge) {
        Coordinate shiftedCoordinate;

        switch (edge) {
            case TOP -> {
                if (coordinate.y() - 1 < 0) {
                    return true;
                }

                shiftedCoordinate = new Coordinate(coordinate.x(), coordinate.y() - 1);
            }
            case LEFT -> {
                if (coordinate.x() - 1 < 0) {
                    return true;
                }

                shiftedCoordinate = new Coordinate(coordinate.x() - 1, coordinate.y());
            }
            case BOTTOM -> {
                if (coordinate.y() + 1 > 9) {
                    return true;
                }

                shiftedCoordinate = new Coordinate(coordinate.x(), coordinate.y() + 1);
            }
            case RIGHT -> {
                if (coordinate.x() + 1 > 9) {
                    return true;
                }

                shiftedCoordinate = new Coordinate(coordinate.x() + 1, coordinate.y());
            }
            default -> throw new IllegalStateException();
        }

        return board.getTileAt(shiftedCoordinate).isEmpty();
    }

    public enum Edge {
        TOP, LEFT, BOTTOM, RIGHT
    }
}
