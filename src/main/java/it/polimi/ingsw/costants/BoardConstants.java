package it.polimi.ingsw.costants;

import it.polimi.ingsw.model.board.cell.CellPattern;

public class BoardConstants {
    public static final int BOARD_DIMENSION = 9;

    public static final CellPattern[][] DFU_BOARD_MATRIX = {
            {null, null, null, CellPattern.THREE_DOTS, CellPattern.FOUR_DOTS, null, null, null, null},
            {null, null, null, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.NORMAL, null, null, null},
            {null, null, CellPattern.THREE_DOTS, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.THREE_DOTS, null, null},
            {null, CellPattern.FOUR_DOTS, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.THREE_DOTS},
            {CellPattern.FOUR_DOTS, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.FOUR_DOTS},
            {CellPattern.THREE_DOTS, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.FOUR_DOTS, null},
            {null, null, CellPattern.THREE_DOTS, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.THREE_DOTS, null, null},
            {null, null, null, CellPattern.FOUR_DOTS, CellPattern.NORMAL, CellPattern.NORMAL, null, null, null},
            {null, null, null, null, CellPattern.FOUR_DOTS, CellPattern.THREE_DOTS, null, null, null}
    };
}
