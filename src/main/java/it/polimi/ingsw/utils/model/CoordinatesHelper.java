package it.polimi.ingsw.utils.model;

import it.polimi.ingsw.model.board.Coordinate;

import java.util.Comparator;
import java.util.List;

public class CoordinatesHelper {

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

        int sameXs = 1, sameYs = 1;

        int x = coordinates.get(0).x();
        int y = coordinates.get(0).y();

        for (int i = 1; i < coordinates.size(); i++) {
            int xi = coordinates.get(i).x();
            int yi = coordinates.get(i).y();

            if (x == xi)
                sameXs++;

            if (y == yi)
                sameYs++;
        }

        if (sameXs == coordinates.size()) {
            List<Coordinate> orderedCoordinates = coordinates.stream().sorted(Comparator.comparing(Coordinate::y)).toList();

            for (int i = 0; i < orderedCoordinates.size() - 1; i++) {
                if (orderedCoordinates.get(i + 1).y() == orderedCoordinates.get(i).y() + 1)
                    continue;
                return false;
            }
        } else if (sameYs == coordinates.size()) {
            List<Coordinate> orderedCoordinates = coordinates.stream().sorted(Comparator.comparing(Coordinate::x)).toList();

            for (int i = 0; i < orderedCoordinates.size() - 1; i++) {
                if (orderedCoordinates.get(i + 1).x() == orderedCoordinates.get(i).x() + 1)
                    continue;
                return false;
            }
        }

        return sameXs == coordinates.size() || sameYs == coordinates.size();
    }
}
