package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.game.extractors.TileExtractor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_2_PLAYERS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BoardFillTest {
    @Test
    @DisplayName("Tests the correct functionality of fill method, #1 positively")
    public void test_fill_method_positively_1() {
        Board board = new Board();
        TileExtractor extractor = new TileExtractor();

        board.fill(extractor.extractAmount(board.countEmptyCells(GAME_MODE_2_PLAYERS)), GAME_MODE_2_PLAYERS);
        assertEquals(0, board.countEmptyCells(GAME_MODE_2_PLAYERS));
    }

    @Test
    @DisplayName("Tests the correct functionality of fill method, #2 positively")
    public void test_fill_method_positively_2() {
        Board board = new Board();
        TileExtractor extractor = new TileExtractor();
        board.fill(extractor.extractAmount(board.countEmptyCells(GAME_MODE_2_PLAYERS)), GAME_MODE_2_PLAYERS);

        Coordinate c1 = new Coordinate(1, 3), c2 = new Coordinate(0, 3), c3 = new Coordinate(0, 4), c4 = new Coordinate(5, 5);

        assertEquals(2, board.countFreeEdges(c1));
        assertEquals(3, board.countFreeEdges(c2));
        assertEquals(3, board.countFreeEdges(c3));
        assertEquals(0, board.countFreeEdges(c4));
    }

    @Test
    @DisplayName("Tests the correct functionality of fill method, #3 positively")
    public void test_fill_method_positively_3() {
        Board board = new Board();
        TileExtractor extractor = new TileExtractor();

        board.fill(extractor.extractAmount(board.countEmptyCells(GAME_MODE_2_PLAYERS)), GAME_MODE_2_PLAYERS);
        Coordinate c = new Coordinate(5, 5);
        board.removeTileAt(c);
        assertEquals(Optional.empty(), board.getTileAt(c));
    }

    @Test
    @DisplayName("Tests the correct functionality of fill method, #4 positively")
    public void test_fill_method_positively_4() {
        Board board = new Board();
        TileExtractor extractor = new TileExtractor();

        board.fill(extractor.extractAmount(board.countEmptyCells(GAME_MODE_2_PLAYERS)), GAME_MODE_2_PLAYERS);
        Coordinate c = new Coordinate(5, 5);
        assertFalse(board.hasAtLeastOneFreeEdge(c));
    }
}
