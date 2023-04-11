package it.polimi.ingsw.model.game.extractors;

import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCardMatrixContainer;
import it.polimi.ingsw.utils.CollectionUtils;
import org.apache.commons.lang.NotImplementedException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonalGoalCardExtractor extends ElementExtractor<PersonalGoalCard> {

    private final List<PersonalGoalCard> state = PersonalGoalCardMatrixContainer.personalGoalCardDomain;

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
        throw new NotImplementedException("Can not extract a list of personal goal cards");
    }
}
