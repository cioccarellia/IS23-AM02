package it.polimi.ingsw.controller.result.failures;

public enum StatusError implements RequestError {
    GAME_ALREADY_ENDED,
    MAX_PLAYERS_REACHED,
    GENERIC_FAILURE
}