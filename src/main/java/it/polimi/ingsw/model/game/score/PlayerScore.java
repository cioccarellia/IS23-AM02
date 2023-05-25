package it.polimi.ingsw.model.game.score;

import it.polimi.ingsw.model.game.goal.Token;

import java.util.List;

public record PlayerScore(
        String username,
        List<Token> tokens,
        ScoreBreakdown breakdown
) {
    public int total() {
        return breakdown.bookshelfGroupPoints() + breakdown.tokenPoints() + breakdown.personalGoalCardPoints();
    }
}

