package it.polimi.ingsw.model.board;

import java.io.Serializable;

/**
 * Utility class used to represent a coordinate in a matrix
 */
public record Coordinate(int x, int y) implements Serializable {
}