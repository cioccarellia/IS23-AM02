package it.polimi.ingsw.model.bookshelf;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.board.Tile.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BookShelfInsertTest implements ShelfMatrixTester {
    @Test
    @DisplayName("Verify the correct functionality of insert method in an empty bookshelf, positively")
    public void test_insert_method_empty_bookShelf_positively() {
        Bookshelf shelf = new Bookshelf();
        List<Tile> tiles = List.of(CAT, TROPHY, PLANT);

        final int column = 1;
        shelf.insert(column, tiles);

        Tile[][] matrix = shelf.getShelfMatrix();

        assertEquals(CAT, matrix[5][column]);
        assertEquals(TROPHY, matrix[4][column]);
        assertEquals(PLANT, matrix[3][column]);
        assertNull(matrix[2][column]);
        assertNull(matrix[1][column]);
        assertNull(matrix[0][column]);

    }

}
