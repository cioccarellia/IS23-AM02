package it.polimi.ingsw.ui.cli.printer;

import it.polimi.ingsw.model.game.goal.CommonGoalCardStatus;
import it.polimi.ingsw.model.game.goal.Token;
import it.polimi.ingsw.ui.cli.Console;

import java.util.Stack;

public class CommonGoalCardsPrinter {

    public static void printTokens(Stack<Token> tokens){
        tokens.forEach(token -> {
            Console.out(" " + token.getPoints());
        });
        Console.out("\n> ");
    }

    public static void print(CommonGoalCardStatus card) {

        switch (card.getCommonGoalCard().getId()) {
            case SIX_PAIRS -> {
                Console.out("\nSix groups each containing at least 2 tiles of the same type.\nThe tiles of one" +
                        " group can be different from those of another group.\n");
                printTokens(card.getCardTokens());
            }
            case DIAGONAL -> {
                Console.out("\nFive tiles of the same type forming a diagonal\n");
                printTokens(card.getCardTokens());
            }
            case FOUR_GROUP_FOUR -> {
                Console.out("\nFour groups each containing at least 4 tiles of the same type.\n" +
                        "The tiles of one group can be different from those of another group.\n");
                printTokens(card.getCardTokens());
            }
            case FOUR_MAX3DIFF_LINES -> {
                Console.out("\nFour lines each formed by 5 tiles of maximum three different types.\n" +
                        "One line can show the same or a different combination of another line.\n");
                printTokens(card.getCardTokens());
            }
            case FOUR_CORNERS -> {
                Console.out("\nFour tiles of the same type in the four corners of the bookshelf.\n");
                printTokens(card.getCardTokens());
            }
            case TWO_DIFF_COLUMNS -> {
                Console.out("\nTwo columns each formed by 6 different types of tiles.\n");
                printTokens(card.getCardTokens());
            }
            case TWO_SQUARES -> {
                Console.out("\nTwo groups each containing 4 tiles of the same type in a 2x2 square.\nThe tiles" +
                        " of one square can be different from those of the other square.\n");
                printTokens(card.getCardTokens());
            }
            case TWO_DIFF_LINES -> {
                Console.out("\nTwo lines each formed by 5 different types of tiles.\n One line can show the " +
                        "same or a different combination of the other line.\n");
                printTokens(card.getCardTokens());
            }
            case THREE_MAX3DIFF_COLUMNS -> {
                Console.out("\nThree columns each formed by 6 tiles of maximum three different types.\n" +
                        "One column can show the same or a different combination of another column.\n ");
                printTokens(card.getCardTokens());
            }
            case X_TILES -> {
                Console.out("\nFive tiles of the same type forming an X\n");
                printTokens(card.getCardTokens());
            }
            case EIGHT_TILES -> {
                Console.out("\nEight tiles of the same type. There’s no restriction about the position of these" +
                        " tiles.\n");
                printTokens(card.getCardTokens());
            }
            case STAIRS -> {
                Console.out("\nFive columns of increasing or decreasing height.\nStarting from the first column" +
                        " on the left or on the right, each next column must be made of exactly one more tile.\n" +
                        "Tiles can be of any type. \n");
                printTokens(card.getCardTokens());
            }
            default -> {
            }
        }
    }
}
