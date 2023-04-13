package it.polimi.ingsw.model.bookshelf;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.board.Tile.*;
import static org.junit.jupiter.api.Assertions.*;

public class BookShelfTest implements ShelfMatrixTester {

    @Test
    @DisplayName("Verify the correct functionality of insert method, positively")
    public void test_insert_method_positively() {
        Bookshelf shelf = new Bookshelf();
        List<Tile> tiles = List.of(CAT, TROPHY, PLANT);

        final int column = 1;
        shelf.insert(column, tiles);

        Tile[][] matrix = shelf.getShelfMatrix();

        assertEquals(CAT, matrix[0][column]);
        assertEquals(TROPHY, matrix[1][column]);
        assertEquals(PLANT, matrix[2][column]);
        assertNull(matrix[3][column]);
        assertNull(matrix[4][column]);
    }
}