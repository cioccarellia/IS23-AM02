package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.board.Tile;

import java.util.function.Function;

/**
 * Card given by the game, each card rappresents a goal that all the players need to reach
 */
public class CommonGoalCard {
    private Function<Tile[][], Boolean> evaluationFunction;

    /**
     * Applies the internal evaluation function to the given tile matrix
     * and returns whether
     *
     * @param shelfMatrix
     */
    public boolean matches(Tile[][] shelfMatrix) {
        return evaluationFunction.apply(shelfMatrix);
    }
}