package it.polimi.ingsw.utils.model;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.config.board.BoardConfiguration;

import java.util.Comparator;
import java.util.List;

public class CoordinatesHelper {

    private final static int dimension = BoardConfiguration.getInstance().getDimension();

    /**
     * Returns true if all coordinates are in a straight line, either column or line
     */


    static public boolean areCoordinatesInStraightLine(List<Coordinate> coordinates) {

        if (coordinates == null || coordinates.isEmpty()) {
            return false;
        }

        if (coordinates.size() == 1) {
            return true;
        }
        Coordinate first = coordinates.get(0);
        Coordinate second = coordinates.get(1);

        if (coordinates.size() == 2) {
            return consecutiveCoordinates(coordinates, orizzontallyAlignedCoordinates(first, second), verticallyAlignedCoordinates(first, second));
        } else if (coordinates.size() == 3) {
            Coordinate third = coordinates.get(2);
            boolean sameXs = orizzontallyAlignedCoordinates(first, second) && orizzontallyAlignedCoordinates(second, third) && orizzontallyAlignedCoordinates(first, third);
            boolean sameys = verticallyAlignedCoordinates(first, second) && verticallyAlignedCoordinates(second, third) && verticallyAlignedCoordinates(first, third);
            return consecutiveCoordinates(coordinates, sameXs, sameys);
        }
        return false;
    }

    public static boolean verticallyAlignedCoordinates(Coordinate firstCoordinate, Coordinate secondCoordinate) {
        return firstCoordinate.y() == secondCoordinate.y();
    }

    public static boolean orizzontallyAlignedCoordinates(Coordinate firstCoordinate, Coordinate secondCoordinate) {
        return firstCoordinate.x() == secondCoordinate.x();
    }

    public static boolean consecutiveCoordinates(List<Coordinate> coordinates, boolean sameX, boolean sameY) {
        List<Coordinate> orderedCoordinates = coordinates.stream().sorted(Comparator.comparing(Coordinate::y)).toList();
        if (!sameX && !sameY) return false;
        if (sameX) {
            for (int i = 1; i < orderedCoordinates.size(); i++) {
                if ((coordinates.get(i).y() - coordinates.get(i - 1).y()) != 1) return false;
            }
        } else if (sameY) {
            for (int i = 1; i < orderedCoordinates.size(); i++) {
                if ((coordinates.get(i).x() - coordinates.get(i - 1).x()) != 1) return false;
            }
        }
        return true;
    }
}
