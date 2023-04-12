package it.polimi.ingsw.model.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class GameTest {

    @Test
    @DisplayName("Verify player adding positively")
    //FIXME
    public void test_game_positively(){

        Game gameTester= new Game(GameMode.GAME_MODE_2_PLAYERS);

        gameTester.addPlayer("PlayerA");
        gameTester.addPlayer("PlayerB");

        assertEquals(2, gameTester.getPlayersMap().size());
        assertEquals("PlayerA", gameTester.getPlayer("PlayerA").toString());
        assertEquals("PlayerB", gameTester.getPlayer("PlayerB").toString());
    }
}
