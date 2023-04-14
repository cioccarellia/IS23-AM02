package it.polimi.ingsw.model.bookshelf;


import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.board.Tile.*;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
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
/*
    @Test
    @DisplayName("Verify the correct functionality of insert method in an filled up bookshelf, positively")
    public void test_insert_method_full_bookShelf_positively() {
        Bookshelf shelf = new Bookshelf();
        shelf.fillUpBookShelf(defaultBookshelf.BOOKSHELF_MATRIX);
        List<Tile> tiles = List.of(CAT, TROPHY, PLANT);

        final int column = 1;
        shelf.insert(column,tiles);
        assertFalse(false);

    }
    */
    @Test
    @DisplayName("Verify the correct functionality of isFull method in an filled up bookshelf, positively")
    public void test_isFull_method_full_bookShelf_positively() {
        Bookshelf shelf = new Bookshelf();
        shelf.fillUpBookShelf(defaultBookshelf.BOOKSHELF_MATRIX);

        assertTrue(shelf.isFull());

    }

    @Test
    @DisplayName("Verify the correct functionality of isFull method in an empty bookshelf, positively")
    public void test_isFull_method_empty_bookShelf_positively() {
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
    @DisplayName("Verify the correct functionality of canFit method in an filled up bookshelf, positively")
    public void test_canFit_method_filledUp_bookShelf_positively() {
        Bookshelf shelf = new Bookshelf();
        shelf.fillUpBookShelf(defaultBookshelf.BOOKSHELF_MATRIX);
        final int column = 1;

        assertFalse(shelf.canFit(column,maxSelectionSize));
    }

    @Test
    @DisplayName("Verify the correct functionality of getShelfMatrix method in an filled up bookshelf, positively")
    public void test_getShelfMatrix_method_positively() {
        Bookshelf shelf = new Bookshelf();
        shelf.fillUpBookShelf(defaultBookshelf.BOOKSHELF_MATRIX);
        Tile[][] actualMatrix = shelf.getShelfMatrix();
        Tile[][] expectedMatrix= defaultBookshelf.BOOKSHELF_MATRIX;

        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                assertEquals(expectedMatrix[i][j],actualMatrix[i][j]);
            }
        }

    }

}