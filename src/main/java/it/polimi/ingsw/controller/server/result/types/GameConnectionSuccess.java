package it.polimi.ingsw.controller.server.result.types;

public record GameConnectionSuccess(
        String username
) implements RequestType {
}
