package it.polimi.ingsw.model.game.extractors;

import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.cards.common.CommonGoalCardFunctionContainer;
import it.polimi.ingsw.utils.ListUtils;

import java.util.List;
import java.util.Random;

public class CommonGoalCardExtractor {

    private final List<CommonGoalCard> status = CommonGoalCardFunctionContainer.commonGoalCardDomain;

    /**
     * Extracts a random {@link CommonGoalCard} from the current {@link CommonGoalCardExtractor#status},
     * removing it in the process.
     * */
    public CommonGoalCard extract() {
        return ListUtils.extractAndRemoveRandomElement(status);
    }
}
