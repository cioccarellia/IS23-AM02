package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GameIsSelectionValidTest implements GameTester {
    @Test
    @DisplayName("verify the function isSelectionValid, positively #1")
    public void test_isSelectionValid_1_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        Coordinate c1 = new Coordinate(4, 1);
        Coordinate c2 = new Coordinate(5, 1);

        Set<Coordinate> selection = Set.of(c1, c2);

        assertTrue(game.isSelectionValid(selection));
    }

    @Test
    @DisplayName("verify the function isSelectionValid, positively #2")
    public void test_isSelectionValid_2_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        Coordinate c1 = new Coordinate(1, 5);
        Coordinate c2 = new Coordinate(1, 4);
        Coordinate coords3 = new Coordinate(1, 3);
        Set<Coordinate> selection = Set.of(c1, c2, coords3);

        assertTrue(game.isSelectionValid(selection));
    }

    @Test
    @DisplayName("verify the function isSelectionValid, positively #3")
    public void test_isSelectionValid_3_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        Coordinate c1 = new Coordinate(1, 5);
        Coordinate c2 = new Coordinate(1, 4);

        Set<Coordinate> selection = Set.of(c1, c2);

        assertTrue(game.isSelectionValid(selection));
    }

    @Test
    @DisplayName("verify the function isSelectionValid, negatively #1")
    public void test_isSelectionValid_1_negatively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        Coordinate c1 = new Coordinate(7, 1);
        Coordinate c2 = new Coordinate(7, 4);

        Set<Coordinate> selection = Set.of(c1, c2);

        assertFalse(game.isSelectionValid(selection));
    }

    @Test
    @DisplayName("verify the function isSelectionValid, negatively #2")
    public void test_isSelectionValid_2_negatively() {
        Game game = new Game(GameMode.GAME_MODE_3_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);
        game.addPlayer(PLAYER_C);

        game.onGameStarted();

        Coordinate c1 = new Coordinate(1, 4);
        Coordinate c2 = new Coordinate(1, 5);
        Coordinate c3 = new Coordinate(1, 8);

        Set<Coordinate> selection = Set.of(c1, c2, c3);

        assertFalse(game.isSelectionValid(selection));
    }

    @Test
    @DisplayName("verify the function isSelectionValid, negatively #3")
    public void test_isSelectionValid_3_negatively() {
        Game game = new Game(GameMode.GAME_MODE_3_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);
        game.addPlayer(PLAYER_C);

        game.onGameStarted();

        Coordinate c1 = new Coordinate(4, 2);
        Coordinate c2 = new Coordinate(4, 3);
        Coordinate c3 = new Coordinate(4, 4);

        Set<Coordinate> selection = Set.of(c1, c2, c3);

        assertFalse(game.isSelectionValid(selection));
    }
}
