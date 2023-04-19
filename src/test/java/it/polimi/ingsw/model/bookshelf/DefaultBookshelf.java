package it.polimi.ingsw.model.bookshelf;

import it.polimi.ingsw.model.board.Tile;

import static it.polimi.ingsw.model.board.Tile.*;

public class DefaultBookshelf {

    public static final Tile[][] BOOKSHELF_MATRIX = {
            {CAT, CAT, CAT, CAT, CAT},
            {CAT, CAT, CAT, CAT, CAT},
            {CAT, CAT, CAT, CAT, CAT},
            {CAT, CAT, CAT, CAT, CAT},
            {CAT, CAT, CAT, CAT, CAT},
            {CAT, CAT, CAT, CAT, CAT}

    };

    public static final Tile[][] NOT_FULL_BOOKSHELF_MATRIX = {
            {CAT, CAT, null, CAT, CAT},
            {CAT, CAT, null, CAT, CAT},
            {CAT, CAT, CAT, CAT, CAT},
            {CAT, CAT, CAT, CAT, CAT},
            {CAT, CAT, CAT, CAT, CAT},
            {CAT, CAT, CAT, CAT, CAT}
    };

}
