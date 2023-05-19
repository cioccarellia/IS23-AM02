package it.polimi.ingsw.ui.cli.printer;

import it.polimi.ingsw.model.board.Tile;

import static it.polimi.ingsw.model.board.Tile.*;

public class ExampleCommonGoalCards {
    public static final Tile[][] SIX_PAIRS_EXAMPLE = {
            {CAT, null, null, null, null},
            {CAT, null, null, null, null},
            {PLANT, null, null, null, CAT},
            {PLANT, null, null, null, CAT},
            {CAT, null, null, null, GAME},
            {CAT, null, CAT, CAT, GAME}

    };
    public static final Tile[][] FOUR_GROUP_FOUR_EXAMPLE = {
            {null, PLANT, null, null, null},
            {null, PLANT, null, null, null},
            {null, PLANT, null, null, null},
            {null, PLANT, TROPHY, null, GAME},
            {CAT, CAT, TROPHY, GAME, GAME},
            {CAT, CAT, TROPHY, TROPHY, GAME}

    };
    public static final Tile[][] DIAGONAL_EXAMPLE = {
            {null, null, null, null, null},
            {CAT, null, null, null, null},
            {null, CAT, null, null, null},
            {null, null, CAT, null, null},
            {null, null, null, CAT, null},
            {null, null, null, null, CAT}

    };
    public static final Tile[][] FOUR_MAX3DIFF_LINES_EXAMPLE = {
            {PLANT, FRAME, PLANT, PLANT, CAT},
            {null, null, null, null, null},
            {GAME, GAME, GAME, GAME, GAME},
            {null, null, null, null, null},
            {PLANT, PLANT, PLANT, FRAME, FRAME},
            {CAT, FRAME, CAT, FRAME, GAME}

    };
    public static final Tile[][] FOUR_CORNERS_EXAMPLE = {
            {CAT, null, null, null, CAT},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {CAT, null, null, null, CAT}

    };
    public static final Tile[][] TWO_DIFF_COLUMNS_EXAMPLE = {
            {CAT, null, BOOK, null, null},
            {BOOK, null, TROPHY, null, null},
            {TROPHY, null, GAME, null, null},
            {GAME, null, CAT, null, null},
            {PLANT, null, PLANT, null, null},
            {FRAME, null, FRAME, null, null}

    };
    public static final Tile[][] TWO_SQUARES_EXAMPLE = {
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, PLANT, PLANT, null},
            {null, null, PLANT, PLANT, null},
            {CAT, CAT, null, null, null},
            {CAT, CAT, null, null, null}

    };
    public static final Tile[][] TWO_DIFF_LINES_EXAMPLE = {
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {CAT, GAME, FRAME, PLANT, BOOK},
            {null, null, null, null, null},
            {CAT, GAME, FRAME, PLANT, BOOK}

    };
    public static final Tile[][] THREE_MAX3DIFF_COLUMNS_EXAMPLE = {
            {GAME, FRAME, null, BOOK, null},
            {GAME, PLANT, null, BOOK, null},
            {GAME, PLANT, null, CAT, null},
            {GAME, FRAME, null, CAT, null},
            {GAME, CAT, null, BOOK, null},
            {GAME, CAT, null, BOOK, null}

    };
    public static final Tile[][] X_TILES_EXAMPLE = {
            {null, null, null, null, null},
            {null, null, null, null, null},
            {PLANT, null, PLANT, null, null},
            {null, PLANT, null, null, null},
            {PLANT, null, PLANT, null, null},
            {null, null, null, null, null}

    };
    public static final Tile[][] EIGHT_TILES_EXAMPLE = {
            {null, null, PLANT, null, PLANT},
            {null, null, null, null, null},
            {PLANT, null, PLANT, null, null},
            {null, PLANT, null, null, null},
            {PLANT, null, PLANT, null, null},
            {null, null, null, PLANT, null}

    };
    public static final Tile[][] STAIRS_EXAMPLE = {
            {null, null, null, null, null},
            {FRAME, null, null, null, null},
            {CAT, PLANT, null, null, null},
            {CAT, FRAME, CAT, null, null},
            {TROPHY, CAT, GAME, CAT, null},
            {CAT, PLANT, FRAME, CAT, CAT}

    };

}
