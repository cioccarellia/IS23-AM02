package it.polimi.ingsw.controller.server.result.types;

import it.polimi.ingsw.model.game.Game;

public record TileSelectionSuccess(
        Game model
) implements RequestType {

}