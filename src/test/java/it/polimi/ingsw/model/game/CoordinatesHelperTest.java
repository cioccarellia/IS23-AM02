package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.utils.model.CoordinatesHelper.areCoordinatesInStraightLine;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CoordinatesHelperTest {

    // tests with no tiles
    @Test
    @DisplayName("Tests the correct function of areCoordinatesInStraightLine negatively, no tiles")
    public void areCoordinatesInStraightLineNegativelyForNoTiles_test() {
        assertFalse(areCoordinatesInStraightLine(null));
    }


    // tests with one tile
    @Test
    @DisplayName("Tests the correct function of areCoordinatesInStraightLine positively, one tile - 1")
    public void areCoordinatesInStraightLinePositivelyForOneTile_test1() {
        Coordinate testCoo1 = new Coordinate(5, 0);

        assertTrue(areCoordinatesInStraightLine(List.of(testCoo1)));
    }

    @Test
    @DisplayName("Tests the correct function of areCoordinatesInStraightLine positively, one tile - 2")
    public void areCoordinatesInStraightLinePositivelyForOneTile_test2() {
        Coordinate testCoo1 = new Coordinate(4, 4);

        assertTrue(areCoordinatesInStraightLine(List.of(testCoo1)));
    }


    // tests with two tiles - same column
    @Test
    @DisplayName("Tests the correct function of areCoordinatesInStraightLine positively, two tiles in column - 1")
    public void areCoordinatesInStraightLinePositivelyForTwoTilesInColumn_test1() {
        Coordinate testCoo1 = new Coordinate(4, 8);
        Coordinate testCoo2 = new Coordinate(5, 8);

        List<Coordinate> testCooList = List.of(testCoo1, testCoo2);

        assertTrue(areCoordinatesInStraightLine(testCooList));
    }

    @Test
    @DisplayName("Tests the correct function of areCoordinatesInStraightLine positively, two tiles in column - 2")
    public void areCoordinatesInStraightLinePositivelyForTwoTilesInColumn_test2() {
        Coordinate testCoo1 = new Coordinate(0, 3);
        Coordinate testCoo2 = new Coordinate(1, 3);

        List<Coordinate> testCooList = List.of(testCoo1, testCoo2);

        assertTrue(areCoordinatesInStraightLine(testCooList));
    }

    @Test
    @DisplayName("Tests the correct function of areCoordinatesInStraightLine negatively, two tiles in column - 1")
    public void areCoordinatesInStraightLineNegativelyForTwoTilesInColumn_test1() {
        Coordinate testCoo1 = new Coordinate(4, 4);
        Coordinate testCoo2 = new Coordinate(5, 5);

        List<Coordinate> testCooList = List.of(testCoo1, testCoo2);

        assertFalse(areCoordinatesInStraightLine(testCooList));
    }

    @Test
    @DisplayName("Tests the correct function of areCoordinatesInStraightLine negatively, two tiles in column - 2")
    public void areCoordinatesInStraightLineNegativelyForTwoTilesInColumn_test2() {
        Coordinate testCoo1 = new Coordinate(0, 3);
        Coordinate testCoo2 = new Coordinate(5, 3);

        List<Coordinate> testCooList = List.of(testCoo1, testCoo2);

        assertFalse(areCoordinatesInStraightLine(testCooList));
    }


    // tests with two tiles - same row
    @Test
    @DisplayName("Tests the correct function of areCoordinatesInStraightLine positively, two tiles in row - 1")
    public void areCoordinatesInStraightLinePositivelyForTwoTilesInRow_test1() {
        Coordinate testCoo1 = new Coordinate(1, 3);
        Coordinate testCoo2 = new Coordinate(1, 4);

        List<Coordinate> testCooList = List.of(testCoo1, testCoo2);

        assertTrue(areCoordinatesInStraightLine(testCooList));
    }

    @Test
    @DisplayName("Tests the correct function of areCoordinatesInStraightLine positively, two tiles in row - 2")
    public void areCoordinatesInStraightLinePositivelyForTwoTilesInRow_test2() {
        Coordinate testCoo1 = new Coordinate(6, 3);
        Coordinate testCoo2 = new Coordinate(6, 4);

        List<Coordinate> testCooList = List.of(testCoo1, testCoo2);

        assertTrue(areCoordinatesInStraightLine(testCooList));
    }

    @Test
    @DisplayName("Tests the correct function of areCoordinatesInStraightLine negatively, two tiles in row - 1")
    public void areCoordinatesInStraightLineNegativelyForTwoTilesInRow_test1() {
        Coordinate testCoo1 = new Coordinate(4, 4);
        Coordinate testCoo2 = new Coordinate(3, 8);

        List<Coordinate> testCooList = List.of(testCoo1, testCoo2);

        assertFalse(areCoordinatesInStraightLine(testCooList));
    }

    @Test
    @DisplayName("Tests the correct function of areCoordinatesInStraightLine negatively, two tiles in row - 2")
    public void areCoordinatesInStraightLineNegativelyForTwoTilesInRow_test2() {
        Coordinate testCoo1 = new Coordinate(4, 0);
        Coordinate testCoo2 = new Coordinate(4, 3);

        List<Coordinate> testCooList = List.of(testCoo1, testCoo2);

        assertFalse(areCoordinatesInStraightLine(testCooList));
    }


    // tests with three tiles - same column
    @Test
    @DisplayName("Test the correct function of areCoordinatesInStraightLine positively, three tiles in column - 1")
    public void areCoordinatesInStraightLinePositivelyForThreeTilesInColumn_test1() {
        Coordinate testCoo1 = new Coordinate(2, 3);
        Coordinate testCoo2 = new Coordinate(3, 3);
        Coordinate testCoo3 = new Coordinate(4, 3);

        List<Coordinate> testCoolist = List.of(testCoo1, testCoo2, testCoo3);

        assertTrue(areCoordinatesInStraightLine(testCoolist));
    }

    @Test
    @DisplayName("Test the correct function of areCoordinatesInStraightLine positively, three tiles in column - 2")
    public void areCoordinatesInStraightLinePositivelyForThreeTilesInColumn_test2() {
        Coordinate testCoo1 = new Coordinate(2, 3);
        Coordinate testCoo2 = new Coordinate(3, 3);
        Coordinate testCoo3 = new Coordinate(4, 3);

        List<Coordinate> testCoolist = List.of(testCoo1, testCoo2, testCoo3);

        assertTrue(areCoordinatesInStraightLine(testCoolist));
    }

    @Test
    @DisplayName("Test the correct function of areCoordinatesInStraightLine negatively, three tiles in column - 1")
    public void areCoordinatesInStraightLineNegativelyForThreeTilesInColumn_test1() {
        Coordinate testCoo1 = new Coordinate(2, 4);
        Coordinate testCoo2 = new Coordinate(3, 3);
        Coordinate testCoo3 = new Coordinate(4, 4);

        List<Coordinate> testCoolist = List.of(testCoo1, testCoo2, testCoo3);

        assertFalse(areCoordinatesInStraightLine(testCoolist));
    }

    @Test
    @DisplayName("Test the correct function of areCoordinatesInStraightLine negatively, three tiles in column - 2")
    public void areCoordinatesInStraightLineNegativelyForThreeTilesInColumn_test2() {
        Coordinate testCoo1 = new Coordinate(2, 4);
        Coordinate testCoo2 = new Coordinate(3, 4);
        Coordinate testCoo3 = new Coordinate(6, 4);

        List<Coordinate> testCoolist = List.of(testCoo1, testCoo2, testCoo3);

        assertFalse(areCoordinatesInStraightLine(testCoolist));
    }


    // tests with three tiles - same row
    @Test
    @DisplayName("Test the correct function of areCoordinatesInStraightLine positively, three tiles in row - 1")
    public void areCoordinatesInStraightLinePositivelyForThreeTilesInRow_test1() {
        Coordinate testCoo1 = new Coordinate(5, 1);
        Coordinate testCoo2 = new Coordinate(5, 2);
        Coordinate testCoo3 = new Coordinate(5, 3);

        List<Coordinate> testCoolist = List.of(testCoo1, testCoo2, testCoo3);

        assertTrue(areCoordinatesInStraightLine(testCoolist));
    }

    @Test
    @DisplayName("Test the correct function of areCoordinatesInStraightLine positively, three tiles in row - 2")
    public void areCoordinatesInStraightLinePositivelyForThreeTilesInRow_test2() {
        Coordinate testCoo1 = new Coordinate(1, 3);
        Coordinate testCoo2 = new Coordinate(1, 4);
        Coordinate testCoo3 = new Coordinate(1, 5);

        List<Coordinate> testCoolist = List.of(testCoo1, testCoo2, testCoo3);

        assertTrue(areCoordinatesInStraightLine(testCoolist));
    }

    @Test
    @DisplayName("Test the correct function of areCoordinatesInStraightLine negatively, three tiles in row - 1")
    public void areCoordinatesInStraightLineNegativelyForThreeTilesInRow_test1() {
        Coordinate testCoo1 = new Coordinate(2, 2);
        Coordinate testCoo2 = new Coordinate(3, 2);
        Coordinate testCoo3 = new Coordinate(4, 3);

        List<Coordinate> testCoolist = List.of(testCoo1, testCoo2, testCoo3);

        assertFalse(areCoordinatesInStraightLine(testCoolist));
    }

    @Test
    @DisplayName("Test the correct function of areCoordinatesInStraightLine negatively, three tiles in row - 2")
    public void areCoordinatesInStraightLineNegativelyForThreeTilesInRow_test2() {
        Coordinate testCoo1 = new Coordinate(2, 2);
        Coordinate testCoo2 = new Coordinate(2, 4);
        Coordinate testCoo3 = new Coordinate(2, 5);

        List<Coordinate> testCoolist = List.of(testCoo1, testCoo2, testCoo3);

        assertFalse(areCoordinatesInStraightLine(testCoolist));
    }
}
