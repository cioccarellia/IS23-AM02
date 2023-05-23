package it.polimi.ingsw.controller.server.result.types;

public record GameCreationSuccess(
        String username
) implements RequestType {
}
