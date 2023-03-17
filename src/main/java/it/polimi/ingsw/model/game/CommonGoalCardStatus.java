package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.cards.common.CommonGoalCard;

import java.util.List;
import java.util.Stack;

/**
 * Represents the current status of a {@link it.polimi.ingsw.model.cards.common.CommonGoalCard} instance,
 * along with its associated possible {@link it.polimi.ingsw.model.game.Token}s.
 */
public class CommonGoalCardStatus {

    private final CommonGoalCard commonGoalCard;

    /**
     * Stack of tokens associated with the common goal card.
     * A stack is used to model the fact that the tokens are
     * removed top to bottom.
     */
    private final Stack<Token> tokenStack = new Stack<>();

    public CommonGoalCardStatus(CommonGoalCard commonGoalCard, List<Token> tokens) {
        this.commonGoalCard = commonGoalCard;

        tokens.forEach(tokenStack::push);
    }

    public CommonGoalCard getCommonGoalCard() {
        return commonGoalCard;
    }

    /**
     * Whether the current status still has possible tokens to be acquired
     * */
    public boolean hasTokenLeft() {
        return !tokenStack.isEmpty();
    }

    /**
     * Removes the highest token on the stack and returns it, if present
     */
    public CommonGoalCard acquireAndRemoveTopToken() {
        return commonGoalCard;
    }

}
