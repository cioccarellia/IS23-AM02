package it.polimi.ingsw.model.game;

/**
 * Beacon of points
 * */
public enum Token {
    FULL_SHELF_TOKEN(1),
    COMMON_GOAL_TOKEN_2_POINTS(2),
    COMMON_GOAL_TOKEN_4_POINTS(4),
    COMMON_GOAL_TOKEN_6_POINTS(6),
    COMMON_GOAL_TOKEN_8_POINTS(8);

    private final int points;

    Token(int tokenPoints) {
        assert tokenPoints > 0;

        this.points = tokenPoints;
    }

    public int getPoints() {
        return points;
    }
}