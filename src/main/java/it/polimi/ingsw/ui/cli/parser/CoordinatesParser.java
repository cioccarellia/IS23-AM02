package it.polimi.ingsw.ui.cli.parser;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.ui.cli.Console;

public class CoordinatesParser {

    public static Coordinate scan() {

        while (true) {
            String input = Console.in();

            if (input.length() != 3) {
                continue;
            }

            String[] tokens = input.split(",");

            if (tokens.length != 2) {
                continue;
            }

            final String x = tokens[0];
            final String y = tokens[1];

            if (isValidNumber(x) && isValidNumber(y)) {
                return new Coordinate(Integer.parseInt(x), Integer.parseInt(y));
            }
        }
    }

    private static boolean isValidNumber(String s) {
        try {
            final int number = Integer.parseInt(s);
            return number >= 0 && number < 9;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
