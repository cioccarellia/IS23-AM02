package it.polimi.ingsw.controller.result.failures;

public enum BookshelfInsertionFailure implements RequestError {
    WRONG_SELECTION,
    ILLEGAL_COLUMN,
    TOO_MANY_TILES,
    NO_FIT,
    WRONG_PLAYER,
    WRONG_STATUS,
}