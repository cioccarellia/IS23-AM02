package it.polimi.ingsw.controller.server.result.types;

import it.polimi.ingsw.model.GameModel;

public record TileInsertionSuccess(
        GameModel model
) implements RequestType {

}