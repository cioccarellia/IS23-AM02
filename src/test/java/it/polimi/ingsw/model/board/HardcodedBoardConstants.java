package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.cell.CellPattern;

import static it.polimi.ingsw.model.board.cell.CellPattern.*;

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
}
