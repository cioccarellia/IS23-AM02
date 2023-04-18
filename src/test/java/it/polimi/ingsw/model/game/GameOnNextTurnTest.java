package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.player.action.PlayerCurrentGamePhase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameOnNextTurnTest implements GameTester{

    @Test
    @DisplayName("Verify the function of onNextTurn, positively ")
    public void test_onNextTurn_positively(){
        Game testingGame=new Game(GameMode.GAME_MODE_2_PLAYERS);
        testingGame.addPlayer(PLAYER_A);
        testingGame.addPlayer(PLAYER_B);

        testingGame.onNextTurn(PLAYER_B);

        assertEquals(PlayerCurrentGamePhase.SELECTING,testingGame.getSessionFor(PLAYER_B).getPlayerCurrentGamePhase());
    }

    @Test
    @DisplayName("Verify the function of onNextTurn, negatively ")
    public void test_onNextTurn_negatively(){
        Game testingGame=new Game(GameMode.GAME_MODE_2_PLAYERS);
        testingGame.addPlayer(PLAYER_A);
        testingGame.addPlayer(PLAYER_B);

        testingGame.onNextTurn(PLAYER_B);

        boolean isFalse= testingGame.getSessionFor(PLAYER_B).getPlayerCurrentGamePhase().equals(PlayerCurrentGamePhase.IDLE);
        assertFalse(isFalse);
    }

}
