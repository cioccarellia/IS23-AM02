package it.polimi.ingsw.ui.game.cli.printer;

import it.polimi.ingsw.model.game.goal.CommonGoalCardStatus;
import it.polimi.ingsw.model.game.goal.Token;
import it.polimi.ingsw.ui.game.cli.Console;

import java.util.Stack;

public class CommonGoalCardsDescriptionPrinter {

    /**
     * Prints the (remaining) tokens from a given common goal card
     *
     * @param tokens tokens from the common goal card
     */
    public static void printTokens(Stack<Token> tokens) {
        Console.out("Remaining tokens:");
        tokens.forEach(token -> Console.out(" " + token.getPoints()));
        Console.printnl();
    }

    /**
     * @param card status of a common goal card, within the game that is being played
     */
    public static void print(CommonGoalCardStatus card) {
        switch (card.getCommonGoalCard().getId()) {
            case SIX_PAIRS -> {
                Console.out("""
                        Six groups each containing at least 2 tiles of the same type.
                        The tiles of one group can be different from those of another group.
                        """);
                printTokens(card.getCardTokens());
            }
            case DIAGONAL -> {
                Console.out("""
                        Five tiles of the same type forming a diagonal
                        """);
                printTokens(card.getCardTokens());
            }
            case FOUR_GROUP_FOUR -> {
                Console.out("""
                        Four groups each containing at least 4 tiles of the same type.
                        The tiles of one group can be different from those of another group.
                        """);
                printTokens(card.getCardTokens());
            }
            case FOUR_MAX3DIFF_LINES -> {
                Console.out("""
                        Four lines each formed by 5 tiles of maximum three different types.
                        One line can show the same or a different combination of another line.
                        """);
                printTokens(card.getCardTokens());
            }
            case FOUR_CORNERS -> {
                Console.out("""
                        Four tiles of the same type in the four corners of the bookshelf.
                        """);
                printTokens(card.getCardTokens());
            }
            case TWO_DIFF_COLUMNS -> {
                Console.out("""
                        Two columns each formed by 6 different types of tiles.
                        """);
                printTokens(card.getCardTokens());
            }
            case TWO_SQUARES -> {
                Console.out("""
                        Two groups each containing 4 tiles of the same type in a 2x2 square.
                        The tiles of one square can be different from those of the other square.
                        """);
                printTokens(card.getCardTokens());
            }
            case TWO_DIFF_LINES -> {
                Console.out("""
                        Two lines each formed by 5 different types of tiles.
                         One line can show the same or a different combination of the other line.
                        """);
                printTokens(card.getCardTokens());
            }
            case THREE_MAX3DIFF_COLUMNS -> {
                Console.out("""
                        Three columns each formed by 6 tiles of maximum three different types.
                        One column can show the same or a different combination of another column.
                        """);
                printTokens(card.getCardTokens());
            }
            case X_TILES -> {
                Console.out("""
                        Five tiles of the same type forming an X.
                        """);
                printTokens(card.getCardTokens());
            }
            case EIGHT_TILES -> {
                Console.out("""
                        Eight tiles of the same type. There’s no restriction about the position of these tiles.
                        """);
                printTokens(card.getCardTokens());
            }
            case STAIRS -> {
                Console.out("""
                        Five columns of increasing or decreasing height.
                        Starting from the first column on the left or on the right, each next column must be made of exactly one more tile.
                        Tiles can be of any type.
                        """);
                printTokens(card.getCardTokens());
            }
            default -> {
            }
        }
    }
}
