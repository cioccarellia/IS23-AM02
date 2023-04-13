package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.result.SingleResult;
import it.polimi.ingsw.controller.result.failures.SignUpRequest;
import it.polimi.ingsw.model.game.GameMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class BaseControllerTests {


    @Test
    public void x() {
        GameController controller = new GameController(GameMode.GAME_MODE_4_PLAYERS);

        var a = controller.onPlayerSignUpRequest("alberto");
        var b = controller.onPlayerSignUpRequest("cookie");
        var c = controller.onPlayerSignUpRequest("giulia");
        var d = controller.onPlayerSignUpRequest("marco");

        
        var err = controller.onPlayerSignUpRequest("__err");

        switch (a) {
            case SingleResult.Success<SignUpRequest> success -> {
                // ok
            }
            case SingleResult.Failure<SignUpRequest> failure -> {
                fail("Sign up request should have been successful");
            }
        }
        
        switch (err) {
            case SingleResult.Success<SignUpRequest> success -> {
                fail("Sign up request should have been unsuccessful");
            }
            case SingleResult.Failure<SignUpRequest> failure -> {
                // ok
            }
        }

        assertTrue(a instanceof SingleResult.Success<SignUpRequest>);
        assertTrue(b instanceof SingleResult.Success<SignUpRequest>);
        assertTrue(c instanceof SingleResult.Success<SignUpRequest>);
        assertTrue(d instanceof SingleResult.Success<SignUpRequest>);
        assertTrue(err instanceof SingleResult.Failure<SignUpRequest>);
    }

}
