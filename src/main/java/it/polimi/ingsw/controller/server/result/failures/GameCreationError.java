package it.polimi.ingsw.controller.server.result.failures;

public enum GameCreationError implements RequestError {
    GAME_ALREADY_STARTED,
    INVALID_USERNAME
}