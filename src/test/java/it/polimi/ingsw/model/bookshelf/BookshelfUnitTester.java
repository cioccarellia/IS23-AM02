package it.polimi.ingsw.model.bookshelf;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.board.cell.Cell;
import it.polimi.ingsw.model.board.cell.CellPattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BookshelfUnitTester {


    @Test
    @DisplayName("Bookshelf matrix editing")
    public void test_shelf_matrix() {
        Bookshelf bookshelf = new Bookshelf();

        Tile[][] clonedInternals = bookshelf.getShelfMatrix();

        clonedInternals[0][0] = Tile.PLANT;

        //assertNull(bookshelf.getShelfMatrix()[0][0]);
    }




    @Test
    @DisplayName("Serialization with gson #1")
    public void test_serialization_gson_1() {
        Bookshelf bookshelf = new Bookshelf();

        bookshelf.insert(0, Arrays.asList(Tile.GAME, Tile.FRAME, Tile.PLANT));

        Gson gson = new GsonBuilder().create();
        String jsonSerializedObject = gson.toJson(bookshelf);

        Bookshelf deserialized = gson.fromJson(jsonSerializedObject, Bookshelf.class);
    }



    @Test
    @DisplayName("Serialization with gson #2")
    public void test_serialization_gson_2() {
        Cell cell = new Cell(CellPattern.THREE_DOTS, true);

        Gson gson = new GsonBuilder().create();
        String jsonCell = gson.toJson(cell);

        Bookshelf deserialized = gson.fromJson(jsonCell, Bookshelf.class);
    }

}
