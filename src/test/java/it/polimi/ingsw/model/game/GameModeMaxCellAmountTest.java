package it.polimi.ingsw.model.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.game.GameMode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameModeMaxCellAmountTest {

    @Test
    @DisplayName("Tests the correct functionality of countEmptyCells method, #1 positive case")
    public void test_gameModeMaxCellAmount_positively_1() {
        GameMode mode = GAME_MODE_4_PLAYERS;
        Game game = new Game(mode);
        assertEquals(game.getBoard().countEmptyCells(mode), game.getGameMode().maxCellAmount());
    }

    @Test
    @DisplayName("Tests the correct functionality of countEmptyCells method, #2 positive case")
    public void test_gameModeMaxCellAmount_positively_2() {
        GameMode mode = GAME_MODE_3_PLAYERS;
        Game game = new Game(mode);
        assertEquals(game.getBoard().countEmptyCells(mode), game.getGameMode().maxCellAmount());
    }

    @Test
    @DisplayName("Tests the correct functionality of countEmptyCells method, #3 positive case")
    public void test_gameModeMaxCellAmount_positively_3() {
        GameMode mode = GAME_MODE_2_PLAYERS;
        Game game = new Game(mode);
        assertEquals(game.getBoard().countEmptyCells(mode), game.getGameMode().maxCellAmount());
    }
}
