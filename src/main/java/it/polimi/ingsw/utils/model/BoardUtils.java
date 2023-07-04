package it.polimi.ingsw.utils.model;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.config.board.BoardConfiguration;

/**
 * Utility class for {@link Board}
 */
public class BoardUtils {

    private final static int dimension = BoardConfiguration.getInstance().getDimension();

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

        int topOutOfBounds = coordinate.x() - 1;
        int bottomOutOfBounds = coordinate.x() + 1;
        int leftOutOfBounds = coordinate.y() - 1;
        int rightOutOfBounds = coordinate.y() + 1;

        switch (edge) {
            case TOP -> {
                if (topOutOfBounds < 0) {
                    return true;
                }

                shiftedCoordinate = new Coordinate(coordinate.x() - 1, coordinate.y());
            }
            case LEFT -> {
                if (leftOutOfBounds < 0) {
                    return true;
                }

                shiftedCoordinate = new Coordinate(coordinate.x(), coordinate.y() - 1);
            }
            case BOTTOM -> {
                if (bottomOutOfBounds >= dimension) {
                    return true;
                }

                shiftedCoordinate = new Coordinate(coordinate.x() + 1, coordinate.y());
            }
            case RIGHT -> {
                if (rightOutOfBounds >= dimension) {
                    return true;
                }

                shiftedCoordinate = new Coordinate(coordinate.x(), coordinate.y() + 1);
            }
            default -> throw new IllegalStateException();
        }

        return board.getTileAt(shiftedCoordinate).isEmpty();
    }

    public enum Edge {
        TOP, LEFT, BOTTOM, RIGHT
    }
}
