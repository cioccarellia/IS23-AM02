package it.polimi.ingsw.model.game.extractors;

import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCardMatrixContainer;
import it.polimi.ingsw.utils.ListUtils;

import java.util.List;

public class PersonalGoalCardExtractor {

    private final List<PersonalGoalCard> status = PersonalGoalCardMatrixContainer.personalGoalCardDomain;

    /**
     * Extracts a random {@link PersonalGoalCard} from the current {@link PersonalGoalCardExtractor#status},
     * removing it in the process.
     */
    public PersonalGoalCard extract() {
        return ListUtils.extractAndRemoveRandomElement(status);
    }
}
