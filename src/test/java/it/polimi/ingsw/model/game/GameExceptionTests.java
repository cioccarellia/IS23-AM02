package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.board.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static it.polimi.ingsw.model.game.GameStatus.*;
import static it.polimi.ingsw.model.player.PlayerNumber.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameExceptionTests implements GameTester {


    @Test
    @DisplayName("Tests that starting a game without enough players throws an exception")
    public void test_onGameStarted_throwsException_NoEnoughPlayers() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer("A");

        Exception exception = assertThrows(IllegalStateException.class, game::onGameStarted);

        String expectedMessage = "Expected number of players (%d) differs from the actual number of players in game (%d)".formatted(2, 1);
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    @DisplayName("Tests that starting a game without enough players throws an exception")
    public void test_onGameStarted_throwsException_TooManyPlayers() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        final String PLAYER_A = "PlayerA", PLAYER_B = "PlayerB", PLAYER_C = "PlayerC", PLAYER_D = "PlayerD";

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        assertEquals(2, game.getPlayerNumberMap().size());

        assertEquals(PLAYER_A, game.getPlayerSession(PLAYER_A).getUsername());
        assertEquals(PLAYER_B, game.getPlayerSession(PLAYER_B).getUsername());

        Exception exception1 = assertThrows(IllegalStateException.class, () -> game.getPlayerSession(PLAYER_C));

        String expectedMessage1 = "Username not found in sessions";
        String actualMessage1 = exception1.getMessage();

        assertTrue(actualMessage1.contains(expectedMessage1));


        Exception exception2 = assertThrows(IllegalStateException.class, () -> game.getPlayerSession(PLAYER_D));

        String expectedMessage2 = "Username not found in sessions";
        String actualMessage2 = exception2.getMessage();

        assertTrue(actualMessage2.contains(expectedMessage2));
    }

    @Test
    @DisplayName("Tests that players can not be added after number of players reached")
    public void test_onGameStarted_throwsException_PlayersAddedAfterGameStarted() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer("A");
        game.addPlayer("B");


        Exception exception = assertThrows(IllegalStateException.class, () -> game.addPlayer(PLAYER_C));

        String expectedMessage = "Impossible to add a player: the game is full (having %d players for %s mode)".formatted(2, GameMode.GAME_MODE_2_PLAYERS);
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Tests that players can not be added after the game has started")
    public void test_onGameStarted_throwsException_PlayersAddedNotInInitializingGamePhase() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);
        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);
        game.onGameStarted();
        Exception exception = assertThrows(IllegalStateException.class, () -> game.addPlayer(PLAYER_C));

        String expectedMessage = "Impossible to add a player: current game phase (%s) not in INITIALIZATION".formatted(RUNNING);
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    @DisplayName("verify the function onPlayerSelectionPhase, coordinates are not valid exception")
    public void test_onPlayerSelectionPhase_coordinates_not_valid_exception() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        game.onGameStarted();

        Coordinate c1 = new Coordinate(0, 0);
        Coordinate c2 = new Coordinate(0, 1);

        Set<Coordinate> selection = Set.of(c1, c2);

        Exception exception = assertThrows(IllegalStateException.class, () -> game.onPlayerSelectionPhase(selection));

        String expectedMessage = "Coordinates are not valid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    @DisplayName("Verify the function of playerHasNoMoreTurns, edge case #1")
    public void test_playerHasNoMoreTurns_edgeCase_1() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        Exception exception = assertThrows(IllegalStateException.class, () -> game.playerHasNoMoreTurns(PLAYER_3));

        String expectedMessage = "Given number does not match to any session";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
