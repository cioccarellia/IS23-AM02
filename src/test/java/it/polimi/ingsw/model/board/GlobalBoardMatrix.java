package it.polimi.ingsw.model.board;

public class GlobalBoardMatrix {

    static Tile[][] globalBoardMatrix = {
            {null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, Tile.GAME, null, null, null},
            {null, null, null, Tile.FRAME, Tile.GAME, Tile.GAME, null, null, null},
            {null, null, Tile.BOOK, Tile.CAT, Tile.FRAME, Tile.CAT, null, null, null},
            {null, Tile.BOOK, Tile.TROPHY, Tile.CAT, Tile.FRAME, Tile.GAME, Tile.BOOK, Tile.BOOK, null},
            {null, null, Tile.BOOK, Tile.TROPHY, Tile.TROPHY, Tile.GAME, null, null, null},
            {null, null, null, Tile.FRAME, Tile.GAME, null, null, null, null},
            {null, null, null, null, Tile.TROPHY, null, null, null, null},
            {null, null, null, null, null, null, null, null, null}

    };
}
