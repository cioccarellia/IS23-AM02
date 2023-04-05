package it.polimi.ingsw.controller.result.failures;

import it.polimi.ingsw.controller.result.RequestError;

public enum SignUpRequest implements RequestError {
    ALREADY_CONNECTED_PLAYER,
    MAX_PLAYER_REACHED
}