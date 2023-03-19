package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.model.board.Tile;

import java.util.function.Function;

/**
 * Represents a shared objective among players
 */
public class CommonGoalCard {
    private final Function<Tile[][], Boolean> f;
    private final CommonGoalCardIdentifier id;

    /**
     * @param identifier         the {@code CommonGoalCard}'s identifier
     * @param evaluationFunction the function for matching the card
     */
    public CommonGoalCard(CommonGoalCardIdentifier identifier, Function<Tile[][], Boolean> evaluationFunction) {
        id = identifier;
        f = evaluationFunction;
    }


    /**
     * Returns the {@code CommonGoalCard}'s identifier
     */
    public CommonGoalCardIdentifier getId() {
        return id;
    }

    /**
     * Applies the internal evaluation function to the given tile matrix,
     * and returns whether the criteria is satisfied or not.
     *
     * @param shelfMatrix the game matrix to compute the logic on.
     */
    public boolean matches(Tile[][] shelfMatrix) {
        return f.apply(shelfMatrix);
    }

}