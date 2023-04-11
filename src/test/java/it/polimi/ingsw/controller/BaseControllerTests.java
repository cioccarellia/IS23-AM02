package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.result.SingleResult;
import it.polimi.ingsw.controller.result.failures.StatusError;
import it.polimi.ingsw.model.game.GameMode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BaseControllerTests {

    GameController controller = new GameController(GameMode.GAME_MODE_4_PLAYERS);

    @Test
    @DisplayName("Checks whether adding more than max players throws an error")
    public void x() {
        var a = controller.onPlayerConnection("alberto");
        var b = controller.onPlayerConnection("cookie");
        var c = controller.onPlayerConnection("giulia");
        var d = controller.onPlayerConnection("marco");
        var e = controller.onPlayerConnection("__err");

        switch (e) {
            case SingleResult.Success<StatusError> success -> {
                fail();
            }
            case SingleResult.Failure<StatusError> failure -> {
                assertEquals(StatusError.MAX_PLAYERS_REACHED, failure.error());
            }
        }

        assertTrue(a instanceof SingleResult.Success<StatusError>);
        assertTrue(b instanceof SingleResult.Success<StatusError>);
        assertTrue(c instanceof SingleResult.Success<StatusError>);
        assertTrue(d instanceof SingleResult.Success<StatusError>);
        assertTrue(e instanceof SingleResult.Failure<StatusError>);

    }

}
