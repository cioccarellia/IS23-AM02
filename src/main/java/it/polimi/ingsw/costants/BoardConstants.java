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

    public static final CellPattern[][] DFU_BOARD_MATRIX2 = {
            {null, null, null, CellPattern.THREE_DOTS},
            {null, null, null, CellPattern.NORMAL},
            {null, null, CellPattern.THREE_DOTS, CellPattern.NORMAL},
            {null, CellPattern.FOUR_DOTS, CellPattern.NORMAL, CellPattern.NORMAL},
            {CellPattern.FOUR_DOTS, CellPattern.NORMAL, CellPattern.NORMAL, CellPattern.NORMAL},
    };
    public CellPattern[][] algorithmicPattern() {
        CellPattern[][] base = new CellPattern[BOARD_DIMENSION][BOARD_DIMENSION];

        for(int i=0;i<5;i++){
            for(int j=0;j<4;j++){
                base[i][j] = DFU_BOARD_MATRIX2[i][j];
            }
        }

        for(int k=5;k<9;k++){
            for(int z=0;z<5;z++){
                base[k][z] = DFU_BOARD_MATRIX2[8-z][k];
            }
        }

        for(int s=4;s<9;s++){
            for(int t=5;t<9;t++){
                base[s][t] = DFU_BOARD_MATRIX2[8-s][8-t];
            }
        }

        for(int h=0;h<4;h++){
            for(int g=4;g<9;g++){
                base[h][g] = DFU_BOARD_MATRIX2[g][8-h];
            }
        }

        return base;
    }
}
