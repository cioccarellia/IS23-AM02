package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Coordinates;

/**
 * Utility class for {@link Board}
 */
public class BoardUtils {

    public enum Edge {
        TOP, LEFT, BOTTOM, RIGHT
    }

    /**
     * Given a coordinate (which identifies a cell), checks whether a specific edge of that cell is free
     *
     * @param board         game board
     * @param coordinates   position of referenced cell
     * @param edge          direction to check the free edge in
     * @return  whether the selected cell has a free edge in that direction
     */
    public static boolean hasFreeEdge(final Board board, Coordinates coordinates, Edge edge) {
        Coordinates shiftedCoordinates;

        switch (edge) {
            case TOP -> {
                if (coordinates.y() - 1 < 0) {
                    return true;
                }

                shiftedCoordinates = new Coordinates(coordinates.x(), coordinates.y() - 1);
            }
            case LEFT -> {
                if (coordinates.x() - 1 < 0) {
                    return true;
                }

                shiftedCoordinates = new Coordinates(coordinates.x() - 1, coordinates.y());
            }
            case BOTTOM -> {
                if (coordinates.y() + 1 > 9) {
                    return true;
                }

                shiftedCoordinates = new Coordinates(coordinates.x(), coordinates.y() + 1);
            }
            case RIGHT -> {
                if (coordinates.x() + 1 > 9) {
                    return true;
                }

                shiftedCoordinates = new Coordinates(coordinates.x() + 1, coordinates.y());
            }
            default -> throw new IllegalStateException();
        }

        return board.getTileAt(shiftedCoordinates).isEmpty();
    }
}
