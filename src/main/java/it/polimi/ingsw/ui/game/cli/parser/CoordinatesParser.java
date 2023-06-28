package it.polimi.ingsw.ui.game.cli.parser;

import com.github.tomaslanger.chalk.Chalk;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.utils.model.CoordinatesHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class CoordinatesParser {
    private static final Scanner scanner = new Scanner(System.in);

    private static final int dimension = BoardConfiguration.getInstance().getDimension();
    private static final LogicConfiguration config = LogicConfiguration.getInstance();
    private static final int maxSelectionSize = LogicConfiguration.getInstance().maxSelectionSize();


    /**
     * @param game the game that is being played
     * @return the set of coordinates selected by the player, already checked for validity
     */
    public static Set<Coordinate> scan(GameModel game) {
        while (true) {
            System.out.print("""
                    Insert coordinates pairs for your selection (at least one tile, at most three)
                    Format: X1,Y1, [X2,Y2], [X3,Y3]                  (Xs are rows, Ys are columns)
                    """);

            String input = scanner.nextLine();

            String[] tokens = input.split(",");

            if (tokens.length < 2 || tokens.length > 2 * maxSelectionSize || tokens.length % 2 != 0) {
                System.out.print(Chalk.on("""
                        You need to select at least one tile and at most three;
                        also, you need both the x-coordinate and the y-coordinate.
                        """).bgYellow().toString());
                continue;
            }

            for (int i = 0; i < tokens.length; i++) {
                tokens[i] = tokens[i].trim();
            }

            Set<Coordinate> validCoordinates = new HashSet<>();

            boolean check = true;

            for (int i = 0; i < tokens.length; i += 2) {
                final String x = tokens[i];
                final String y = tokens[i + 1];

                if (isValidNumber(x) && isValidNumber(y)) {
                    Coordinate coords = new Coordinate(Integer.parseInt(x), Integer.parseInt(y));
                    validCoordinates.add(coords);
                } else {
                    System.out.print(Chalk.on(
                            "Out-of-bounds coordinates detected. Only numbers in 0-9 are valid coordinates.\n"
                    ).bgYellow().toString());
                    check = false;
                    break;
                }
            }

            if (!check) {
                continue;
            }

            if (isSelectionValid(validCoordinates, game)) {
                return validCoordinates;
            } else {
                System.out.print(Chalk.on(
                        "Invalid coordinates.\n"
                ).bgYellow().toString());
            }
        }
    }

    /**
     * @param s number we need to check, in String format
     * @return if the number is within board bounds
     */
    private static boolean isValidNumber(String s) {
        try {
            final int number = Integer.parseInt(s);
            return number >= 0 && number < dimension;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @param coordinates the coordinates the player selected
     * @param game        the game that is being played
     * @return if the selected coordinates are valid, checking if they all contain a tile, if the amount is within game
     * rules, if they all have at least one free edge and if they are in a straight line
     */
    public static boolean isSelectionValid(@NotNull Set<Coordinate> coordinates, GameModel game) {
        boolean areCoordinatesReferencingValidTiles = areAllCoordinatesPresent(coordinates, game);
        boolean isSelectionAmountValid = coordinates.size() <= config.maxSelectionSize() && coordinates.size() > 0;
        boolean isEdgeConditionSatisfied = coordinates.stream().allMatch(coordinate -> game.getBoard().countFreeEdges(coordinate) > 0);
        boolean areCoordinatesInStraightLine = CoordinatesHelper.areCoordinatesInStraightLine(coordinates.stream().toList());

        return areCoordinatesReferencingValidTiles && isSelectionAmountValid && isEdgeConditionSatisfied && areCoordinatesInStraightLine;
    }

    /**
     * @param coordinates the coordinates the player selected
     * @param game        the game that is being played
     * @return if the cells at the given coordinates all contain a tile
     */
    private static boolean areAllCoordinatesPresent(@NotNull Collection<Coordinate> coordinates, GameModel game) {
        return coordinates.stream().allMatch(it -> game.getBoard().getTileAt(it).isPresent());
    }

}
