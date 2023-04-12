package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.result.SingleResult;
import it.polimi.ingsw.controller.result.failures.StatusError;
import it.polimi.ingsw.model.game.GameMode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseControllerTests {

    GameController controller = new GameController(GameMode.GAME_MODE_4_PLAYERS);

    @Test
    public void x() {
        var a = controller.onPlayerConnection("alberto");
        var b = controller.onPlayerConnection("cookie");
        var c = controller.onPlayerConnection("giulia");
        var d = controller.onPlayerConnection("marco");

        switch (a) {
            case SingleResult.Success<StatusError> s -> {

            }
            case SingleResult.Failure<StatusError> failure -> {

            }
        }

        assertTrue(a instanceof SingleResult.Success<StatusError>);
        assertTrue(b instanceof SingleResult.Success<StatusError>);
        assertTrue(c instanceof SingleResult.Success<StatusError>);
        assertTrue(d instanceof SingleResult.Success<StatusError>);

    }

}
