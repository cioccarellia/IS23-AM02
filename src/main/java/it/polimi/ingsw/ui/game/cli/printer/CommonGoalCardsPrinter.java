package it.polimi.ingsw.ui.game.cli.printer;

import it.polimi.ingsw.model.config.bookshelf.BookshelfConfiguration;
import it.polimi.ingsw.model.game.goal.CommonGoalCardStatus;
import it.polimi.ingsw.model.game.goal.Token;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommonGoalCardsPrinter {

    private static final int rows = BookshelfConfiguration.getInstance().rows();
    private static final int cols = BookshelfConfiguration.getInstance().cols();

    private static final List<CommonGoalCardMatrixEntry> exampleMatrices = new ArrayList<>();

    /**
     * @param cards status of common goal cards, within the game that is being played
     */
    public static void print(List<CommonGoalCardStatus> cards) {
        System.out.println();
        int dim = cards.size();
        for (CommonGoalCardStatus card : cards) {
            setExampleMatrices(card);
        }

        System.out.print("Common goal cards (the tiles' disposition is just a general description of the card):");
        System.out.flush();
        System.out.println();

        for (int i = 0; i < dim; i++) {
            var x = exampleMatrices
                    .get(i)
                    .status()
                    .getCommonGoalCard()
                    .getId()
                    .toString()
                    .toLowerCase();

            System.out.print(StringUtils.rightPad(x, 23, " "));
            System.out.flush();
        }

        System.out.println();

        for (int i = 0; i < dim; i++) {
            System.out.print("    0  1  2  3  4      ");
            System.out.flush();
        }

        System.out.println();

        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < dim; k++) {
                System.out.print(i + "  ");
                System.out.flush();

                for (int j = 0; j < cols; j++) {
                    var tile = exampleMatrices.get(k).bookshelf()[i][j];
                    if (tile != null) {
                        System.out.print(" " + TilePrinter.print(tile) + " ");
                        System.out.flush();
                    } else {
                        System.out.print("   ");
                        System.out.flush();
                    }
                }
                System.out.print("     ");
                System.out.flush();
            }
            System.out.println();
        }

        System.out.println();

        for (int i = 0; i < dim; i++) {
            System.out.print("  Tokens:");
            System.out.flush();
            var x = exampleMatrices
                    .get(i)
                    .status()
                    .getCardTokens()
                    .stream()
                    .map(Token::getPoints)
                    .toList()
                    .toString();

            System.out.print(StringUtils.rightPad(x, 14, " "));
            System.out.flush();
        }

        System.out.println();

    }

    public static void setExampleMatrices(CommonGoalCardStatus card) {

        switch (card.getCommonGoalCard().getId()) {
            case SIX_PAIRS ->
                    exampleMatrices.add(new CommonGoalCardMatrixEntry(ExampleCommonGoalCards.SIX_PAIRS_EXAMPLE, card));
            case DIAGONAL ->
                    exampleMatrices.add(new CommonGoalCardMatrixEntry(ExampleCommonGoalCards.DIAGONAL_EXAMPLE, card));
            case FOUR_GROUP_FOUR ->
                    exampleMatrices.add(new CommonGoalCardMatrixEntry(ExampleCommonGoalCards.FOUR_GROUP_FOUR_EXAMPLE, card));
            case FOUR_MAX3DIFF_LINES ->
                    exampleMatrices.add(new CommonGoalCardMatrixEntry(ExampleCommonGoalCards.FOUR_MAX3DIFF_LINES_EXAMPLE, card));
            case FOUR_CORNERS ->
                    exampleMatrices.add(new CommonGoalCardMatrixEntry(ExampleCommonGoalCards.FOUR_CORNERS_EXAMPLE, card));
            case TWO_DIFF_COLUMNS ->
                    exampleMatrices.add(new CommonGoalCardMatrixEntry(ExampleCommonGoalCards.TWO_DIFF_COLUMNS_EXAMPLE, card));
            case TWO_SQUARES ->
                    exampleMatrices.add(new CommonGoalCardMatrixEntry(ExampleCommonGoalCards.TWO_SQUARES_EXAMPLE, card));
            case TWO_DIFF_LINES ->
                    exampleMatrices.add(new CommonGoalCardMatrixEntry(ExampleCommonGoalCards.TWO_DIFF_LINES_EXAMPLE, card));
            case THREE_MAX3DIFF_COLUMNS ->
                    exampleMatrices.add(new CommonGoalCardMatrixEntry(ExampleCommonGoalCards.THREE_MAX3DIFF_COLUMNS_EXAMPLE, card));
            case X_TILES ->
                    exampleMatrices.add(new CommonGoalCardMatrixEntry(ExampleCommonGoalCards.X_TILES_EXAMPLE, card));
            case EIGHT_TILES ->
                    exampleMatrices.add(new CommonGoalCardMatrixEntry(ExampleCommonGoalCards.EIGHT_TILES_EXAMPLE, card));
            case STAIRS ->
                    exampleMatrices.add(new CommonGoalCardMatrixEntry(ExampleCommonGoalCards.STAIRS_EXAMPLE, card));
            default -> {
            }
        }
    }
}

