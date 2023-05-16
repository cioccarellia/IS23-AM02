package it.polimi.ingsw.ui.cli.parser;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.config.board.BoardConfiguration;
import it.polimi.ingsw.model.config.logic.LogicConfiguration;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.ui.cli.Console;
import it.polimi.ingsw.utils.model.CoordinatesHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CoordinatesParser {

    private static final int dimension = BoardConfiguration.getInstance().getDimension();
    private static final LogicConfiguration config = LogicConfiguration.getInstance();


    public static Set<Coordinate> scan(Game game) {

        while (true) {
            Console.out("""
                    Give me the coordinates of the tiles you want (at least one, at most three),
                    in the format: x1, y1, x2, y2, ... (x are rows, y are columns)
                    """);
            Console.flush();

            String input = Console.in();

            String[] tokens = input.split(",");

            if (tokens.length < 2 || tokens.length > 6 || tokens.length % 2 != 0) {
                Console.out("""
                        You need to select at least one tile and at most three;
                        also, you need both the x-coordinate and the y-coordinate1n
                        """);
                continue;
            }

            for (int i = 0; i < tokens.length; i++) {
                tokens[i] = tokens[i].trim();
            }

            Set<Coordinate> validCoordinates = new HashSet<>();
            for (int i = 0; i < tokens.length; i += 2) {
                final String x = tokens[i];
                final String y = tokens[i + 1];

                if (isValidNumber(x) && isValidNumber(y)) {
                    Coordinate coords = new Coordinate(Integer.parseInt(x), Integer.parseInt(y));
                    validCoordinates.add(coords);
                } else {
                    Console.out("""
                            Some of these coordinates are out of bounds, you need to select numbers
                            from 0 to 9.
                            """);
                    break;
                }
            }
            if (isSelectionValid(validCoordinates, game)) {
                return validCoordinates;
            } else {
                Console.out("""
                            The coordinates are not valid.
                            """);
                continue;
            }
        }
    }

    private static boolean isValidNumber(String s) {
        try {
            final int number = Integer.parseInt(s);
            return number >= 0 && number < dimension;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isSelectionValid(@NotNull Set<Coordinate> coordinates, Game game) {
        boolean areCoordinatesReferencingValidTiles = areAllCoordinatesPresent(coordinates, game);
        boolean isSelectionAmountValid = coordinates.size() <= config.maxSelectionSize();
        boolean isEdgeConditionSatisfied = coordinates.stream().allMatch(coordinate -> game.getBoard().countFreeEdges(coordinate) > 0);
        boolean areCoordinatesInStraightLine = CoordinatesHelper.areCoordinatesInStraightLine(coordinates.stream().toList());

        return areCoordinatesReferencingValidTiles && isSelectionAmountValid && isEdgeConditionSatisfied && areCoordinatesInStraightLine;
    }

    private static boolean areAllCoordinatesPresent(@NotNull Collection<Coordinate> coordinates, Game game) {
        return coordinates.stream().allMatch(it -> game.getBoard().getTileAt(it).isPresent());
    }

}
