package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.utils.model.CoordinatesHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CoordinatesHelperTest {

    @Test
    @DisplayName("Test the correct function of areCoordinatesInStraightLine positively for rows")
    public void areCoordinatesInStraightLinePositivelyForRowsTest() {
        Coordinate testingCoordinate1 = new Coordinate(5, 1);
        Coordinate testingCoordinate2 = new Coordinate(5, 2);
        Coordinate testingCoordinate3 = new Coordinate(5, 3);

        List<Coordinate> testingCordianteList = List.of(new Coordinate[]{testingCoordinate1, testingCoordinate2, testingCoordinate3});


        assertTrue(CoordinatesHelper.areCoordinatesInStraightLine(testingCordianteList));
    }

    @Test
    @DisplayName("Test the correct function of areCoordinatesInStraightLine positively for Columns")
    public void areCoordinatesInStraightLinePositivelyForColumnsTest() {
        Coordinate testingCoordinate1 = new Coordinate(2, 3);
        Coordinate testingCoordinate2 = new Coordinate(3, 3);
        Coordinate testingCoordinate3 = new Coordinate(4, 3);

        List<Coordinate> testingCordianteList = List.of(new Coordinate[]{testingCoordinate1, testingCoordinate2, testingCoordinate3});


        assertTrue(CoordinatesHelper.areCoordinatesInStraightLine(testingCordianteList));
    }

    @Test
    @DisplayName("Test the correct function of areCoordinatesInStraightLine negatively for Columns")
    public void areCoordinatesInStraightLineNegativelyForColumnsTest() {
        Coordinate testingCoordinate1 = new Coordinate(2, 3);
        Coordinate testingCoordinate2 = new Coordinate(3, 3);
        Coordinate testingCoordinate3 = new Coordinate(4, 3);

        List<Coordinate> testingCordianteList = List.of(new Coordinate[]{testingCoordinate1, testingCoordinate2, testingCoordinate3});


        assertTrue(CoordinatesHelper.areCoordinatesInStraightLine(testingCordianteList));
    }

    @Test
    @DisplayName("Test the correct function of areCoordinatesInStraightLine negatively for rows")
    public void areCoordinatesInStraightLineNegativelyTest() {
        Coordinate testingCoordinate1 = new Coordinate(2, 2);
        Coordinate testingCoordinate2 = new Coordinate(3, 2);
        Coordinate testingCoordinate3 = new Coordinate(4, 3);

        List<Coordinate> testingCordianteList = List.of(new Coordinate[]{testingCoordinate1, testingCoordinate2, testingCoordinate3});


        assertFalse(CoordinatesHelper.areCoordinatesInStraightLine(testingCordianteList));
    }

    @Test
    @DisplayName("Test the correct function of areCoordinatesInStraightLine negatively, edge case")
    public void areCoordinatesInStraightLineNegativelyEdgeCaseTest() {
        Coordinate testingCoordinate1 = new Coordinate(0, 1);
        Coordinate testingCoordinate2 = new Coordinate(1, 2);
        Coordinate testingCoordinate3 = new Coordinate(0, 3);

        List<Coordinate> testingCordianteList = List.of(new Coordinate[]{testingCoordinate1, testingCoordinate2, testingCoordinate3});


        assertFalse(CoordinatesHelper.areCoordinatesInStraightLine(testingCordianteList));
    }
}
