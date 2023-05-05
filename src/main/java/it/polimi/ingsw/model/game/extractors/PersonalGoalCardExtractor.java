package it.polimi.ingsw.model.game.extractors;

import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static it.polimi.ingsw.model.cards.personal.PersonalGoalCardMatrixContainer.personalGoalCardDomain;

public class PersonalGoalCardExtractor extends ElementExtractor<PersonalGoalCard> {

    private final List<PersonalGoalCard> state = new ArrayList<>(personalGoalCardDomain);

    public Set<PersonalGoalCard> domain() {
        return new HashSet<>(state);
    }

    /**
     * Extracts a random {@link PersonalGoalCard} from the current {@link PersonalGoalCardExtractor#state},
     * removing it in the process.
     */
    @Override
    public PersonalGoalCard extract() {
        return CollectionUtils.extractAndRemoveRandomElement(state);
    }

    @Override
    public List<PersonalGoalCard> extractAmount(int amount) {
        throw new IllegalCallerException("Can not extract a list of personal goal cards");
    }
}
