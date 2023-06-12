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

        /*
        Coordinate first = coordinates.get(0);
        Coordinate last = coordinates.get(coordinates.size() - 1);

        if (first.x() == last.x()) { // Horizontal line
            int minY = Math.min(first.y(), last.y());
            int maxY = Math.max(first.y(), last.y());

            for (int i = minY + 1; i < maxY && i < dimension; i++) {
                Coordinate curr = new Coordinate(first.x(), i);
                if (!coordinates.contains(curr)) {
                    return false;
                }
            }
        } else if (first.y() == last.y()) { // Vertical line
            int minX = Math.min(first.x(), last.x());
            int maxX = Math.max(first.x(), last.x());

            for (int i = minX + 1; i < maxX && i < dimension; i++) {
                Coordinate curr = new Coordinate(i, first.y());
                if (!coordinates.contains(curr)) {
                    return false;
                }
            }
        } else { // Not a straight line
            return false;
        }
        return true;
        */

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
