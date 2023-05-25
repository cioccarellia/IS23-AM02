package it.polimi.ingsw.controller.server.result.failures;

public enum TileSelectionFailures implements RequestError {
    WRONG_GAME_PHASE,
    UNAUTHORIZED_SELECTION,
    UNAUTHORIZED_PLAYER
}
