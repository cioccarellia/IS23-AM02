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
        //FIXME change coordinates from list to set, is unuseful using a list for null parameters if is passed a test inside caller method

        if (coordinates == null || coordinates.isEmpty()) {
            return false;
        }

        if (coordinates.size() == 1) {
            return true;
        }

        int sameXs = 1, sameYs = 1;

        for (int i = 0; i < coordinates.size(); i++) {
            int xi = coordinates.get(i).x();
            int yi = coordinates.get(i).y();

            for (int j = i + 1; j < coordinates.size(); j++) {
                int xj = coordinates.get(j).x();
                int yj = coordinates.get(j).y();

                if (xi == xj)
                    sameXs++;

                if (yi == yj)
                    sameYs++;
            }


        }

        int hasPlus1 = 0, hasPlus2 = 0;

        if (sameXs == coordinates.size()) {
            int minY = coordinates.stream().min(Comparator.comparing(Coordinate::y)).get().y();

            for (Coordinate coordinate : coordinates) {
                if (coordinate.y() == minY + 1) {
                    hasPlus1++;
                } else if (coordinate.y() == minY + 2) {
                    hasPlus2++;
                }
            }

            if (coordinates.size() == 2 && hasPlus1 == 1 && hasPlus2 == 0) {
                return true;
            } else if (coordinates.size() == 3 && hasPlus1 == 1 && hasPlus2 == 1)
                return true;

            return false;

        } else if (sameYs == coordinates.size()) {
            int minX = coordinates.stream().min(Comparator.comparing(Coordinate::x)).get().x();

            for (Coordinate coordinate : coordinates) {
                if (coordinate.x() == minX + 1) {
                    hasPlus1++;
                } else if (coordinate.x() == minX + 2) {
                    hasPlus2++;
                }
            }

            if (coordinates.size() == 2 && hasPlus1 == 1 && hasPlus2 == 0) {
                return true;
            } else if (coordinates.size() == 3 && hasPlus1 == 1 && hasPlus2 == 1)
                return true;

            return false;
        }
        return false;

        /* //if we can make a copy that doesn't change the original, this one is better
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
        */

    }
}
