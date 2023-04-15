package it.polimi.ingsw.model.game.goal;

import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.game.GameMode;

import java.util.Optional;
import java.util.Stack;

/**
 * Represents the current status of a {@link it.polimi.ingsw.model.cards.common.CommonGoalCard} instance,
 * along with its associated possible {@link Token}s.
 */
public class CommonGoalCardStatus {

    private final CommonGoalCard commonGoalCard;

    /**
     * Stack of tokens associated with the common goal card.
     * A stack is used to model the fact that the tokens are
     * removed top to bottom.
     */
    private final Stack<Token> tokenStack = new Stack<>();

    public CommonGoalCardStatus(CommonGoalCard commonGoalCard, GameMode gameMode) {
        this.commonGoalCard = commonGoalCard;

        switch (gameMode) {
            case GAME_MODE_2_PLAYERS -> {
                tokenStack.push(Token.COMMON_GOAL_TOKEN_4_POINTS);
                tokenStack.push(Token.COMMON_GOAL_TOKEN_8_POINTS);
            }
            case GAME_MODE_3_PLAYERS -> {
                tokenStack.push(Token.COMMON_GOAL_TOKEN_4_POINTS);
                tokenStack.push(Token.COMMON_GOAL_TOKEN_6_POINTS);
                tokenStack.push(Token.COMMON_GOAL_TOKEN_8_POINTS);
            }
            case GAME_MODE_4_PLAYERS -> {
                tokenStack.push(Token.COMMON_GOAL_TOKEN_2_POINTS);
                tokenStack.push(Token.COMMON_GOAL_TOKEN_4_POINTS);
                tokenStack.push(Token.COMMON_GOAL_TOKEN_6_POINTS);
                tokenStack.push(Token.COMMON_GOAL_TOKEN_8_POINTS);
            }
            default -> throw new IllegalStateException("Unexpected value: " + gameMode);
        }
    }

    public CommonGoalCard getCommonGoalCard() {
        return commonGoalCard;
    }

    /**
     * Whether the current status still has possible tokens to be acquired
     */
    public boolean isEmpty() {
        return !tokenStack.isEmpty();
    }

    /**
     * Removes the highest token on the stack and returns it, if present
     */
    public Optional<Token> acquireAndRemoveTopToken() {
        return Optional.ofNullable(tokenStack.pop());
    }

}
