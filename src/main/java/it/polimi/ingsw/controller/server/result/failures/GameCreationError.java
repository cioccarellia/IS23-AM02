package it.polimi.ingsw.controller.server.result.failures;

public enum GameCreationError implements RequestError {
    GAME_ALREADY_INITIALIZING,
    GAME_ALREADY_RUNNING,
    INVALID_USERNAME
}