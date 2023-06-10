package it.polimi.ingsw.controller.server.result.types;

import it.polimi.ingsw.model.GameModel;

public record TileSelectionSuccess(
        GameModel model
) implements RequestType {

}