package it.polimi.ingsw.model.bookshelf;


import it.polimi.ingsw.commons.ShelfMatrixTester;
import it.polimi.ingsw.model.board.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.board.Tile.*;
import static org.junit.jupiter.api.Assertions.*;

public class BookShelfFillUpBookShelfTest implements ShelfMatrixTester {

    @Test
    @DisplayName("Verify the correct functionality of fillUpBookshelf method, positively")
    public void test_fillUpBookshelf_method_positively() {
        Bookshelf shelf = new Bookshelf();
        shelf.fillUpBookShelf(defaultBookshelf.NOT_FULL_BOOKSHELF_MATRIX);
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