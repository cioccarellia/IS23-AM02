package it.polimi.ingsw.model.game.goal;

import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.cards.common.CommonGoalCardFunctionContainer;
import it.polimi.ingsw.model.cards.common.CommonGoalCardIdentifier;
import it.polimi.ingsw.model.game.GameMode;

import java.io.Serializable;
import java.util.Optional;
import java.util.Stack;

import static it.polimi.ingsw.model.game.goal.Token.*;

/**
 * Represents the current status of a {@link it.polimi.ingsw.model.cards.common.CommonGoalCard} instance,
 * along with its associated possible {@link Token}s.
 */
public class CommonGoalCardStatus implements Serializable {

    private final CommonGoalCardIdentifier commonGoalCardId;

    /**
     * Stack of tokens associated with the common goal card.
     * A stack is used to model the fact that the tokens are
     * removed top to bottom.
     */
    private final Stack<Token> tokenStack = new Stack<>();

    public CommonGoalCardStatus(CommonGoalCard commonGoalCard, GameMode gameMode) {
        this.commonGoalCardId = commonGoalCard.getId();

        switch (gameMode) {
            case GAME_MODE_2_PLAYERS -> {
                tokenStack.push(COMMON_GOAL_TOKEN_4_POINTS);
                tokenStack.push(COMMON_GOAL_TOKEN_8_POINTS);
            }
            case GAME_MODE_3_PLAYERS -> {
                tokenStack.push(COMMON_GOAL_TOKEN_4_POINTS);
                tokenStack.push(COMMON_GOAL_TOKEN_6_POINTS);
                tokenStack.push(COMMON_GOAL_TOKEN_8_POINTS);
            }
            case GAME_MODE_4_PLAYERS -> {
                tokenStack.push(COMMON_GOAL_TOKEN_2_POINTS);
                tokenStack.push(COMMON_GOAL_TOKEN_4_POINTS);
                tokenStack.push(COMMON_GOAL_TOKEN_6_POINTS);
                tokenStack.push(COMMON_GOAL_TOKEN_8_POINTS);
            }
            default -> throw new IllegalStateException("Unexpected value: " + gameMode);
        }
    }

    public CommonGoalCard getCommonGoalCard() {
        return CommonGoalCardFunctionContainer.commonGoalCardMap().get(commonGoalCardId);
    }

    public Stack<Token> getCardTokens() {
        return tokenStack;
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

    @Override
    public String toString() {
        return "CommonGoalCardStatus{" +
                "commonGoalCardId=" + commonGoalCardId +
                ", tokenStack=" + tokenStack +
                '}';
    }
}
