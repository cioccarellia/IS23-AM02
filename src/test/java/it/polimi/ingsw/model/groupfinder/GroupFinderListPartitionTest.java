package it.polimi.ingsw.model.groupfinder;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.groupfinder.Group;
import it.polimi.ingsw.groupfinder.GroupFinder;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.board.Tile.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupFinderListPartitionTest implements ShelfMatrixTester {

    @Test
    @DisplayName("Verifies the GroupFinder on a column matrix")
    public void groupfind_columnMatrix_positive_1() {
        // matrix to be tested
        Tile[][] threeAdjacentColumns = {
                {CAT, CAT, null, null, GAME},
                {CAT, CAT, null, null, GAME},
                {CAT, CAT, null, null, GAME},
                {CAT, CAT, null, null, GAME},
                {CAT, CAT, null, null, GAME},
                {CAT, CAT, null, null, GAME}
        };

        // expected result
        List<Group> correctPartition = List.of(
                new Group(CAT, 12),
                new Group(GAME, 6)
        );

        // group computation
        GroupFinder f = new GroupFinder(threeAdjacentColumns);
        List<Group> groups = f.computeGroupPartition();

        // assertions
        assertTrue(groups.containsAll(correctPartition));
        assertEquals(2, groups.size());
    }


    @Test
    @DisplayName("Verifies the GroupFinder on a column matrix")
    public void groupfind_columnMatrix_positive_2() {
        // matrix to be tested
        Tile[][] threeAdjacentColumns = {
                {CAT, GAME, FRAME, TROPHY, GAME},
                {CAT, GAME, FRAME, TROPHY, GAME},
                {CAT, GAME, FRAME, TROPHY, GAME},
                {CAT, GAME, FRAME, TROPHY, GAME},
                {CAT, GAME, FRAME, TROPHY, GAME},
                {null, GAME, FRAME, TROPHY, GAME}
        };

        // expected result
        List<Group> correctPartition = List.of(
                new Group(CAT, 5),
                new Group(GAME, 6),
                new Group(FRAME, 6),
                new Group(TROPHY, 6),
                new Group(GAME, 6)
        );

        // group computation
        GroupFinder f = new GroupFinder(threeAdjacentColumns);
        List<Group> groups = f.computeGroupPartition();

        // assertions
        assertTrue(groups.containsAll(correctPartition));
        assertEquals(5, groups.size());
    }


    @Test
    @DisplayName("Null matrix should not produce any group")
    public void groupfind_sparseMatrix() {
        // expected result
        List<Group> correctPartition = List.of();

        // group computation
        GroupFinder f = new GroupFinder(nullMatrix);
        List<Group> groups = f.computeGroupPartition();

        // assertions
        assertTrue(groups.containsAll(correctPartition));
        assertEquals(0, groups.size());
    }


    @Test
    @DisplayName("Null matrix should not produce any group")
    public void groupfind_fullMatrix() {
        // expected result
        List<Group> correctPartition = List.of(
                new Group(PLANT, 30)
        );

        // group computation
        GroupFinder f = new GroupFinder(generateFullMatrixOf(PLANT));
        List<Group> groups = f.computeGroupPartition();

        // assertions
        assertTrue(groups.containsAll(correctPartition));
        assertEquals(1, groups.size());
    }

}
