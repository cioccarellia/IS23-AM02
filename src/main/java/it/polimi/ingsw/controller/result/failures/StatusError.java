package it.polimi.ingsw.controller.result.failures;

import it.polimi.ingsw.controller.result.RequestError;

public enum StatusError implements RequestError {
    UNAUTHORIZED_OPERATION,
    GENERIC_FAILURE
}