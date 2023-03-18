package it.polimi.ingsw.model.cards.personal;

import it.polimi.ingsw.model.board.Tile;

import java.util.Arrays;
import java.util.List;

/**
 * Personal goal card archive
 */
public class PersonalGoalCardMatrixContainer {

    private static final Tile[][] m1 = {
            {null, null, null, null, Tile.GAME},
            {null, null, null, null, null},
            {Tile.TROPHY, null, Tile.FRAME, null, null},
            {null, null, null, Tile.PLANT, null},
            {null, Tile.BOOK, Tile.CAT, null, null},
            {null, null, null, null, null}
    };

    private static final Tile[][] m2 = {
            {null, null, null, null, null},
            {null, Tile.TROPHY, null, null, null},
            {null, null, null, null, null},
            {null, Tile.FRAME, Tile.BOOK, null, null},
            {null, null, null, null, Tile.PLANT},
            {Tile.GAME, null, null, Tile.CAT, null}
    };

    private static final Tile[][] m3 = {
            {null, null, Tile.TROPHY, null, Tile.CAT},
            {null, null, null, null, null},
            {null, null, null, Tile.BOOK, null},
            {null, null, null, null, null},
            {null, Tile.GAME, null, Tile.FRAME, null},
            {Tile.PLANT, null, null, null, null}
    };

    private static final Tile[][] m4 = {
            {Tile.CAT, null, null, null, null},
            {null, null, null, Tile.FRAME, null},
            {null, Tile.PLANT, null, null, null},
            {Tile.TROPHY, null, null, null, null},
            {null, null, null, null, Tile.GAME},
            {null, null, Tile.BOOK, null, null}
    };

    private static final Tile[][] m5 = {
            {null, null, null, null, null},
            {null, Tile.PLANT, null, null, null},
            {Tile.CAT, null, Tile.GAME, null, null},
            {null, null, null, null, Tile.BOOK},
            {null, null, null, Tile.TROPHY, null},
            {null, null, null, null, Tile.FRAME}
    };

    private static final Tile[][] m6 = {
            {null, null, null, null, Tile.FRAME},
            {null, Tile.CAT, null, null, null},
            {null, null, Tile.TROPHY, null, null},
            {Tile.PLANT, null, null, null, null},
            {null, null, null, Tile.BOOK, null},
            {null, null, null, Tile.GAME, null}
    };

    private static final Tile[][] m7 = {
            {null, null, Tile.GAME, null, null},
            {null, null, null, null, null},
            {null, null, Tile.CAT, null, null},
            {null, null, null, null, Tile.BOOK},
            {null, Tile.TROPHY, null, null, Tile.PLANT},
            {Tile.FRAME, null, null, null, null}
    };

    private static final Tile[][] m8 = {
            {null, null, null, null, Tile.TROPHY},
            {null, Tile.GAME, null, null, null},
            {Tile.BOOK, null, null, null, null},
            {null, null, null, Tile.CAT, null},
            {null, Tile.FRAME, null, null, null},
            {null, null, null, Tile.PLANT, null}
    };

    private static final Tile[][] m9 = {
            {null, null, Tile.PLANT, null, null},
            {null, Tile.BOOK, null, null, null},
            {Tile.GAME, null, null, null, null},
            {null, null, Tile.FRAME, null, null},
            {null, null, null, null, Tile.CAT},
            {null, null, null, Tile.TROPHY, null}
    };

    private static final Tile[][] m10 = {
            {null, null, Tile.BOOK, null, null},
            {null, Tile.PLANT, null, null, null},
            {null, null, Tile.FRAME, null, null},
            {null, null, null, Tile.TROPHY, null},
            {null, null, null, null, Tile.GAME},
            {Tile.CAT, null, null, null, null}
    };

    private static final Tile[][] m11 = {
            {Tile.PLANT, null, Tile.FRAME, null, null},
            {null, null, null, null, Tile.CAT},
            {null, null, null, Tile.BOOK, null},
            {null, Tile.GAME, null, null, null},
            {null, null, null, null, null},
            {null, null, Tile.TROPHY, null, null}
    };

    private static final Tile[][] m12 = {
            {null, null, null, null, null},
            {Tile.FRAME, null, null, Tile.GAME, null},
            {null, null, Tile.PLANT, null, null},
            {null, Tile.CAT, null, null, Tile.TROPHY},
            {null, null, null, null, null},
            {Tile.BOOK, null, null, null, null}
    };


    private final static PersonalGoalCard p1 = new PersonalGoalCard(m1);
    private final static PersonalGoalCard p2 = new PersonalGoalCard(m2);
    private final static PersonalGoalCard p3 = new PersonalGoalCard(m3);
    private final static PersonalGoalCard p4 = new PersonalGoalCard(m4);
    private final static PersonalGoalCard p5 = new PersonalGoalCard(m5);
    private final static PersonalGoalCard p6 = new PersonalGoalCard(m6);
    private final static PersonalGoalCard p7 = new PersonalGoalCard(m7);
    private final static PersonalGoalCard p8 = new PersonalGoalCard(m8);
    private final static PersonalGoalCard p9 = new PersonalGoalCard(m9);
    private final static PersonalGoalCard p10 = new PersonalGoalCard(m10);
    private final static PersonalGoalCard p11 = new PersonalGoalCard(m11);
    private final static PersonalGoalCard p12 = new PersonalGoalCard(m12);


    public static final List<PersonalGoalCard> personalGoalCardDomain = Arrays.asList(
            p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12
    );
}
