package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.server.GameController;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameStartError;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.launcher.parameters.ClientProtocol.RMI;
import static it.polimi.ingsw.launcher.parameters.ClientProtocol.SOCKET;
import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_4_PLAYERS;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class BaseControllerTests {

    @Test
    public void x() {
        GameController controller = new GameController();

        SingleResult<GameStartError> a = controller.gameStartRequest(GAME_MODE_4_PLAYERS, "alberto", RMI);

        var b = controller.gameConnectionRequest("cookie", SOCKET);
        var c = controller.gameConnectionRequest("giulia", RMI);
        var d = controller.gameConnectionRequest("marco", SOCKET);

        var err = controller.gameConnectionRequest("__err", SOCKET);

        switch (a) {
            case SingleResult.Success<GameStartError> success -> {
                // ok
            }
            case SingleResult.Failure<GameStartError> failure -> fail("Sign up request should have been successful");
        }

        switch (err) {
            case SingleResult.Success<GameConnectionError> success -> {
                fail("Sign up request should have been unsuccessful");
            }
            case SingleResult.Failure<GameConnectionError> failure -> {
                // ok
            }
        }


        assertTrue(a instanceof SingleResult.Success<GameStartError>);
        assertTrue(b instanceof SingleResult.Success<GameConnectionError>);
        assertTrue(c instanceof SingleResult.Success<GameConnectionError>);
        assertTrue(d instanceof SingleResult.Success<GameConnectionError>);
        assertTrue(err instanceof SingleResult.Failure<GameConnectionError>);
    }

}
