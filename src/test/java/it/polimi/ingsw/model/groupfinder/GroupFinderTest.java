package it.polimi.ingsw.model.groupfinder;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.groupfinder.Group;
import it.polimi.ingsw.groupfinder.GroupFinder;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupFinderTest implements ShelfMatrixTester {

    @Test
    @DisplayName("Verifies the GroupFinder on a column matrix")
    public void groupfind_columnMatrix_positive_1() {
        // matrix to be tested
        Tile[][] threeAdjacentColumns = {
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME},
                {Tile.CAT, Tile.CAT, null, null, Tile.GAME}
        };

        // expected result
        List<Group> correctPartition = List.of(
                new Group(Tile.CAT, 12),
                new Group(Tile.GAME, 6)
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
                {Tile.CAT, Tile.GAME, Tile.FRAME, Tile.TROPHY, Tile.GAME},
                {Tile.CAT, Tile.GAME, Tile.FRAME, Tile.TROPHY, Tile.GAME},
                {Tile.CAT, Tile.GAME, Tile.FRAME, Tile.TROPHY, Tile.GAME},
                {Tile.CAT, Tile.GAME, Tile.FRAME, Tile.TROPHY, Tile.GAME},
                {Tile.CAT, Tile.GAME, Tile.FRAME, Tile.TROPHY, Tile.GAME},
                {null, Tile.GAME, Tile.FRAME, Tile.TROPHY, Tile.GAME}
        };

        // expected result
        List<Group> correctPartition = List.of(
                new Group(Tile.CAT, 5),
                new Group(Tile.GAME, 6),
                new Group(Tile.FRAME, 6),
                new Group(Tile.TROPHY, 6),
                new Group(Tile.GAME, 6)
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
                new Group(Tile.PLANT, 30)
        );

        // group computation
        GroupFinder f = new GroupFinder(fullOf(Tile.PLANT));
        List<Group> groups = f.computeGroupPartition();

        // assertions
        assertTrue(groups.containsAll(correctPartition));
        assertEquals(1, groups.size());
    }

}
