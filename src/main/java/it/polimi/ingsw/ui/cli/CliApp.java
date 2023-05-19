package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.model.board.Coordinate;
import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.PlayerNumber;
import it.polimi.ingsw.ui.UiGateway;
import it.polimi.ingsw.ui.ViewEventHandler;
import it.polimi.ingsw.ui.cli.parser.ColumnParser;
import it.polimi.ingsw.ui.cli.parser.CoordinatesParser;
import it.polimi.ingsw.ui.cli.parser.PlayerTilesOrderInsertionParser;
import it.polimi.ingsw.ui.cli.printer.BoardPrinter;
import it.polimi.ingsw.ui.cli.printer.BookshelvesPrinter;
import it.polimi.ingsw.ui.cli.printer.CommonGoalCardsPrinter;
import it.polimi.ingsw.ui.cli.printer.PersonalGoalCardPrinter;
import javafx.util.Pair;

import java.util.List;
import java.util.Set;

import static it.polimi.ingsw.model.game.GameMode.GAME_MODE_2_PLAYERS;
import static it.polimi.ingsw.model.game.GameStatus.ENDED;
import static it.polimi.ingsw.utils.model.TurnHelper.getNextPlayerNumber;

public class CliApp implements UiGateway {

    public Game model;
    private ViewEventHandler handler;

    public CliApp() {
    }

    public static void main(String[] args) {
        CliApp app = new CliApp();
        Game game = new Game(GAME_MODE_2_PLAYERS);

        game.addPlayer("Cookie");
        game.addPlayer("Marco");

        app.modelUpdate(game);
        app.onGameStarted();

        while (app.model.getGameStatus() != ENDED) {

            app.gameSelection();
            app.modelUpdate(game);

            app.gameInsertion();
            app.modelUpdate(game);

            app.gameChecking();
            app.modelUpdate(game);

            app.gameNextTurn();
            app.modelUpdate(game);
        }

        app.onGameEnded();
    }

    /**
     * Calls model's onGameStarted and notify user that the game is running
     */
    @Override
    public void onGameStarted() {
        model.onGameStarted();
        Console.printnl();

        Console.out("opzione 1\n");
        Console.out("""
                 _____ ______       ___    ___      ________  ___  ___  _______   ___       ________ ___  _______     \s
                |\\   _ \\  _   \\    |\\  \\  /  /|    |\\   ____\\|\\  \\|\\  \\|\\  ___ \\ |\\  \\     |\\  _____\\\\  \\|\\  ___ \\    \s
                \\ \\  \\\\\\__\\ \\  \\   \\ \\  \\/  / /    \\ \\  \\___|\\ \\  \\\\\\  \\ \\   __/|\\ \\  \\    \\ \\  \\__/\\ \\  \\ \\   __/|   \s
                 \\ \\  \\\\|__| \\  \\   \\ \\    / /      \\ \\_____  \\ \\   __  \\ \\  \\_|/_\\ \\  \\    \\ \\   __\\\\ \\  \\ \\  \\_|/__ \s
                  \\ \\  \\    \\ \\  \\   \\/  /  /        \\|____|\\  \\ \\  \\ \\  \\ \\  \\_|\\ \\ \\  \\____\\ \\  \\_| \\ \\  \\ \\  \\_|\\ \\\s
                   \\ \\__\\    \\ \\__\\__/  / /            ____\\_\\  \\ \\__\\ \\__\\ \\_______\\ \\_______\\ \\__\\   \\ \\__\\ \\_______\\
                    \\|__|     \\|__|\\___/ /            |\\_________\\|__|\\|__|\\|_______|\\|_______|\\|__|    \\|__|\\|_______|
                                  \\|___|/             \\|_________|                                                    \s
                                                                                                                      \s
                                                                                                                      """);
        Console.printnl();

        Console.out("opzione 2\n");
        Console.out("""
                          _____                _____                            _____                    _____                    _____                    _____            _____                    _____                    _____         \s
                         /\\    \\              |\\    \\                          /\\    \\                  /\\    \\                  /\\    \\                  /\\    \\          /\\    \\                  /\\    \\                  /\\    \\        \s
                        /::\\____\\             |:\\____\\                        /::\\    \\                /::\\____\\                /::\\    \\                /::\\____\\        /::\\    \\                /::\\    \\                /::\\    \\       \s
                       /::::|   |             |::|   |                       /::::\\    \\              /:::/    /               /::::\\    \\              /:::/    /       /::::\\    \\               \\:::\\    \\              /::::\\    \\      \s
                      /:::::|   |             |::|   |                      /::::::\\    \\            /:::/    /               /::::::\\    \\            /:::/    /       /::::::\\    \\               \\:::\\    \\            /::::::\\    \\     \s
                     /::::::|   |             |::|   |                     /:::/\\:::\\    \\          /:::/    /               /:::/\\:::\\    \\          /:::/    /       /:::/\\:::\\    \\               \\:::\\    \\          /:::/\\:::\\    \\    \s
                    /:::/|::|   |             |::|   |                    /:::/__\\:::\\    \\        /:::/____/               /:::/__\\:::\\    \\        /:::/    /       /:::/__\\:::\\    \\               \\:::\\    \\        /:::/__\\:::\\    \\   \s
                   /:::/ |::|   |             |::|   |                    \\:::\\   \\:::\\    \\      /::::\\    \\              /::::\\   \\:::\\    \\      /:::/    /       /::::\\   \\:::\\    \\              /::::\\    \\      /::::\\   \\:::\\    \\  \s
                  /:::/  |::|___|______       |::|___|______            ___\\:::\\   \\:::\\    \\    /::::::\\    \\   _____    /::::::\\   \\:::\\    \\    /:::/    /       /::::::\\   \\:::\\    \\    ____    /::::::\\    \\    /::::::\\   \\:::\\    \\ \s
                 /:::/   |::::::::\\    \\      /::::::::\\    \\          /\\   \\:::\\   \\:::\\    \\  /:::/\\:::\\    \\ /\\    \\  /:::/\\:::\\   \\:::\\    \\  /:::/    /       /:::/\\:::\\   \\:::\\    \\  /\\   \\  /:::/\\:::\\    \\  /:::/\\:::\\   \\:::\\    \\\s
                /:::/    |:::::::::\\____\\    /::::::::::\\____\\        /::\\   \\:::\\   \\:::\\____\\/:::/  \\:::\\    /::\\____\\/:::/__\\:::\\   \\:::\\____\\/:::/____/       /:::/  \\:::\\   \\:::\\____\\/::\\   \\/:::/  \\:::\\____\\/:::/__\\:::\\   \\:::\\____\\
                \\::/    / ~~~~~/:::/    /   /:::/~~~~/~~              \\:::\\   \\:::\\   \\::/    /\\::/    \\:::\\  /:::/    /\\:::\\   \\:::\\   \\::/    /\\:::\\    \\       \\::/    \\:::\\   \\::/    /\\:::\\  /:::/    \\::/    /\\:::\\   \\:::\\   \\::/    /
                 \\/____/      /:::/    /   /:::/    /                  \\:::\\   \\:::\\   \\/____/  \\/____/ \\:::\\/:::/    /  \\:::\\   \\:::\\   \\/____/  \\:::\\    \\       \\/____/ \\:::\\   \\/____/  \\:::\\/:::/    / \\/____/  \\:::\\   \\:::\\   \\/____/\s
                             /:::/    /   /:::/    /                    \\:::\\   \\:::\\    \\               \\::::::/    /    \\:::\\   \\:::\\    \\       \\:::\\    \\               \\:::\\    \\       \\::::::/    /            \\:::\\   \\:::\\    \\    \s
                            /:::/    /   /:::/    /                      \\:::\\   \\:::\\____\\               \\::::/    /      \\:::\\   \\:::\\____\\       \\:::\\    \\               \\:::\\____\\       \\::::/____/              \\:::\\   \\:::\\____\\   \s
                           /:::/    /    \\::/    /                        \\:::\\  /:::/    /               /:::/    /        \\:::\\   \\::/    /        \\:::\\    \\               \\::/    /        \\:::\\    \\               \\:::\\   \\::/    /   \s
                          /:::/    /      \\/____/                          \\:::\\/:::/    /               /:::/    /          \\:::\\   \\/____/          \\:::\\    \\               \\/____/          \\:::\\    \\               \\:::\\   \\/____/    \s
                         /:::/    /                                         \\::::::/    /               /:::/    /            \\:::\\    \\               \\:::\\    \\                                \\:::\\    \\               \\:::\\    \\        \s
                        /:::/    /                                           \\::::/    /               /:::/    /              \\:::\\____\\               \\:::\\____\\                                \\:::\\____\\               \\:::\\____\\       \s
                        \\::/    /                                             \\::/    /                \\::/    /                \\::/    /                \\::/    /                                 \\::/    /                \\::/    /       \s
                         \\/____/                                               \\/____/                  \\/____/                  \\/____/                  \\/____/                                   \\/____/                  \\/____/        \s
                                                                                                                                                                                                                                            \s""");
        Console.printnl();

        Console.out("opzione 3\n");
        Console.out("""
                '##::::'##:'##:::'##:::::'######::'##::::'##:'########:'##:::::::'########:'####:'########:
                 ###::'###:. ##:'##:::::'##... ##: ##:::: ##: ##.....:: ##::::::: ##.....::. ##:: ##.....::
                 ####'####::. ####:::::: ##:::..:: ##:::: ##: ##::::::: ##::::::: ##:::::::: ##:: ##:::::::
                 ## ### ##:::. ##:::::::. ######:: #########: ######::: ##::::::: ######:::: ##:: ######:::
                 ##. #: ##:::: ##::::::::..... ##: ##.... ##: ##...:::: ##::::::: ##...::::: ##:: ##...::::
                 ##:.:: ##:::: ##:::::::'##::: ##: ##:::: ##: ##::::::: ##::::::: ##:::::::: ##:: ##:::::::
                 ##:::: ##:::: ##:::::::. ######:: ##:::: ##: ########: ########: ##:::::::'####: ########:
                ..:::::..:::::..:::::::::......:::..:::::..::........::........::..::::::::....::........::
                """);
        Console.printnl();

        Console.out("opzione 4\n");
        Console.out("""
                 __       __  __      __         ______   __    __  ________  __        ________  ______  ________\s
                |  \\     /  \\|  \\    /  \\       /      \\ |  \\  |  \\|        \\|  \\      |        \\|      \\|        \\
                | $$\\   /  $$ \\$$\\  /  $$      |  $$$$$$\\| $$  | $$| $$$$$$$$| $$      | $$$$$$$$ \\$$$$$$| $$$$$$$$
                | $$$\\ /  $$$  \\$$\\/  $$       | $$___\\$$| $$__| $$| $$__    | $$      | $$__      | $$  | $$__   \s
                | $$$$\\  $$$$   \\$$  $$         \\$$    \\ | $$    $$| $$  \\   | $$      | $$  \\     | $$  | $$  \\  \s
                | $$\\$$ $$ $$    \\$$$$          _\\$$$$$$\\| $$$$$$$$| $$$$$   | $$      | $$$$$     | $$  | $$$$$  \s
                | $$ \\$$$| $$    | $$          |  \\__| $$| $$  | $$| $$_____ | $$_____ | $$       _| $$_ | $$_____\s
                | $$  \\$ | $$    | $$           \\$$    $$| $$  | $$| $$     \\| $$     \\| $$      |   $$ \\| $$     \\
                 \\$$      \\$$     \\$$            \\$$$$$$  \\$$   \\$$ \\$$$$$$$$ \\$$$$$$$$ \\$$       \\$$$$$$ \\$$$$$$$$
                                                                                                                  \s
                                                                                                                  \s
                                                                                                                  \s""");
        Console.printnl();

        Console.out("opzione 5\n");
        Console.out("""
                $$\\      $$\\ $$\\     $$\\        $$$$$$\\  $$\\   $$\\ $$$$$$$$\\ $$\\       $$$$$$$$\\ $$$$$$\\ $$$$$$$$\\\s
                $$$\\    $$$ |\\$$\\   $$  |      $$  __$$\\ $$ |  $$ |$$  _____|$$ |      $$  _____|\\_$$  _|$$  _____|
                $$$$\\  $$$$ | \\$$\\ $$  /       $$ /  \\__|$$ |  $$ |$$ |      $$ |      $$ |        $$ |  $$ |     \s
                $$\\$$\\$$ $$ |  \\$$$$  /        \\$$$$$$\\  $$$$$$$$ |$$$$$\\    $$ |      $$$$$\\      $$ |  $$$$$\\   \s
                $$ \\$$$  $$ |   \\$$  /          \\____$$\\ $$  __$$ |$$  __|   $$ |      $$  __|     $$ |  $$  __|  \s
                $$ |\\$  /$$ |    $$ |          $$\\   $$ |$$ |  $$ |$$ |      $$ |      $$ |        $$ |  $$ |     \s
                $$ | \\_/ $$ |    $$ |          \\$$$$$$  |$$ |  $$ |$$$$$$$$\\ $$$$$$$$\\ $$ |      $$$$$$\\ $$$$$$$$\\\s
                \\__|     \\__|    \\__|           \\______/ \\__|  \\__|\\________|\\________|\\__|      \\______|\\________|
                                                                                                                  \s
                                                                                                                  \s
                                                                                                                  \s""");
        Console.printnl();

        Console.out("opzione 6\n");
        Console.out("""
                ___________________________________________________________________________________
                    _   _    _     _        __     _     _   _____    _      _____      __   _____\s
                    /  /|    |    /       /    )   /    /    /    '   /      /    '     /    /    '
                ---/| /-|----|---/--------\\-------/___ /----/__------/------/__--------/----/__----
                  / |/  |    |  /          \\     /    /    /        /      /          /    /      \s
                _/__/___|____|_/_______(____/___/____/____/____ ___/____/_/________ _/_ __/____ ___
                              /                                                                   \s
                          (_ /                                                                    \s""");
        Console.printnl();

        Console.out("opzione 7\n");
        Console.out("""
                 /$$      /$$ /$$     /$$        /$$$$$$  /$$   /$$ /$$$$$$$$ /$$       /$$$$$$$$ /$$$$$$ /$$$$$$$$
                | $$$    /$$$|  $$   /$$/       /$$__  $$| $$  | $$| $$_____/| $$      | $$_____/|_  $$_/| $$_____/
                | $$$$  /$$$$ \\  $$ /$$/       | $$  \\__/| $$  | $$| $$      | $$      | $$        | $$  | $$     \s
                | $$ $$/$$ $$  \\  $$$$/        |  $$$$$$ | $$$$$$$$| $$$$$   | $$      | $$$$$     | $$  | $$$$$  \s
                | $$  $$$| $$   \\  $$/          \\____  $$| $$__  $$| $$__/   | $$      | $$__/     | $$  | $$__/  \s
                | $$\\  $ | $$    | $$           /$$  \\ $$| $$  | $$| $$      | $$      | $$        | $$  | $$     \s
                | $$ \\/  | $$    | $$          |  $$$$$$/| $$  | $$| $$$$$$$$| $$$$$$$$| $$       /$$$$$$| $$$$$$$$
                |__/     |__/    |__/           \\______/ |__/  |__/|________/|________/|__/      |______/|________/
                                                                                                                  \s
                                                                                                                  \s
                                                                                                                  \s""");
        Console.printnl();

        Console.out("opzione 8\n");
        Console.out("""
                 __  ____     __   _____ _    _ ______ _      ______ _____ ______\s
                |  \\/  \\ \\   / /  / ____| |  | |  ____| |    |  ____|_   _|  ____|
                | \\  / |\\ \\_/ /  | (___ | |__| | |__  | |    | |__    | | | |__  \s
                | |\\/| | \\   /    \\___ \\|  __  |  __| | |    |  __|   | | |  __| \s
                | |  | |  | |     ____) | |  | | |____| |____| |     _| |_| |____\s
                |_|  |_|  |_|    |_____/|_|  |_|______|______|_|    |_____|______|
                                                                                 \s
                                                                                 \s""".indent(1));
        Console.printnl();

        Console.out("opzione 9\n");
        Console.out("""
                 __       __  __      __         ______   __    __  ________  __        ________  ______  ________\s
                /  \\     /  |/  \\    /  |       /      \\ /  |  /  |/        |/  |      /        |/      |/        |
                $$  \\   /$$ |$$  \\  /$$/       /$$$$$$  |$$ |  $$ |$$$$$$$$/ $$ |      $$$$$$$$/ $$$$$$/ $$$$$$$$/\s
                $$$  \\ /$$$ | $$  \\/$$/        $$ \\__$$/ $$ |__$$ |$$ |__    $$ |      $$ |__      $$ |  $$ |__   \s
                $$$$  /$$$$ |  $$  $$/         $$      \\ $$    $$ |$$    |   $$ |      $$    |     $$ |  $$    |  \s
                $$ $$ $$/$$ |   $$$$/           $$$$$$  |$$$$$$$$ |$$$$$/    $$ |      $$$$$/      $$ |  $$$$$/   \s
                $$ |$$$/ $$ |    $$ |          /  \\__$$ |$$ |  $$ |$$ |_____ $$ |_____ $$ |       _$$ |_ $$ |_____\s
                $$ | $/  $$ |    $$ |          $$    $$/ $$ |  $$ |$$       |$$       |$$ |      / $$   |$$       |
                $$/      $$/     $$/            $$$$$$/  $$/   $$/ $$$$$$$$/ $$$$$$$$/ $$/       $$$$$$/ $$$$$$$$/\s
                                                                                                                  \s
                                                                                                                  \s
                                                                                                                  \s""");
        Console.printnl();

        Console.out("opzione 10\n");
        Console.out("""
                ##     ## ##    ##     ######  ##     ## ######## ##       ######## #### ########\s
                ###   ###  ##  ##     ##    ## ##     ## ##       ##       ##        ##  ##      \s
                #### ####   ####      ##       ##     ## ##       ##       ##        ##  ##      \s
                ## ### ##    ##        ######  ######### ######   ##       ######    ##  ######  \s
                ##     ##    ##             ## ##     ## ##       ##       ##        ##  ##      \s
                ##     ##    ##       ##    ## ##     ## ##       ##       ##        ##  ##      \s
                ##     ##    ##        ######  ##     ## ######## ######## ##       #### ########\s""");
        Console.printnl();

        Console.out("opzione 11\n");
        Console.out("""
                ╔╦╗╦ ╦  ╔═╗╦ ╦╔═╗╦  ╔═╗╦╔═╗
                ║║║╚╦╝  ╚═╗╠═╣║╣ ║  ╠╣ ║║╣\s
                ╩ ╩ ╩   ╚═╝╩ ╩╚═╝╩═╝╚  ╩╚═╝""");
        Console.printnl();

        Console.out("opzione 12\n");
        Console.out("""
                 .----------------.  .----------------.   .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------.\s
                | .--------------. || .--------------. | | .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |
                | | ____    ____ | || |  ____  ____  | | | |    _______   | || |  ____  ____  | || |  _________   | || |   _____      | || |  _________   | || |     _____    | || |  _________   | |
                | ||_   \\  /   _|| || | |_  _||_  _| | | | |   /  ___  |  | || | |_   ||   _| | || | |_   ___  |  | || |  |_   _|     | || | |_   ___  |  | || |    |_   _|   | || | |_   ___  |  | |
                | |  |   \\/   |  | || |   \\ \\  / /   | | | |  |  (__ \\_|  | || |   | |__| |   | || |   | |_  \\_|  | || |    | |       | || |   | |_  \\_|  | || |      | |     | || |   | |_  \\_|  | |
                | |  | |\\  /| |  | || |    \\ \\/ /    | | | |   '.___`-.   | || |   |  __  |   | || |   |  _|  _   | || |    | |   _   | || |   |  _|      | || |      | |     | || |   |  _|  _   | |
                | | _| |_\\/_| |_ | || |    _|  |_    | | | |  |`\\____) |  | || |  _| |  | |_  | || |  _| |___/ |  | || |   _| |__/ |  | || |  _| |_       | || |     _| |_    | || |  _| |___/ |  | |
                | ||_____||_____|| || |   |______|   | | | |  |_______.'  | || | |____||____| | || | |_________|  | || |  |________|  | || | |_____|      | || |    |_____|   | || | |_________|  | |
                | |              | || |              | | | |              | || |              | || |              | || |              | || |              | || |              | || |              | |
                | '--------------' || '--------------' | | '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |
                 '----------------'  '----------------'   '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'\s""");
        Console.printnl();

        Console.out("opzione 13\n");
        Console.out("""
                          .         .                                                                                                                                   \s
                         ,8.       ,8.   `8.`8888.      ,8'              d888888o.   8 8888        8 8 8888888888   8 8888         8 8888888888    8 8888 8 8888888888  \s
                        ,888.     ,888.   `8.`8888.    ,8'             .`8888:' `88. 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                       .`8888.   .`8888.   `8.`8888.  ,8'              8.`8888.   Y8 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                      ,8.`8888. ,8.`8888.   `8.`8888.,8'               `8.`8888.     8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                     ,8'8.`8888,8^8.`8888.   `8.`88888'                 `8.`8888.    8 8888        8 8 888888888888 8 8888         8 888888888888  8 8888 8 888888888888\s
                    ,8' `8.`8888' `8.`8888.   `8. 8888                   `8.`8888.   8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                   ,8'   `8.`88'   `8.`8888.   `8 8888                    `8.`8888.  8 8888888888888 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                  ,8'     `8.`'     `8.`8888.   8 8888                8b   `8.`8888. 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                 ,8'       `8        `8.`8888.  8 8888                `8b.  ;8.`8888 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                ,8'         `         `8.`8888. 8 8888                 `Y8888P ,88P' 8 8888        8 8 888888888888 8 888888888888 8 8888          8 8888 8 888888888888\s""");
        Console.printnl();

        Console.out("opzione 14\n");
        Console.out("""
                888b     d888 Y88b   d88P       .d8888b.  888    888 8888888888 888      8888888888 8888888 8888888888\s
                8888b   d8888  Y88b d88P       d88P  Y88b 888    888 888        888      888          888   888       \s
                88888b.d88888   Y88o88P        Y88b.      888    888 888        888      888          888   888       \s
                888Y88888P888    Y888P          "Y888b.   8888888888 8888888    888      8888888      888   8888888   \s
                888 Y888P 888     888              "Y88b. 888    888 888        888      888          888   888       \s
                888  Y8P  888     888                "888 888    888 888        888      888          888   888       \s
                888   "   888     888          Y88b  d88P 888    888 888        888      888          888   888       \s
                888       888     888           "Y8888P"  888    888 8888888888 88888888 888        8888888 8888888888\s
                                                                                                                      \s
                                                                                                                      \s
                                                                                                                      \s""");
        Console.printnl();

        Console.out("opzione 15\n");
        Console.out("""
                                                                                                                                                                                                                      \s
                                                                                                                                                                                                                      \s
                MMMMMMMM               MMMMMMMMYYYYYYY       YYYYYYY        SSSSSSSSSSSSSSS HHHHHHHHH     HHHHHHHHHEEEEEEEEEEEEEEEEEEEEEELLLLLLLLLLL             FFFFFFFFFFFFFFFFFFFFFFIIIIIIIIIIEEEEEEEEEEEEEEEEEEEEEE
                M:::::::M             M:::::::MY:::::Y       Y:::::Y      SS:::::::::::::::SH:::::::H     H:::::::HE::::::::::::::::::::EL:::::::::L             F::::::::::::::::::::FI::::::::IE::::::::::::::::::::E
                M::::::::M           M::::::::MY:::::Y       Y:::::Y     S:::::SSSSSS::::::SH:::::::H     H:::::::HE::::::::::::::::::::EL:::::::::L             F::::::::::::::::::::FI::::::::IE::::::::::::::::::::E
                M:::::::::M         M:::::::::MY::::::Y     Y::::::Y     S:::::S     SSSSSSSHH::::::H     H::::::HHEE::::::EEEEEEEEE::::ELL:::::::LL             FF::::::FFFFFFFFF::::FII::::::IIEE::::::EEEEEEEEE::::E
                M::::::::::M       M::::::::::MYYY:::::Y   Y:::::YYY     S:::::S              H:::::H     H:::::H    E:::::E       EEEEEE  L:::::L                 F:::::F       FFFFFF  I::::I    E:::::E       EEEEEE
                M:::::::::::M     M:::::::::::M   Y:::::Y Y:::::Y        S:::::S              H:::::H     H:::::H    E:::::E               L:::::L                 F:::::F               I::::I    E:::::E            \s
                M:::::::M::::M   M::::M:::::::M    Y:::::Y:::::Y          S::::SSSS           H::::::HHHHH::::::H    E::::::EEEEEEEEEE     L:::::L                 F::::::FFFFFFFFFF     I::::I    E::::::EEEEEEEEEE  \s
                M::::::M M::::M M::::M M::::::M     Y:::::::::Y            SS::::::SSSSS      H:::::::::::::::::H    E:::::::::::::::E     L:::::L                 F:::::::::::::::F     I::::I    E:::::::::::::::E  \s
                M::::::M  M::::M::::M  M::::::M      Y:::::::Y               SSS::::::::SS    H:::::::::::::::::H    E:::::::::::::::E     L:::::L                 F:::::::::::::::F     I::::I    E:::::::::::::::E  \s
                M::::::M   M:::::::M   M::::::M       Y:::::Y                   SSSSSS::::S   H::::::HHHHH::::::H    E::::::EEEEEEEEEE     L:::::L                 F::::::FFFFFFFFFF     I::::I    E::::::EEEEEEEEEE  \s
                M::::::M    M:::::M    M::::::M       Y:::::Y                        S:::::S  H:::::H     H:::::H    E:::::E               L:::::L                 F:::::F               I::::I    E:::::E            \s
                M::::::M     MMMMM     M::::::M       Y:::::Y                        S:::::S  H:::::H     H:::::H    E:::::E       EEEEEE  L:::::L         LLLLLL  F:::::F               I::::I    E:::::E       EEEEEE
                M::::::M               M::::::M       Y:::::Y            SSSSSSS     S:::::SHH::::::H     H::::::HHEE::::::EEEEEEEE:::::ELL:::::::LLLLLLLLL:::::LFF:::::::FF           II::::::IIEE::::::EEEEEEEE:::::E
                M::::::M               M::::::M    YYYY:::::YYYY         S::::::SSSSSS:::::SH:::::::H     H:::::::HE::::::::::::::::::::EL::::::::::::::::::::::LF::::::::FF           I::::::::IE::::::::::::::::::::E
                M::::::M               M::::::M    Y:::::::::::Y         S:::::::::::::::SS H:::::::H     H:::::::HE::::::::::::::::::::EL::::::::::::::::::::::LF::::::::FF           I::::::::IE::::::::::::::::::::E
                MMMMMMMM               MMMMMMMM    YYYYYYYYYYYYY          SSSSSSSSSSSSSSS   HHHHHHHHH     HHHHHHHHHEEEEEEEEEEEEEEEEEEEEEELLLLLLLLLLLLLLLLLLLLLLLLFFFFFFFFFFF           IIIIIIIIIIEEEEEEEEEEEEEEEEEEEEEE
                                                                                                                                                                                                                      \s
                                                                                                                                                                                                                      \s
                                                                                                                                                                                                                      \s
                                                                                                                                                                                                                      \s
                                                                                                                                                                                                                      \s
                                                                                                                                                                                                                      \s
                                                                                                                                                                                                                      \s""");
        Console.printnl();

        Console.out("opzione 16\n");
        Console.out("""
                 _   .-')                        .-')    ('-. .-.   ('-.                                ('-.  \s
                ( '.( OO )_                     ( OO ). ( OO )  / _(  OO)                             _(  OO) \s
                 ,--.   ,--.) ,--.   ,--.      (_)---\\_),--. ,--.(,------.,--.        ,------.,-.-') (,------.\s
                 |   `.'   |   \\  `.'  /       /    _ | |  | |  | |  .---'|  |.-') ('-| _.---'|  |OO) |  .---'\s
                 |         | .-')     /        \\  :` `. |   .|  | |  |    |  | OO )(OO|(_\\    |  |  \\ |  |    \s
                 |  |'.'|  |(OO  \\   /          '..`''.)|       |(|  '--. |  |`-' |/  |  '--. |  |(_/(|  '--. \s
                 |  |   |  | |   /  /\\_        .-._)   \\|  .-.  | |  .--'(|  '---.'\\_)|  .--',|  |_.' |  .--' \s
                 |  |   |  | `-./  /.__)       \\       /|  | |  | |  `---.|      |   \\|  |_)(_|  |    |  `---.\s
                 `--'   `--'   `--'             `-----' `--' `--' `------'`------'    `--'    `--'    `------'\s""");
        Console.printnl();

        Console.out("Game has started, Enjoy the game and good luck!\n");
    }

    /**
     * Updates model's instance in order to show users an updated model every turn
     *
     * @param game model is passed to the function on order to update always the same model
     */
    @Override
    public void modelUpdate(Game game) {
        this.model = game;
    }

    /**
     * Shows users' Bookshelves, updates Board, Common goal cards and Tokens, First Player and Current Player, Private
     * goal card
     */
    public void printGameModel() {
        //ConsoleClear.clearConsole();
        Console.printnl();
        Console.out("Board:");
        Console.printnl();
        BoardPrinter.print(model.getBoard());

        CommonGoalCardsPrinter.print(model.getCommonGoalCards());
        Console.printnl();

        Console.printnl();
        Console.out("Personal goal card for player " +
                model.getSessions().getByNumber(model.getCurrentPlayer().getPlayerNumber()).getUsername() + ":\n");
        Console.flush();
        //TODO printing the current player's name is temporary, once fixed remove
        //TODO we need to print the player's card for whom is asking for their private goal card, not the current player. Others can't see other player's card
        PersonalGoalCardPrinter.print(model.getCurrentPlayer().getPersonalGoalCard());
        Console.printnl();
        BookshelvesPrinter.print(model);
        Console.printnl();
    }

    /**
     * Calls model's onPlayerSelectionPhase in order have the current player selecting up to three tiles.
     */
    @Override
    public void gameSelection() {
        printGameModel();
        Set<Coordinate> validCoordinates = CoordinatesParser.scan(model);
        model.onPlayerSelectionPhase(validCoordinates);
    }

    /**
     * Inserts selected tiles inside current player's own Bookshelf, in the column they chose.
     */
    @Override
    public void gameInsertion() {
        int tilesSize = model.getCurrentPlayer().getPlayerTileSelection().getSelectedTiles().size();
        List<Tile> selectedTiles = model.getCurrentPlayer().getPlayerTileSelection().getSelectedTiles();
        int column = ColumnParser.scan(model.getCurrentPlayer().getBookshelf().getShelfMatrix(), tilesSize);
        List<Tile> orderedTiles;

        if (tilesSize > 1) {
            orderedTiles = PlayerTilesOrderInsertionParser
                    .scan(selectedTiles);
        } else
            orderedTiles = selectedTiles;

        model.onPlayerInsertionPhase(column, orderedTiles);
    }

    public void gameChecking() {
        model.onPlayerCheckingPhase();
    }

    public void gameNextTurn() {
        model.onNextTurn(model.getSessions().getByNumber(getNextPlayerNumber(model.getCurrentPlayer().getPlayerNumber(),
                model.getGameMode())).getUsername());
    }

    /**
     * Shows ranking and announces the winner.
     */
    @Override
    public void onGameEnded() {
        Console.out("""
                The game has ended. Congratulations to the winner!
                Here's the player's ranking with their points:
                """);

        List<Pair<PlayerNumber, Integer>> playersRanking = model.onGameEnded();

        playersRanking.forEach(System.out::println);
    }

    public void setHandler(ViewEventHandler handler) {
        this.handler = handler;
    }

}
