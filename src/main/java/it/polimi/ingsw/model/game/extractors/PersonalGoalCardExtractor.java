package it.polimi.ingsw.model.game.extractors;

import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCardMatrixContainer;

import java.util.List;
import java.util.Random;

public class PersonalGoalCardExtractor {

    private final List<PersonalGoalCard> status = PersonalGoalCardMatrixContainer.personalGoalCardDomain;

    public PersonalGoalCard extract() {
        // get random index
        int index = new Random().nextInt(status.size());

        // extract the random card
        PersonalGoalCard element = status.get(index);

        // remove it from the domain
        status.remove(index);

        return element;
    }
}
