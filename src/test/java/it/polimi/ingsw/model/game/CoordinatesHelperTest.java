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
    @DisplayName("Test the correct function of areCoordinatesInStraightLine positively for rows, edge case")
    public void areCoordinatesInStraightLinePositivelyForRowsEdgeCaseTest() {
        Coordinate testingCoordinate1 = new Coordinate(1, 3);
        Coordinate testingCoordinate2 = new Coordinate(1, 4);

        List<Coordinate> testingCordianteList = List.of(new Coordinate[]{testingCoordinate1, testingCoordinate2});


        assertTrue(CoordinatesHelper.areCoordinatesInStraightLine(testingCordianteList));
    }

    @Test
    @DisplayName("Test the correct function of areCoordinatesInStraightLine positively for columns, edge case")
    public void areCoordinatesInStraightLinePositivelyForColumnsEdgeCaseTest() {
        Coordinate testingCoordinate2 = new Coordinate(7, 4);
        Coordinate testingCoordinate1 = new Coordinate(7, 5);


        List<Coordinate> testingCordianteList = List.of(testingCoordinate1, testingCoordinate2);


        assertTrue(CoordinatesHelper.areCoordinatesInStraightLine(testingCordianteList));
    }

    @Test
    @DisplayName("Test the correct function of areCoordinatesInStraightLine positively for columns, edge case #2")
    public void areCoordinatesInStraightLinePositivelyForColumnsEdgeCaseTest2() {
        Coordinate testingCoordinate1 = new Coordinate(4, 7);
        Coordinate testingCoordinate2 = new Coordinate(5, 7);


        List<Coordinate> testingCordianteList = List.of(testingCoordinate1, testingCoordinate2);


        assertTrue(CoordinatesHelper.areCoordinatesInStraightLine(testingCordianteList));
    }

    @Test
    @DisplayName("Test the correct function of areCoordinatesInStraightLine positively for columns, edge case #3")
    public void areCoordinatesInStraightLinePositivelyForColumnsEdgeCaseTest3() {
        Coordinate testingCoordinate1 = new Coordinate(4, 8);
        Coordinate testingCoordinate2 = new Coordinate(5, 8);


        List<Coordinate> testingCordianteList = List.of(testingCoordinate1, testingCoordinate2);


        assertTrue(CoordinatesHelper.areCoordinatesInStraightLine(testingCordianteList));
    }

    @Test
    @DisplayName("Test the correct function of areCoordinatesInStraightLine positively for columns, edge case #4")
    public void areCoordinatesInStraightLinePositivelyForColumnsEdgeCaseTest4() {
        Coordinate testingCoordinate1 = new Coordinate(0, 9);
        Coordinate testingCoordinate2 = new Coordinate(1, 9);


        List<Coordinate> testingCordianteList = List.of(testingCoordinate1, testingCoordinate2);


        assertTrue(CoordinatesHelper.areCoordinatesInStraightLine(testingCordianteList));
    }

    @Test
    @DisplayName("Test the correct function of areCoordinatesInStraightLine negatively for columns, edge case")
    public void areCoordinatesInStraightLineNegativelyForColumnsEdgeCaseTest() {
        Coordinate testingCoordinate1 = new Coordinate(0, 9);
        Coordinate testingCoordinate2 = new Coordinate(2, 9);


        List<Coordinate> testingCordianteList = List.of(testingCoordinate1, testingCoordinate2);


        assertFalse(CoordinatesHelper.areCoordinatesInStraightLine(testingCordianteList));
    }

    @Test
    @DisplayName("Test the correct function of areCoordinatesInStraightLine negatively for columns, edge case #2")
    public void areCoordinatesInStraightLineNegativelyForColumnsEdgeCaseTest2() {
        Coordinate testingCoordinate1 = new Coordinate(0, 9);
        Coordinate testingCoordinate2 = new Coordinate(3, 4);


        List<Coordinate> testingCordianteList = List.of(testingCoordinate1, testingCoordinate2);


        assertFalse(CoordinatesHelper.areCoordinatesInStraightLine(testingCordianteList));
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
        Coordinate testingCoordinate1 = new Coordinate(2, 4);
        Coordinate testingCoordinate2 = new Coordinate(3, 3);
        Coordinate testingCoordinate3 = new Coordinate(4, 4);

        List<Coordinate> testingCordianteList = List.of(new Coordinate[]{testingCoordinate1, testingCoordinate2, testingCoordinate3});


        assertFalse(CoordinatesHelper.areCoordinatesInStraightLine(testingCordianteList));
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
