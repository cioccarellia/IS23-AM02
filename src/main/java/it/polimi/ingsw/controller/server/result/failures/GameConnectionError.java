package it.polimi.ingsw.controller.server.result.failures;

public enum GameConnectionError implements RequestError {
    ALREADY_CONNECTED_PLAYER,
    USERNAME_ALREADY_IN_USE,
    MAX_PLAYER_REACHED,
    GAME_ALREADY_STARTED,
    GAME_ALREADY_ENDED
}