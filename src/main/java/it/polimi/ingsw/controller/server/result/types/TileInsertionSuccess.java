package it.polimi.ingsw.controller.server.result.types;

import it.polimi.ingsw.model.game.Game;

public record TileInsertionSuccess(
        Game model
) implements RequestType {

}