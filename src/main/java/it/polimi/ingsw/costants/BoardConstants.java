package it.polimi.ingsw.costants;

import it.polimi.ingsw.model.board.cell.CellPattern;

import static it.polimi.ingsw.model.board.cell.CellPattern.*;

public class BoardConstants {
    public static final int BOARD_DIMENSION = 9;

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

    public static final CellPattern[][] DFU_BOARD_MATRIX_PARTIAL = {
            {null, null, null, THREE_DOTS},
            {null, null, null, NORMAL},
            {null, null, THREE_DOTS, NORMAL},
            {null, FOUR_DOTS, NORMAL, NORMAL},
            {FOUR_DOTS, NORMAL, NORMAL, NORMAL},
    };

    public CellPattern[][] algorithmicPattern() {
        CellPattern[][] base = new CellPattern[BOARD_DIMENSION][BOARD_DIMENSION];

        base[4][4] = NORMAL;

        for (int i = 0; i < 5; i++) {
            System.arraycopy(DFU_BOARD_MATRIX_PARTIAL[i], 0, base[i], 0, 4);
        }

        for (int k = 5; k < 9; k++) {
            for (int z = 0; z < 5; z++) {
                base[k][z] = DFU_BOARD_MATRIX_PARTIAL[8 - z][k];
            }
        }

        for (int s = 4; s < 9; s++) {
            for (int t = 5; t < 9; t++) {
                base[s][t] = DFU_BOARD_MATRIX_PARTIAL[8 - s][8 - t];
            }
        }

        for (int h = 0; h < 4; h++) {
            for (int g = 4; g < 9; g++) {
                base[h][g] = DFU_BOARD_MATRIX_PARTIAL[g][8 - h];
            }
        }

        return base;
    }
}
