package it.polimi.ingsw.model.bookshelf;

import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.board.Tile.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookShelfInsertTest {
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

    @Test
    @DisplayName("Verify the insert method throws out of bounds column exception")
    public void test_insert_out_of_bounds_column_exception() {
        Bookshelf shelf = new Bookshelf();
        List<Tile> tiles = List.of(CAT, FRAME, GAME);
        final int column = 6;


        Exception exception = assertThrows(IllegalArgumentException.class, () -> shelf.insert(column, tiles));

        String expectedMessage = "Received column index out of bounds. Expected [0,4], received %d".formatted(column);
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Verify the insert method throws too many tiles selected exception")
    public void test_insert_too_many_tiles_selected_exception() {
        Bookshelf shelf = new Bookshelf();
        List<Tile> tiles = List.of(CAT, FRAME, GAME, TROPHY, PLANT);
        final int column = 1;


        Exception exception = assertThrows(IllegalArgumentException.class, () -> shelf.insert(column, tiles));

        String expectedMessage = "Received a selection of tiles with more than three elements";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Verify the insert method throws can't fit tiles in that column exception")
    public void test_insert_cannot_fit_exception() {
        Bookshelf shelf = new Bookshelf();
        shelf.fillUpBookShelf(defaultBookshelf.BOOKSHELF_MATRIX);
        List<Tile> tiles = List.of(CAT, FRAME, GAME);
        final int column = 2;


        Exception exception = assertThrows(IllegalStateException.class, () -> shelf.insert(column, tiles));

        String expectedMessage = "Selected tiles do not fit in designated column";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
