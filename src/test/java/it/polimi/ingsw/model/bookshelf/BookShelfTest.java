package it.polimi.ingsw.model.bookshelf;


import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static it.polimi.ingsw.model.board.Tile.*;
import static org.junit.jupiter.api.Assertions.*;

public class BookShelfTest implements ShelfMatrixTester {
    private static final int maxSelectionSize = LogicConfiguration.getInstance().maxSelectionSize();

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
        shelf.fillUpBookShelf(DefaultBookshelf.BOOKSHELF_MATRIX);
        List<Tile> tiles = List.of(CAT, FRAME, GAME);
        final int column = 2;


        Exception exception = assertThrows(IllegalStateException.class, () -> shelf.insert(column, tiles));

        String expectedMessage = "Selected tiles do not fit in designated column";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Verify the correct functionality of isFull method in a filled up bookshelf, positively")
    public void test_isFull_method_full_bookShelf_positively() {
        Bookshelf shelf = new Bookshelf();
        shelf.fillUpBookShelf(DefaultBookshelf.BOOKSHELF_MATRIX);

        assertTrue(shelf.isFull());

    }

    @Test
    @DisplayName("Verify the correct functionality of isFull method in almost filled up bookshelf, negatively")
    public void test_isFull_method_almost_full_bookShelf_negatively() {
        Bookshelf shelf = new Bookshelf();
        shelf.fillUpBookShelf(DefaultBookshelf.NOT_FULL_BOOKSHELF_MATRIX);

        assertFalse(shelf.isFull());

    }

    @Test
    @DisplayName("Verify the correct functionality of isFull method in an empty bookshelf, negatively")
    public void test_isFull_method_empty_bookShelf_negatively() {
        Bookshelf shelf = new Bookshelf();
        List<Tile> tiles = List.of(CAT, TROPHY, PLANT);
        final int column = 1;
        shelf.insert(column, tiles);

        assertFalse(shelf.isFull());

    }

    @Test
    @DisplayName("Verify the correct functionality of canFit method in an empty bookshelf, positively")
    public void test_canFit_method_empty_bookShelf_positively() {
        Bookshelf shelf = new Bookshelf();
        List<Tile> tiles = List.of(CAT, TROPHY, PLANT);
        final int column = 2;
        shelf.insert(column, tiles);

        assertTrue(shelf.canFit(column, maxSelectionSize));

    }

    @Test
    @DisplayName("Verify the correct functionality of canFit method in a filled up bookshelf, negatively")
    public void test_canFit_method_filledUp_bookShelf_negatively() {
        Bookshelf shelf = new Bookshelf();
        shelf.fillUpBookShelf(DefaultBookshelf.BOOKSHELF_MATRIX);
        final int column = 1;

        assertFalse(shelf.canFit(column, maxSelectionSize));
    }

    @Test
    @DisplayName("Verify the correct functionality of canFit method in a filled up bookshelf, negatively")
    public void test_canFit_method_almost_full_bookShelf_negatively() {
        Bookshelf shelf = new Bookshelf();
        shelf.fillUpBookShelf(DefaultBookshelf.NOT_FULL_BOOKSHELF_MATRIX);
        final int column = 2;

        assertFalse(shelf.canFit(column, maxSelectionSize));
    }

    @Test
    @DisplayName("Verify the correct functionality of getShelfMatrix method in a filled up bookshelf, positively")
    public void test_getShelfMatrix_method_positively() {
        Bookshelf shelf = new Bookshelf();
        shelf.fillUpBookShelf(DefaultBookshelf.BOOKSHELF_MATRIX);
        Tile[][] actualMatrix = shelf.getShelfMatrix();
        Tile[][] expectedMatrix = DefaultBookshelf.BOOKSHELF_MATRIX;

        assertTrue(Objects.deepEquals(actualMatrix, expectedMatrix));

    }

    @Test
    @DisplayName("Verify the correct functionality of getShelfMatrix method in a filled up bookshelf, positively #2")
    public void test_getShelfMatrix_method_positively_2() {
        Bookshelf shelf = new Bookshelf();
        shelf.fillUpBookShelf(DefaultBookshelf.NOT_FULL_BOOKSHELF_MATRIX);
        Tile[][] actualMatrix = shelf.getShelfMatrix();
        Tile[][] expectedMatrix = DefaultBookshelf.NOT_FULL_BOOKSHELF_MATRIX;

        assertTrue(Objects.deepEquals(actualMatrix, expectedMatrix));

    }

    @Test
    @DisplayName("Verify the correct functionality of fillUpBookshelf method, positively")
    public void test_fillUpBookshelf_method_positively() {
        Bookshelf shelf = new Bookshelf();
        shelf.fillUpBookShelf(DefaultBookshelf.NOT_FULL_BOOKSHELF_MATRIX);
        Tile[][] matrix = shelf.getShelfMatrix();

        for (int i = 2; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                assertEquals(CAT, matrix[i][j]);
            }
        }
        assertEquals(CAT, matrix[0][0]);
        assertEquals(CAT, matrix[0][1]);
        assertEquals(CAT, matrix[0][3]);
        assertEquals(CAT, matrix[0][4]);
        assertEquals(CAT, matrix[1][0]);
        assertEquals(CAT, matrix[1][1]);
        assertEquals(CAT, matrix[1][3]);
        assertEquals(CAT, matrix[1][4]);

    }

}