package it.polimi.ingsw.model.config.board;

import it.polimi.ingsw.model.board.cell.CellPattern;
import it.polimi.ingsw.model.config.Configuration;
import it.polimi.ingsw.utils.resources.ResourceReader;

/**
 * Manages configuration parameters for {@link it.polimi.ingsw.model.board.Board}, according to
 * the matching specification {@link BoardSpecifics}.
 * The parameters are dimension and cell pattern matrix.
 * */
public class BoardConfiguration extends Configuration<BoardSpecifics> {

    // used for singleton pattern
    private static BoardConfiguration instance;

    // deserializes and stores the game specifics (from json)
    private final BoardSpecifics specs = ResourceReader.readAndDeserialize(provideResourcePath(), BoardSpecifics.class);


    public int getDimension() {
        return specs.dimension();
    }

    public CellPattern[][] getMatrix() {
        int dimension = getDimension();
        CellPattern[][] matrix = new CellPattern[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                CellPattern matchingCellPattern;

                int value = provideSpecs().matrix()[i][j];

                switch (value) {
                    case 0 -> matchingCellPattern = null;
                    case 2 -> matchingCellPattern = CellPattern.NORMAL;
                    case 3 -> matchingCellPattern = CellPattern.THREE_DOTS;
                    case 4 -> matchingCellPattern = CellPattern.FOUR_DOTS;
                    default -> throw new IllegalStateException("Illegal specifications: [%d] is not 0, 2, 3 or 4".formatted(value));
                }

                matrix[i][j] = matchingCellPattern;
            }
        }

        return matrix;
    }


    @Override
    protected BoardSpecifics provideSpecs() {
        return specs;
    }

    @Override
    protected String provideResourcePath() {
        return "board/board.json";
    }


    public static BoardConfiguration getInstance() {
        if (instance == null) {
            instance = new BoardConfiguration();
        }

        return instance;
    }
}
