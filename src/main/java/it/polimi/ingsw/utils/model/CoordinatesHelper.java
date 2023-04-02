package it.polimi.ingsw.utils.model;

import it.polimi.ingsw.model.board.Coordinate;

import java.util.List;

public class CoordinatesHelper {
    /**
     * Returns true if all coordinates
     */
    static public boolean areCoordinatesInStraightLine(List<Coordinate> coordinates) {
        if (coordinates == null || coordinates.isEmpty()) {
            return false;
        }

        if (coordinates.size() == 1) {
            return true;
        }

        // sort the coordinates on the plane
        coordinates.sort((c1, c2) -> {
            if (c1.x() != c2.x()) {
                return Integer.compare(c1.x(), c2.x());
            } else {
                return Integer.compare(c1.y(), c2.y());
            }
        });


        Coordinate first = coordinates.get(0);
        Coordinate last = coordinates.get(coordinates.size() - 1);

        if (first.x() == last.x()) { // Vertical line
            int minY = Math.min(first.y(), last.y());
            int maxY = Math.max(first.y(), last.y());

            for (int i = minY + 1; i < maxY; i++) {
                Coordinate curr = new Coordinate(first.x(), i);
                if (!coordinates.contains(curr)) {
                    return false;
                }
            }
        } else if (first.y() == last.y()) { // Horizontal line
            int minX = Math.min(first.x(), last.x());
            int maxX = Math.max(first.x(), last.x());

            for (int i = minX + 1; i < maxX; i++) {
                Coordinate curr = new Coordinate(i, first.y());
                if (!coordinates.contains(curr)) {
                    return false;
                }
            }
        } else { // Not a straight line
            return false;
        }

        return true;
    }
}
