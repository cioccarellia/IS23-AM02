package it.polimi.ingsw.model.bookshelf;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.board.cell.Cell;
import it.polimi.ingsw.utils.json.Parsers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static it.polimi.ingsw.model.board.Tile.*;
import static it.polimi.ingsw.model.board.cell.CellPattern.THREE_DOTS;

public class BookshelfUnitTest {

    @Test
    @DisplayName("Bookshelf matrix editing")
    public void test_shelf_matrix() {
        Bookshelf bookshelf = new Bookshelf();

        Tile[][] matrix = bookshelf.getShelfMatrix();

        matrix[0][0] = PLANT;

        //assertNull(bookshelf.getShelfMatrix()[0][0]);
    }


    @Test
    @DisplayName("Serialization with gson #1")
    public void test_serialization_gson_1() {
        Bookshelf bookshelf = new Bookshelf();

        bookshelf.insert(0, Arrays.asList(GAME, FRAME, PLANT));

        String jsonSerializedObject = Parsers.defaultGson().toJson(bookshelf);

        Bookshelf deserialized = Parsers.defaultGson().fromJson(jsonSerializedObject, Bookshelf.class);
    }


    @Test
    @DisplayName("Serialization with gson #2")
    public void test_serialization_gson_2() {
        Cell cell = new Cell(THREE_DOTS, true);

        String jsonCell = Parsers.defaultGson().toJson(cell);
        Bookshelf deserialized = Parsers.defaultGson().fromJson(jsonCell, Bookshelf.class);
    }

}
