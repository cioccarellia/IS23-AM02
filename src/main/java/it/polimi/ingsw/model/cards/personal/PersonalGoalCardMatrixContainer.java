package it.polimi.ingsw.model.cards.personal;

import it.polimi.ingsw.model.board.Tile;

import java.util.Arrays;
import java.util.List;

import static it.polimi.ingsw.model.board.Tile.*;

/**
 * Personal goal cards archive
 */
public class PersonalGoalCardMatrixContainer {

    public static final Tile[][] m1 = {
            {null, null, null, null, GAME},
            {null, null, null, null, null},
            {TROPHY, null, FRAME, null, null},
            {null, null, null, PLANT, null},
            {null, BOOK, CAT, null, null},
            {null, null, null, null, null}
    };

    public static final Tile[][] m2 = {
            {null, null, null, null, null},
            {null, TROPHY, null, null, null},
            {null, null, null, null, null},
            {null, FRAME, BOOK, null, null},
            {null, null, null, null, PLANT},
            {GAME, null, null, CAT, null}
    };

    public static final Tile[][] m3 = {
            {null, null, TROPHY, null, CAT},
            {null, null, null, null, null},
            {null, null, null, BOOK, null},
            {null, null, null, null, null},
            {null, GAME, null, FRAME, null},
            {PLANT, null, null, null, null}
    };

    public static final Tile[][] m4 = {
            {CAT, null, null, null, null},
            {null, null, null, FRAME, null},
            {null, PLANT, null, null, null},
            {TROPHY, null, null, null, null},
            {null, null, null, null, GAME},
            {null, null, BOOK, null, null}
    };

    public static final Tile[][] m5 = {
            {null, null, null, null, null},
            {null, PLANT, null, null, null},
            {CAT, null, GAME, null, null},
            {null, null, null, null, BOOK},
            {null, null, null, TROPHY, null},
            {null, null, null, null, FRAME}
    };

    public static final Tile[][] m6 = {
            {null, null, null, null, FRAME},
            {null, CAT, null, null, null},
            {null, null, TROPHY, null, null},
            {PLANT, null, null, null, null},
            {null, null, null, BOOK, null},
            {null, null, null, GAME, null}
    };

    public static final Tile[][] m7 = {
            {null, null, GAME, null, null},
            {null, null, null, null, null},
            {null, null, CAT, null, null},
            {null, null, null, null, BOOK},
            {null, TROPHY, null, null, PLANT},
            {FRAME, null, null, null, null}
    };

    public static final Tile[][] m8 = {
            {null, null, null, null, TROPHY},
            {null, GAME, null, null, null},
            {BOOK, null, null, null, null},
            {null, null, null, CAT, null},
            {null, FRAME, null, null, null},
            {null, null, null, PLANT, null}
    };

    public static final Tile[][] m9 = {
            {null, null, PLANT, null, null},
            {null, BOOK, null, null, null},
            {GAME, null, null, null, null},
            {null, null, FRAME, null, null},
            {null, null, null, null, CAT},
            {null, null, null, TROPHY, null}
    };

    public static final Tile[][] m10 = {
            {null, null, BOOK, null, null},
            {null, PLANT, null, null, null},
            {null, null, FRAME, null, null},
            {null, null, null, TROPHY, null},
            {null, null, null, null, GAME},
            {CAT, null, null, null, null}
    };

    public static final Tile[][] m11 = {
            {PLANT, null, FRAME, null, null},
            {null, null, null, null, CAT},
            {null, null, null, BOOK, null},
            {null, GAME, null, null, null},
            {null, null, null, null, null},
            {null, null, TROPHY, null, null}
    };

    public static final Tile[][] m12 = {
            {null, null, null, null, null},
            {FRAME, null, null, GAME, null},
            {null, null, PLANT, null, null},
            {null, CAT, null, null, TROPHY},
            {null, null, null, null, null},
            {BOOK, null, null, null, null}
    };

    public final static PersonalGoalCard p1 = new PersonalGoalCard(m1);
    public final static PersonalGoalCard p2 = new PersonalGoalCard(m2);
    public final static PersonalGoalCard p3 = new PersonalGoalCard(m3);
    public final static PersonalGoalCard p4 = new PersonalGoalCard(m4);
    public final static PersonalGoalCard p5 = new PersonalGoalCard(m5);
    public final static PersonalGoalCard p6 = new PersonalGoalCard(m6);
    public final static PersonalGoalCard p7 = new PersonalGoalCard(m7);
    public final static PersonalGoalCard p8 = new PersonalGoalCard(m8);
    public final static PersonalGoalCard p9 = new PersonalGoalCard(m9);
    public final static PersonalGoalCard p10 = new PersonalGoalCard(m10);
    public final static PersonalGoalCard p11 = new PersonalGoalCard(m11);
    public final static PersonalGoalCard p12 = new PersonalGoalCard(m12);


    public static final List<PersonalGoalCard> personalGoalCardDomain = Arrays.asList(
            p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12
    );

}

