package it.polimi.ingsw.model.game.extractors;

import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.cards.common.CommonGoalCardFunctionContainer;
import it.polimi.ingsw.utils.ListUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommonGoalCardExtractor extends ElementExtractor<CommonGoalCard> {

    private final List<CommonGoalCard> state = CommonGoalCardFunctionContainer.commonGoalCardDomain;


    public Set<CommonGoalCard> domain() {
        return new HashSet<>(state);
    }

    /**
     * Extracts a random {@link CommonGoalCard} from the current {@link CommonGoalCardExtractor#state},
     * removing it in the process.
     */
    @Override
    public CommonGoalCard extract() {
        return ListUtils.extractAndRemoveRandomElement(state);
    }
}
