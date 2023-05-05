package it.polimi.ingsw.model.bookshelf;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.board.Tile.*;
import static it.polimi.ingsw.model.bookshelf.DefaultBookshelf.BOOKSHELF_MATRIX;
import static it.polimi.ingsw.model.bookshelf.DefaultBookshelf.NOT_FULL_BOOKSHELF_MATRIX;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookShelfIsFullTest implements ShelfMatrixTester {
    @Test
    @DisplayName("Verify the correct functionality of isFull method in almost filled up bookshelf, negatively")
    public void test_isFull_method_almost_full_bookShelf_negatively() {
        Bookshelf shelf = new Bookshelf();
        shelf.fillUpBookShelf(NOT_FULL_BOOKSHELF_MATRIX);

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
    @DisplayName("Verify the correct functionality of isFull method in a filled up bookshelf, positively")
    public void test_isFull_method_full_bookShelf_positively() {
        Bookshelf shelf = new Bookshelf();
        shelf.fillUpBookShelf(BOOKSHELF_MATRIX);

        assertTrue(shelf.isFull());

    }
}
