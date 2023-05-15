package it.polimi.ingsw.ui.cli.printer;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.cards.personal.PersonalGoalCard;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.ui.cli.Console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.polimi.ingsw.model.cards.personal.PersonalGoalCardMatrixContainer.*;
import static it.polimi.ingsw.model.player.PlayerSession.*;

public class PersonalGoalCardPrinter {
    private static final int rows = BookshelfConfiguration.getInstance().rows();
    private static final int cols = BookshelfConfiguration.getInstance().cols();
    public static void print(PersonalGoalCard personalGoalCard){
        int verticalGuideNumber = 0;
        Tile tile=null;

        Console.out("X   0  1  2  3  4\n");

        for(int i=0; i<rows;i++){

            Console.out(verticalGuideNumber);
            Console.out("  ");

            for(int j=0;j<cols;j++){

                tile=personalGoalCard.getShelfPointMatrix()[i][j];
                String tileText = TilePrinter.print(tile);
                Console.out(" " + tileText + " ");

            }

            Console.out("\n");
            verticalGuideNumber++;

        }



    }


}
