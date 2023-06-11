package it.polimi.ingsw.model.board;

import java.io.Serializable;

/**
 * Utility class used to represent a coordinate in a matrix
 */
public record Coordinate(int x, int y) implements Serializable {

    public static Coordinate parse(String s) {
        String[] tokens = s.split(",");

        String _x;
        String _y;

        try {
            _x = tokens[0];
            _y = tokens[1];
        } catch (IndexOutOfBoundsException ioob) {
            throw new IllegalArgumentException();
        }

        try {
            int x = Integer.parseInt(_x);
            int y = Integer.parseInt(_y);

            return new Coordinate(x, y);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException();
        }
    }

    public boolean equals(Coordinate c) {
        return this.x() == c.x() && this.y() == c.y();
    }
}