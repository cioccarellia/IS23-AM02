package it.polimi.ingsw.controller.result.types;

import it.polimi.ingsw.model.board.Tile;

public record StatusSuccess(
        Tile[][] board
) implements RequestType{
}