package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.result.SingleResult;
import it.polimi.ingsw.controller.result.failures.SignUpRequest;
import it.polimi.ingsw.controller.result.failures.StatusError;
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

        SingleResult<StatusError> a = controller.gameStartedRequest(GAME_MODE_4_PLAYERS, "alberto", RMI);

        var b = controller.gameConnectionRequest("cookie", SOCKET);
        var c = controller.gameConnectionRequest("giulia", RMI);
        var d = controller.gameConnectionRequest("marco", SOCKET);

        var err = controller.gameConnectionRequest("__err", SOCKET);

        switch (a) {
            case SingleResult.Success<StatusError> success -> {
                // ok
            }
            case SingleResult.Failure<StatusError> failure -> fail("Sign up request should have been successful");
        }

        switch (err) {
            case SingleResult.Success<SignUpRequest> success -> {
                fail("Sign up request should have been unsuccessful");
            }
            case SingleResult.Failure<SignUpRequest> failure -> {
                // ok
            }
        }


        assertTrue(a instanceof SingleResult.Success<StatusError>);
        assertTrue(b instanceof SingleResult.Success<SignUpRequest>);
        assertTrue(c instanceof SingleResult.Success<SignUpRequest>);
        assertTrue(d instanceof SingleResult.Success<SignUpRequest>);
        assertTrue(err instanceof SingleResult.Failure<SignUpRequest>);
    }

}
