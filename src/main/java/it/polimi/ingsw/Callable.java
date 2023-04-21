package it.polimi.ingsw;

import it.polimi.ingsw.controller.result.SingleResult;
import it.polimi.ingsw.controller.result.failures.SignUpRequest;

import java.rmi.Remote;

public interface Callable extends Remote {
    SingleResult<SignUpRequest> onPlayerSignUpRequest(String username);
}
