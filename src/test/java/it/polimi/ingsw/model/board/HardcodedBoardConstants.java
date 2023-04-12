package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.cell.CellPattern;
import org.jetbrains.annotations.TestOnly;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.Arrays;
import java.util.List;

import static it.polimi.ingsw.model.board.cell.CellPattern.*;
import static it.polimi.ingsw.model.board.Tile.*;

public class HardcodedBoardConstants {
    public static final CellPattern[][] DFU_BOARD_MATRIX = {
            {null, null, null, THREE_DOTS, FOUR_DOTS, null, null, null, null},
            {null, null, null, NORMAL, NORMAL, NORMAL, null, null, null},
            {null, null, THREE_DOTS, NORMAL, NORMAL, NORMAL, THREE_DOTS, null, null},
            {null, FOUR_DOTS, NORMAL, NORMAL, NORMAL, NORMAL, NORMAL, NORMAL, THREE_DOTS},
            {FOUR_DOTS, NORMAL, NORMAL, NORMAL, NORMAL, NORMAL, NORMAL, NORMAL, FOUR_DOTS},
            {THREE_DOTS, NORMAL, NORMAL, NORMAL, NORMAL, NORMAL, NORMAL, FOUR_DOTS, null},
            {null, null, THREE_DOTS, NORMAL, NORMAL, NORMAL, THREE_DOTS, null, null},
            {null, null, null, FOUR_DOTS, NORMAL, NORMAL, null, null, null},
            {null, null, null, null, FOUR_DOTS, THREE_DOTS, null, null, null}
    };

    public static final Tile[][] GLOBAL_BOARD_MATRIX = {
            {null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, GAME, null, null, null},
            {null, null, null, FRAME, GAME, GAME, null, null, null},
            {null, null, BOOK, CAT, FRAME, CAT, null, null, null},
            {null, BOOK, TROPHY, CAT, FRAME, GAME, BOOK, BOOK, null},
            {null, null, BOOK, TROPHY, TROPHY, GAME, null, null, null},
            {null, null, null, FRAME, GAME, null, null, null, null},
            {null, null, null, null, TROPHY, null, null, null, null},
            {null, null, null, null, null, null, null, null, null}

    };

    public static Tile getTile(int x, int y){
        return GLOBAL_BOARD_MATRIX[x][y];
    }

    public static List<Tile[]> tileToList(){
        return Arrays.stream(GLOBAL_BOARD_MATRIX).toList();
    }
}
