package it.polimi.ingsw.ui.cli.printer;

import com.github.tomaslanger.chalk.Chalk;
import it.polimi.ingsw.model.board.Tile;

public class TilePrinter {

    /**
     * Prints the tile with a certain color and background.
     *
     * @param tile that needs printing
     */
    public static String print(Tile tile) {
        return switch (tile) {
            case BOOK -> Chalk.on("B").bgWhite().black().toString();
            case CAT -> Chalk.on("C").bgGreen().black().toString();
            case GAME -> Chalk.on("G").bgYellow().black().toString();
            case TROPHY -> Chalk.on("T").bgCyan().black().toString();
            case PLANT -> Chalk.on("P").bgRed().black().toString();
            case FRAME -> Chalk.on("F").bgBlue().black().toString();
        };
    }

}
