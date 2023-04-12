package it.polimi.ingsw.controller.result.failures;

public enum SignUpRequest implements RequestError {
    ALREADY_CONNECTED_PLAYER,
    USERNAME_ALREADY_IN_USE,
    MAX_PLAYER_REACHED,
    WRONG_ACTIVE_PLAYER,
    GAME_ALREADY_ENDED
}