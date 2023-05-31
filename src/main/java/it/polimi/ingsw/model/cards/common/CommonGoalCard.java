package it.polimi.ingsw.model.cards.common;

import it.polimi.ingsw.model.board.Tile;

import java.io.Serializable;
import java.util.function.Predicate;

/**
 * Represents a shared objective among players
 */
public class CommonGoalCard implements Serializable {
    private final CommonGoalCardIdentifier id;
    private final Predicate<Tile[][]> f;

    /**
     * @param identifier         the {@code CommonGoalCard}'s identifier
     * @param evaluationFunction the function used for evaluating and testing the criteria on the given matrix
     */
    public CommonGoalCard(CommonGoalCardIdentifier identifier, Predicate<Tile[][]> evaluationFunction) {
        id = identifier;
        f = evaluationFunction;
    }


    /**
     * @return the {@code CommonGoalCard}'s identifier
     */
    public CommonGoalCardIdentifier getId() {
        return id;
    }

    /**
     * Applies the internal evaluation function to the given tile matrix,
     * and returns whether the criteria are satisfied or not.
     *
     * @param shelfMatrix the game matrix to compute the logic on.
     */
    public boolean matches(Tile[][] shelfMatrix) {
        return f.test(shelfMatrix);
    }

    @Override
    public String toString() {
        return "CommonGoalCard{" +
                "id=" + id +
                ", f=" + f +
                '}';
    }
}