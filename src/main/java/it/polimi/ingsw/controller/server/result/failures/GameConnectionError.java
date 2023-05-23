package it.polimi.ingsw.controller.server.result.failures;

public enum GameConnectionError implements RequestError {
    ALREADY_CONNECTED_PLAYER,
    USERNAME_ALREADY_IN_USE,
    MAX_PLAYER_AMOUNT_EACHED,
    NO_GAME_TO_JOIN,
    GAME_ALREADY_STARTED,
    GAME_ALREADY_ENDED,
    INVALID_USERNAME
}