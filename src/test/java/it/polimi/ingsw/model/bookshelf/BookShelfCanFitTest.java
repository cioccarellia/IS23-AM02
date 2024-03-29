package it.polimi.ingsw.model.bookshelf;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.board.Tile.*;
import static it.polimi.ingsw.model.bookshelf.DefaultBookshelf.BOOKSHELF_MATRIX;
import static it.polimi.ingsw.model.bookshelf.DefaultBookshelf.NOT_FULL_BOOKSHELF_MATRIX;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookShelfCanFitTest implements ShelfMatrixTester {
    private static final int maxSelectionSize = LogicConfiguration.getInstance().maxSelectionSize();

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
        shelf.fillUpBookShelf(BOOKSHELF_MATRIX);
        final int column = 1;

        assertFalse(shelf.canFit(column, maxSelectionSize));
    }

    @Test
    @DisplayName("Verify the correct functionality of canFit method in a filled up bookshelf, negatively")
    public void test_canFit_method_almost_full_bookShelf_negatively() {
        Bookshelf shelf = new Bookshelf();
        shelf.fillUpBookShelf(NOT_FULL_BOOKSHELF_MATRIX);
        final int column = 2;

        assertFalse(shelf.canFit(column, maxSelectionSize));
    }
}
