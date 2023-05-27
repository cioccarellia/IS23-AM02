package it.polimi.ingsw.model.game.score;

/**
 * Represents the breakdown of a player's score into different categories.
 */
public record ScoreBreakdown(
        int tokenPoints,
        int personalGoalCardPoints,
        int bookshelfGroupPoints
) {
}
