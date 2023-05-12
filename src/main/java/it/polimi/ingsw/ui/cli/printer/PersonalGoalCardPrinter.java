package it.polimi.ingsw.ui.cli.printer;

import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCardMatrixContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.polimi.ingsw.model.cards.personal.PersonalGoalCardMatrixContainer.*;

public class PersonalGoalCardPrinter {
    private static final List<PersonalGoalCard> personalGoalCard = new ArrayList<>(personalGoalCardDomain);
    private static String print(int pos){
        return Arrays.deepToString(personalGoalCard.get(pos).getShelfPointMatrix());
    }


}
