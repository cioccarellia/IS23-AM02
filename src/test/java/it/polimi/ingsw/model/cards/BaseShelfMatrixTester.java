package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import org.jetbrains.annotations.NotNull;

import static it.polimi.ingsw.costants.BookShelfConstants.COLUMNS;
import static it.polimi.ingsw.costants.BookShelfConstants.ROWS;

/**
 * Base class for {@link CommonGoalCard} tests
 * */
public class BaseShelfMatrixTester {
    /**
     * Matrix filled with null values
     * */
    public Tile[][] nullMatrix = new Tile[ROWS][COLUMNS];

    /**
     * Returns a matrix filled with the passed {@link Tile} value
     * */
    public static Tile[][] fullOf(@NotNull Tile content) {
        Tile[][] fullMatrix = new Tile[ROWS][COLUMNS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                fullMatrix[i][j] = content;
            }
        }

        return fullMatrix;
    }
}
