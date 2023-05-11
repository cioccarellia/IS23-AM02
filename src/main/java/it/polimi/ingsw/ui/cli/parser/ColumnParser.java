package it.polimi.ingsw.ui.cli.parser;

import it.polimi.ingsw.ui.cli.Console;

public class ColumnParser {

    public static int scan() {

        int column = Integer.parseInt(Console.in());

            while (column < 0 && column > 4) {
                column = Integer.parseInt(Console.in());
            }

        return column;
    }
}
