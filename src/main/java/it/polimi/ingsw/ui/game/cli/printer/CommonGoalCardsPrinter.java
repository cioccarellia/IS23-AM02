package it.polimi.ingsw.ui.game.cli.printer;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.model.game.goal.CommonGoalCardStatus;
import it.polimi.ingsw.model.game.goal.Token;
import it.polimi.ingsw.ui.game.cli.Console;
import javafx.util.Pair;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommonGoalCardsPrinter {

    private static final int rows = BookshelfConfiguration.getInstance().rows();
    private static final int cols = BookshelfConfiguration.getInstance().cols();

    private static final List<Pair<Tile[][], CommonGoalCardStatus>> exampleMatrices = new ArrayList<>();

    /**
     * @param cards status of common goal cards, within the game that is being played
     */
    public static void print(List<CommonGoalCardStatus> cards) {
        Console.outln();
        int dim = cards.size();
        for (CommonGoalCardStatus card : cards) {
            setExampleMatrices(card);
        }

        Console.out("Common goal cards (the tiles' disposition is just a general description of the card):");
        Console.outln();

        for (int i = 0; i < dim; i++) {
            var x = exampleMatrices
                    .get(i)
                    .getValue()
                    .getCommonGoalCard()
                    .getId()
                    .toString()
                    .toLowerCase();

            Console.out(StringUtils.rightPad(x, 23, " "));
        }

        Console.outln();

        for (int i = 0; i < dim; i++) {
            Console.out("    0  1  2  3  4      ");
        }

        Console.outln();

        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < dim; k++) {
                Console.out(i);
                Console.out("  ");

                for (int j = 0; j < cols; j++) {
                    var tile = exampleMatrices.get(k).getKey()[i][j];
                    if (tile != null) {
                        Console.out(" " + TilePrinter.print(tile) + " ");
                    } else
                        Console.out("   ");
                }

                Console.out("     ");
            }
            Console.outln();
        }

        Console.outln();

        for (int i = 0; i < dim; i++) {
            Console.out("  Tokens:");
            var x = exampleMatrices
                    .get(i)
                    .getValue()
                    .getCardTokens()
                    .stream()
                    .map(Token::getPoints)
                    .toList()
                    .toString();

            Console.out(StringUtils.rightPad(x, 14, " "));
        }

        Console.outln();

    }

    public static void setExampleMatrices(CommonGoalCardStatus card) {

        switch (card.getCommonGoalCard().getId()) {
            case SIX_PAIRS -> exampleMatrices.add(new Pair<>(ExampleCommonGoalCards.SIX_PAIRS_EXAMPLE, card));
            case DIAGONAL -> exampleMatrices.add(new Pair<>(ExampleCommonGoalCards.DIAGONAL_EXAMPLE, card));
            case FOUR_GROUP_FOUR ->
                    exampleMatrices.add(new Pair<>(ExampleCommonGoalCards.FOUR_GROUP_FOUR_EXAMPLE, card));
            case FOUR_MAX3DIFF_LINES ->
                    exampleMatrices.add(new Pair<>(ExampleCommonGoalCards.FOUR_MAX3DIFF_LINES_EXAMPLE, card));
            case FOUR_CORNERS -> exampleMatrices.add(new Pair<>(ExampleCommonGoalCards.FOUR_CORNERS_EXAMPLE, card));
            case TWO_DIFF_COLUMNS ->
                    exampleMatrices.add(new Pair<>(ExampleCommonGoalCards.TWO_DIFF_COLUMNS_EXAMPLE, card));
            case TWO_SQUARES -> exampleMatrices.add(new Pair<>(ExampleCommonGoalCards.TWO_SQUARES_EXAMPLE, card));
            case TWO_DIFF_LINES -> exampleMatrices.add(new Pair<>(ExampleCommonGoalCards.TWO_DIFF_LINES_EXAMPLE, card));
            case THREE_MAX3DIFF_COLUMNS ->
                    exampleMatrices.add(new Pair<>(ExampleCommonGoalCards.THREE_MAX3DIFF_COLUMNS_EXAMPLE, card));
            case X_TILES -> exampleMatrices.add(new Pair<>(ExampleCommonGoalCards.X_TILES_EXAMPLE, card));
            case EIGHT_TILES -> exampleMatrices.add(new Pair<>(ExampleCommonGoalCards.EIGHT_TILES_EXAMPLE, card));
            case STAIRS -> exampleMatrices.add(new Pair<>(ExampleCommonGoalCards.STAIRS_EXAMPLE, card));
            default -> {
            }
        }
    }
}

