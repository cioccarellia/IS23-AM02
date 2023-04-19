package it.polimi.ingsw.model.groupfinder;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.groupfinder.Group;
import it.polimi.ingsw.groupfinder.GroupFinder;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GroupFinderMapPartitionTest implements ShelfMatrixTester {

    @Test
    @DisplayName("Verifies the GroupFinder on a column matrix, two groups")
    public void groupfind_columnMatrix_positive_1() {
        // matrix to be tested
        Tile[][] threeAdjacentColumns = {
                {Tile.PLANT, Tile.PLANT, null, null, Tile.GAME},
                {Tile.PLANT, Tile.PLANT, null, null, Tile.GAME},
                {Tile.PLANT, Tile.PLANT, null, null, Tile.GAME},
                {Tile.PLANT, Tile.PLANT, null, null, Tile.GAME},
                {Tile.PLANT, Tile.PLANT, null, null, Tile.GAME},
                {Tile.PLANT, Tile.PLANT, null, null, Tile.GAME}
        };

        // group computation
        GroupFinder f = new GroupFinder(threeAdjacentColumns);
        Map<Tile, List<Group>> groupMap = f.computeGroupPartitionMap();

        // map has only two tile types, so only two map entries should be generated
        assertEquals(2, groupMap.keySet().size());


        // expected group result for Tile.PLANT
        List<Group> plantPartition = List.of(
                new Group(Tile.PLANT, 12)
        );

        // checking Tile.PLANT's expected group is the only group in its keyset
        assertEquals(1, groupMap.get(Tile.PLANT).size());
        assertTrue(
                groupMap.get(Tile.PLANT).containsAll(plantPartition)
        );


        // expected group result for Tile.GAME
        List<Group> gamePartition = List.of(
                new Group(Tile.GAME, 6)
        );

        // checking Tile.PLANT's expected group is the only group in its keyset
        assertEquals(1, groupMap.get(Tile.GAME).size());
        assertTrue(
                groupMap.get(Tile.GAME).containsAll(gamePartition)
        );
    }


    @Test
    @DisplayName("Verifies the GroupFinder on a column matrix, 5 groups, 2 types")
    public void groupfind_columnMatrix_positive_2() {
        // matrix to be tested
        Tile[][] threeAdjacentColumns = {
                {Tile.PLANT, Tile.GAME, Tile.PLANT, Tile.GAME, Tile.PLANT},
                {Tile.PLANT, Tile.GAME, Tile.PLANT, Tile.GAME, Tile.PLANT},
                {Tile.PLANT, Tile.GAME, Tile.PLANT, Tile.GAME, Tile.PLANT},
                {Tile.PLANT, Tile.GAME, Tile.PLANT, Tile.GAME, Tile.PLANT},
                {Tile.PLANT, Tile.GAME, Tile.PLANT, Tile.GAME, Tile.PLANT},
                {Tile.PLANT, Tile.GAME, Tile.PLANT, Tile.GAME, Tile.PLANT}
        };

        // group computation
        GroupFinder f = new GroupFinder(threeAdjacentColumns);
        Map<Tile, List<Group>> groupMap = f.computeGroupPartitionMap();

        // map has only two tile types, so only two map entries should be generated
        assertEquals(2, groupMap.keySet().size());


        // expected group result for Tile.PLANT
        List<Group> plantPartition = List.of(
                new Group(Tile.PLANT, 6),
                new Group(Tile.PLANT, 6),
                new Group(Tile.PLANT, 6)
        );

        // checking Tile.PLANT's expected group is the only group in its keyset
        assertEquals(3, groupMap.get(Tile.PLANT).size());
        assertTrue(
                groupMap.get(Tile.PLANT).containsAll(plantPartition)
        );


        // expected group result for Tile.GAME
        List<Group> gamePartition = List.of(
                new Group(Tile.GAME, 6),
                new Group(Tile.GAME, 6)
        );

        // checking Tile.PLANT's expected group is the only group in its keyset
        assertEquals(2, groupMap.get(Tile.GAME).size());
        assertTrue(
                groupMap.get(Tile.GAME).containsAll(gamePartition)
        );
    }
}
