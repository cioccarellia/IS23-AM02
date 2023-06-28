package it.polimi.ingsw.ui.game.cli.printer;

import it.polimi.ingsw.model.game.goal.CommonGoalCardStatus;
import it.polimi.ingsw.model.game.goal.Token;

import java.util.Stack;

public class CommonGoalCardsDescriptionPrinter {

    /**
     * Prints the (remaining) tokens from a given common goal card
     *
     * @param tokens tokens from the common goal card
     */
    public static void printTokens(Stack<Token> tokens) {
        System.out.print("Remaining tokens:");
        System.out.flush();
        tokens.forEach(token -> {
            System.out.print(" " + token.getPoints());
            System.out.flush();
        });
        System.out.println();
    }

    /**
     * @param card status of a common goal card, within the game that is being played
     */
    public static void print(CommonGoalCardStatus card) {
        switch (card.getCommonGoalCard().getId()) {
            case SIX_PAIRS -> {
                System.out.print("""
                        Six groups each containing at least 2 tiles of the same type.
                        The tiles of one group can be different from those of another group.
                        """);
                System.out.flush();
                printTokens(card.getCardTokens());
            }
            case DIAGONAL -> {
                System.out.print("""
                        Five tiles of the same type forming a diagonal
                        """);
                System.out.flush();
                printTokens(card.getCardTokens());
            }
            case FOUR_GROUP_FOUR -> {
                System.out.print("""
                        Four groups each containing at least 4 tiles of the same type.
                        The tiles of one group can be different from those of another group.
                        """);
                System.out.flush();
                printTokens(card.getCardTokens());
            }
            case FOUR_MAX3DIFF_LINES -> {
                System.out.print("""
                        Four lines each formed by 5 tiles of maximum three different types.
                        One line can show the same or a different combination of another line.
                        """);
                System.out.flush();
                printTokens(card.getCardTokens());
            }
            case FOUR_CORNERS -> {
                System.out.print("""
                        Four tiles of the same type in the four corners of the bookshelf.
                        """);
                System.out.flush();
                printTokens(card.getCardTokens());
            }
            case TWO_DIFF_COLUMNS -> {
                System.out.print("""
                        Two columns each formed by 6 different types of tiles.
                        """);
                System.out.flush();
                printTokens(card.getCardTokens());
            }
            case TWO_SQUARES -> {
                System.out.print("""
                        Two groups each containing 4 tiles of the same type in a 2x2 square.
                        The tiles of one square can be different from those of the other square.
                        """);
                System.out.flush();
                printTokens(card.getCardTokens());
            }
            case TWO_DIFF_LINES -> {
                System.out.print("""
                        Two lines each formed by 5 different types of tiles.
                         One line can show the same or a different combination of the other line.
                        """);
                System.out.flush();
                printTokens(card.getCardTokens());
            }
            case THREE_MAX3DIFF_COLUMNS -> {
                System.out.print("""
                        Three columns each formed by 6 tiles of maximum three different types.
                        One column can show the same or a different combination of another column.
                        """);
                System.out.flush();
                printTokens(card.getCardTokens());
            }
            case X_TILES -> {
                System.out.print("""
                        Five tiles of the same type forming an X.
                        """);
                System.out.flush();
                printTokens(card.getCardTokens());
            }
            case EIGHT_TILES -> {
                System.out.print("""
                        Eight tiles of the same type. There’s no restriction about the position of these tiles.
                        """);
                System.out.flush();
                printTokens(card.getCardTokens());
            }
            case STAIRS -> {
                System.out.print("""
                        Five columns of increasing or decreasing height.
                        Starting from the first column on the left or on the right, each next column must be made of exactly one more tile.
                        Tiles can be of any type.
                        """);
                System.out.flush();
                printTokens(card.getCardTokens());
            }
            default -> {
            }
        }
    }
}
