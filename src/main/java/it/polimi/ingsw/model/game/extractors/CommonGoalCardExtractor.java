package it.polimi.ingsw.model.game.extractors;

import it.polimi.ingsw.model.cards.common.CommonGoalCard;
import it.polimi.ingsw.model.cards.common.CommonGoalCardFunctionContainer;
import it.polimi.ingsw.utils.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommonGoalCardExtractor extends ElementExtractor<CommonGoalCard> implements Serializable {

    private final List<CommonGoalCard> state = new ArrayList<>(CommonGoalCardFunctionContainer.getActiveCommonGoalCards());

    public Set<CommonGoalCard> domain() {
        return new HashSet<>(state);
    }

    /**
     * Extracts a random {@link CommonGoalCard} from the current {@link CommonGoalCardExtractor#state},
     * removing it in the process.
     */
    @Override
    public CommonGoalCard extract() {
        assert state.size() > 0;
        return CollectionUtils.extractAndRemoveRandomElement(state);
    }

    @Override
    public List<CommonGoalCard> extractAmount(int amount) {
        assert amount < state.size();
        return CollectionUtils.extractAndRemoveRandomElementAmount(state, amount);
    }
}
