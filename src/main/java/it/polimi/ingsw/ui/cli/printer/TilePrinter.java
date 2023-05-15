package it.polimi.ingsw.ui.cli.printer;

import com.github.tomaslanger.chalk.Chalk;
import it.polimi.ingsw.model.board.Tile;

public class TilePrinter {

    public static String print(Tile t) {
        return switch (t) {
            case BOOK -> Chalk.on("B").bgWhite().blue().toString();
            case CAT -> Chalk.on("C").bgGreen().white().toString();
            case GAME -> Chalk.on("G").bgYellow().black().toString();
            case TROPHY -> Chalk.on("T").bgCyan().black().toString();
            case PLANT -> Chalk.on("P").bgRed().black().toString();
            case FRAME -> Chalk.on("F").bgBlue().white().toString();
            case null -> Chalk.on(" ").bgBlack().white().toString();
        };
    }

}
