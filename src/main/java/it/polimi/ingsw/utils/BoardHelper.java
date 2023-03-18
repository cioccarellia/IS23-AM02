package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Coordinates;

/**
 * Utility class to check properties about coordinates
 */
public class BoardHelper {
    public enum Edge {
        TOP, LEFT, BOTTOM, RIGHT;
    }

    /**
     * throughout "Strategy" Pattern, return true if side cells is empty
     * @param board
     * @param coordinates
     * @param edge
     * @return
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
