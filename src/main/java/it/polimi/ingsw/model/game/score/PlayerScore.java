package it.polimi.ingsw.model.game.score;

import it.polimi.ingsw.model.game.goal.Token;

import java.util.List;

/**
 * Represents the score of a player in the game
 *
 * @param username  the username of the player
 * @param tokens    the list of current tokens of the player
 * @param breakdown the score breakdown of the player
 */
public record PlayerScore(
        String username,
        List<Token> tokens,
        ScoreBreakdown breakdown
) {

    /**
     * Calculates the total score of the player by summing the points from different sources.
     *
     * @return the total score of the player
     */
    public int total() {
        return breakdown.bookshelfGroupPoints() + breakdown.tokenPoints() + breakdown.personalGoalCardPoints();
    }

    /**
     * Retrieves the points earned from the bookshelf groups.
     *
     * @return the points earned from the bookshelf groups
     */
    public int getBookshelfPoints() {
        return breakdown.bookshelfGroupPoints();
    }

    /**
     * Retrieves the points earned from the tokens.
     *
     * @return the points earned from the tokens
     */
    public int getTokenPoints() {
        return breakdown.tokenPoints();
    }

    /**
     * Retrieves the points earned from the personal goal cards.
     *
     * @return the points earned from the personal goal cards
     */
    public int getPersonalGoalCardsPoints() {
        return breakdown.personalGoalCardPoints();
    }
}

