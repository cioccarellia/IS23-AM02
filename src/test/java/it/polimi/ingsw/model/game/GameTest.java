package it.polimi.ingsw.model.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class GameTest {

    @Test
    @DisplayName("Verify player adding positively")
    public void test_game_positively() {
        Game game = new Game(GameMode.GAME_MODE_2_PLAYERS);

        final String PLAYER_A = "PlayerA", PLAYER_B = "PlayerB";

        game.addPlayer(PLAYER_A);
        game.addPlayer(PLAYER_B);

        assertEquals(2, game.getPlayersMap().size());

        assertTrue(game.getPlayer(PLAYER_A).isPresent());
        assertTrue(game.getPlayer(PLAYER_B).isPresent());

        assertEquals(PLAYER_A, game.getPlayer(PLAYER_A).get().getUsername());
        assertEquals(PLAYER_B, game.getPlayer(PLAYER_B).get().getUsername());
    }
}
