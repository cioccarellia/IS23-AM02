package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.model.board.Tile;

import java.util.function.Function;

/**
 * Card given by the game, each card represents a goal that all the players need to reach
 */
public class CommonGoalCard {
    private final Function<Tile[][], Boolean> evaluationFunction;

    public CommonGoalCard(CommonGoalCardIdentifier id, Function<Tile[][], Boolean> f) {
        evaluationFunction = f;
    }

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