package it.polimi.ingsw.model.game.extractors;

import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.cards.common.CommonGoalCardFunctionContainer;

import java.util.List;
import java.util.Random;

public class CommonGoalCardExtractor {

    private final List<CommonGoalCard> status = CommonGoalCardFunctionContainer.commonGoalCardDomain;

    /**
     * Extracts a random {@link CommonGoalCard} from the current {@link CommonGoalCardExtractor#status},
     * removing it in the process.
     * */
    public CommonGoalCard extract() {
        // get random index
        int index = new Random().nextInt(status.size());

        // extract the random card
        CommonGoalCard element = status.get(index);

        // remove it from the domain
        status.remove(index);

        return element;
    }
}
