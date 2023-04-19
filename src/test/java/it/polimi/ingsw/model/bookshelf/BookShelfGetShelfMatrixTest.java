package it.polimi.ingsw.model.bookshelf;

import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class BookShelfGetShelfMatrixTest implements ShelfMatrixTester {
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
}