package it.polimi.ingsw.model.game.goal;

import java.io.Serializable;

/**
 * General element, assignable to a {@link it.polimi.ingsw.model.player.PlayerSession}
 * during a game which is worth a certain amount of point(s).
 */
public enum Token implements Serializable {
    FULL_SHELF_TOKEN(1),
    COMMON_GOAL_TOKEN_2_POINTS(2),
    COMMON_GOAL_TOKEN_4_POINTS(4),
    COMMON_GOAL_TOKEN_6_POINTS(6),
    COMMON_GOAL_TOKEN_8_POINTS(8);

    /**
     * The positive point value associated with a single token.
     */
    private final int points;

    Token(int tokenPoints) {
        assert tokenPoints > 0;

        this.points = tokenPoints;
    }

    public int getPoints() {
        return points;
    }
}