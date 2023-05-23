package it.polimi.ingsw.ui.cli.splash;

import com.github.tomaslanger.chalk.Chalk;
import it.polimi.ingsw.ui.cli.Console;
import it.polimi.ingsw.utils.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SplashScreen {

    private static final List<String> screens = Arrays.asList(
            "\n" +
                    "        _   _       _        _         _            _       _    _            _             _        _          _      \n" +
                    "       /\\_\\/\\_\\ _  /\\ \\     /\\_\\      / /\\         / /\\    / /\\ /\\ \\         _\\ \\          /\\ \\     /\\ \\       /\\ \\    \n" +
                    "      / / / / //\\_\\\\ \\ \\   / / /     / /  \\       / / /   / / //  \\ \\       /\\__ \\        /  \\ \\    \\ \\ \\     /  \\ \\   \n" +
                    "     /\\ \\/ \\ \\/ / / \\ \\ \\_/ / /     / / /\\ \\__   / /_/   / / // /\\ \\ \\     / /_ \\_\\      / /\\ \\ \\   /\\ \\_\\   / /\\ \\ \\  \n" +
                    "    /  \\____\\__/ /   \\ \\___/ /     / / /\\ \\___\\ / /\\ \\__/ / // / /\\ \\_\\   / / /\\/_/     / / /\\ \\_\\ / /\\/_/  / / /\\ \\_\\ \n" +
                    "   / /\\/________/     \\ \\ \\_/      \\ \\ \\ \\/___// /\\ \\___\\/ // /_/_ \\/_/  / / /         / /_/_ \\/_// / /    / /_/_ \\/_/ \n" +
                    "  / / /\\/_// / /       \\ \\ \\        \\ \\ \\     / / /\\/___/ // /____/\\    / / /         / /____/\\  / / /    / /____/\\    \n" +
                    " / / /    / / /         \\ \\ \\   _    \\ \\ \\   / / /   / / // /\\____\\/   / / / ____    / /\\____\\/ / / /    / /\\____\\/    \n" +
                    "/ / /    / / /           \\ \\ \\ /_/\\__/ / /  / / /   / / // / /______  / /_/_/ ___/\\ / / /   ___/ / /__  / / /______    \n" +
                    "\\/_/    / / /             \\ \\_\\\\ \\/___/ /  / / /   / / // / /_______\\/_______/\\__\\// / /   /\\__\\/_/___\\/ / /_______\\   \n" +
                    "        \\/_/               \\/_/ \\_____\\/   \\/_/    \\/_/ \\/__________/\\_______\\/    \\/_/    \\/_________/\\/__________/   \n" +
                    "                                                                                                                       \n",
            "\n" +
                    "      ___                       ___           ___           ___                         ___                       ___     \n" +
                    "     /\\  \\                     /\\__\\         /\\  \\         /\\__\\                       /\\__\\                     /\\__\\    \n" +
                    "    |::\\  \\         ___       /:/ _/_        \\:\\  \\       /:/ _/_                     /:/ _/_       ___         /:/ _/_   \n" +
                    "    |:|:\\  \\       /|  |     /:/ /\\  \\        \\:\\  \\     /:/ /\\__\\                   /:/ /\\__\\     /\\__\\       /:/ /\\__\\  \n" +
                    "  __|:|\\:\\  \\     |:|  |    /:/ /::\\  \\   ___ /::\\  \\   /:/ /:/ _/_   ___     ___   /:/ /:/  /    /:/__/      /:/ /:/ _/_ \n" +
                    " /::::|_\\:\\__\\    |:|  |   /:/_/:/\\:\\__\\ /\\  /:/\\:\\__\\ /:/_/:/ /\\__\\ /\\  \\   /\\__\\ /:/_/:/  /    /::\\  \\     /:/_/:/ /\\__\\\n" +
                    " \\:\\~~\\  \\/__/  __|:|__|   \\:\\/:/ /:/  / \\:\\/:/  \\/__/ \\:\\/:/ /:/  / \\:\\  \\ /:/  / \\:\\/:/  /     \\/\\:\\  \\__  \\:\\/:/ /:/  /\n" +
                    "  \\:\\  \\       /::::\\  \\    \\::/ /:/  /   \\::/__/       \\::/_/:/  /   \\:\\  /:/  /   \\::/__/       ~~\\:\\/\\__\\  \\::/_/:/  / \n" +
                    "   \\:\\  \\      ~~~~\\:\\  \\    \\/_/:/  /     \\:\\  \\        \\:\\/:/  /     \\:\\/:/  /     \\:\\  \\          \\::/  /   \\:\\/:/  /  \n" +
                    "    \\:\\__\\          \\:\\__\\     /:/  /       \\:\\__\\        \\::/  /       \\::/  /       \\:\\__\\         /:/  /     \\::/  /   \n" +
                    "     \\/__/           \\/__/     \\/__/         \\/__/         \\/__/         \\/__/         \\/__/         \\/__/       \\/__/    \n",
            "\n" +
                    "    ___       ___       ___       ___       ___       ___       ___       ___       ___   \n" +
                    "   /\\__\\     /\\__\\     /\\  \\     /\\__\\     /\\  \\     /\\__\\     /\\  \\     /\\  \\     /\\  \\  \n" +
                    "  /::L_L_   |::L__L   /::\\  \\   /:/__/_   /::\\  \\   /:/  /    /::\\  \\   _\\:\\  \\   /::\\  \\ \n" +
                    " /:/L:\\__\\  |:::\\__\\ /\\:\\:\\__\\ /::\\/\\__\\ /::\\:\\__\\ /:/__/    /::\\:\\__\\ /\\/::\\__\\ /::\\:\\__\\\n" +
                    " \\/_/:/  /  /:;;/__/ \\:\\:\\/__/ \\/\\::/  / \\:\\:\\/  / \\:\\  \\    \\/\\:\\/__/ \\::/\\/__/ \\:\\:\\/  /\n" +
                    "   /:/  /   \\/__/     \\::/  /    /:/  /   \\:\\/  /   \\:\\__\\      \\/__/   \\:\\__\\    \\:\\/  / \n" +
                    "   \\/__/               \\/__/     \\/__/     \\/__/     \\/__/               \\/__/     \\/__/  \n",
            "\n" +
                    "          _____                _____                    _____                    _____                    _____                    _____            _____                    _____                    _____          \n" +
                    "         /\\    \\              |\\    \\                  /\\    \\                  /\\    \\                  /\\    \\                  /\\    \\          /\\    \\                  /\\    \\                  /\\    \\         \n" +
                    "        /::\\____\\             |:\\____\\                /::\\    \\                /::\\____\\                /::\\    \\                /::\\____\\        /::\\    \\                /::\\    \\                /::\\    \\        \n" +
                    "       /::::|   |             |::|   |               /::::\\    \\              /:::/    /               /::::\\    \\              /:::/    /       /::::\\    \\               \\:::\\    \\              /::::\\    \\       \n" +
                    "      /:::::|   |             |::|   |              /::::::\\    \\            /:::/    /               /::::::\\    \\            /:::/    /       /::::::\\    \\               \\:::\\    \\            /::::::\\    \\      \n" +
                    "     /::::::|   |             |::|   |             /:::/\\:::\\    \\          /:::/    /               /:::/\\:::\\    \\          /:::/    /       /:::/\\:::\\    \\               \\:::\\    \\          /:::/\\:::\\    \\     \n" +
                    "    /:::/|::|   |             |::|   |            /:::/__\\:::\\    \\        /:::/____/               /:::/__\\:::\\    \\        /:::/    /       /:::/__\\:::\\    \\               \\:::\\    \\        /:::/__\\:::\\    \\    \n" +
                    "   /:::/ |::|   |             |::|   |            \\:::\\   \\:::\\    \\      /::::\\    \\              /::::\\   \\:::\\    \\      /:::/    /       /::::\\   \\:::\\    \\              /::::\\    \\      /::::\\   \\:::\\    \\   \n" +
                    "  /:::/  |::|___|______       |::|___|______    ___\\:::\\   \\:::\\    \\    /::::::\\    \\   _____    /::::::\\   \\:::\\    \\    /:::/    /       /::::::\\   \\:::\\    \\    ____    /::::::\\    \\    /::::::\\   \\:::\\    \\  \n" +
                    " /:::/   |::::::::\\    \\      /::::::::\\    \\  /\\   \\:::\\   \\:::\\    \\  /:::/\\:::\\    \\ /\\    \\  /:::/\\:::\\   \\:::\\    \\  /:::/    /       /:::/\\:::\\   \\:::\\    \\  /\\   \\  /:::/\\:::\\    \\  /:::/\\:::\\   \\:::\\    \\ \n" +
                    "/:::/    |:::::::::\\____\\    /::::::::::\\____\\/::\\   \\:::\\   \\:::\\____\\/:::/  \\:::\\    /::\\____\\/:::/__\\:::\\   \\:::\\____\\/:::/____/       /:::/  \\:::\\   \\:::\\____\\/::\\   \\/:::/  \\:::\\____\\/:::/__\\:::\\   \\:::\\____\\\n" +
                    "\\::/    / ~~~~~/:::/    /   /:::/~~~~/~~      \\:::\\   \\:::\\   \\::/    /\\::/    \\:::\\  /:::/    /\\:::\\   \\:::\\   \\::/    /\\:::\\    \\       \\::/    \\:::\\   \\::/    /\\:::\\  /:::/    \\::/    /\\:::\\   \\:::\\   \\::/    /\n" +
                    " \\/____/      /:::/    /   /:::/    /          \\:::\\   \\:::\\   \\/____/  \\/____/ \\:::\\/:::/    /  \\:::\\   \\:::\\   \\/____/  \\:::\\    \\       \\/____/ \\:::\\   \\/____/  \\:::\\/:::/    / \\/____/  \\:::\\   \\:::\\   \\/____/ \n" +
                    "             /:::/    /   /:::/    /            \\:::\\   \\:::\\    \\               \\::::::/    /    \\:::\\   \\:::\\    \\       \\:::\\    \\               \\:::\\    \\       \\::::::/    /            \\:::\\   \\:::\\    \\     \n" +
                    "            /:::/    /   /:::/    /              \\:::\\   \\:::\\____\\               \\::::/    /      \\:::\\   \\:::\\____\\       \\:::\\    \\               \\:::\\____\\       \\::::/____/              \\:::\\   \\:::\\____\\    \n" +
                    "           /:::/    /    \\::/    /                \\:::\\  /:::/    /               /:::/    /        \\:::\\   \\::/    /        \\:::\\    \\               \\::/    /        \\:::\\    \\               \\:::\\   \\::/    /    \n" +
                    "          /:::/    /      \\/____/                  \\:::\\/:::/    /               /:::/    /          \\:::\\   \\/____/          \\:::\\    \\               \\/____/          \\:::\\    \\               \\:::\\   \\/____/     \n" +
                    "         /:::/    /                                 \\::::::/    /               /:::/    /            \\:::\\    \\               \\:::\\    \\                                \\:::\\    \\               \\:::\\    \\         \n" +
                    "        /:::/    /                                   \\::::/    /               /:::/    /              \\:::\\____\\               \\:::\\____\\                                \\:::\\____\\               \\:::\\____\\        \n" +
                    "        \\::/    /                                     \\::/    /                \\::/    /                \\::/    /                \\::/    /                                 \\::/    /                \\::/    /        \n" +
                    "         \\/____/                                       \\/____/                  \\/____/                  \\/____/                  \\/____/                                   \\/____/                  \\/____/         \n" +
                    "                                                                                                                                                                                                                     \n",
            "\n" +
                    "      ___                       ___           ___           ___                         ___                       ___     \n" +
                    "     /\\  \\                     /\\__\\         /\\  \\         /\\__\\                       /\\__\\                     /\\__\\    \n" +
                    "    |::\\  \\         ___       /:/ _/_        \\:\\  \\       /:/ _/_                     /:/ _/_       ___         /:/ _/_   \n" +
                    "    |:|:\\  \\       /|  |     /:/ /\\  \\        \\:\\  \\     /:/ /\\__\\                   /:/ /\\__\\     /\\__\\       /:/ /\\__\\  \n" +
                    "  __|:|\\:\\  \\     |:|  |    /:/ /::\\  \\   ___ /::\\  \\   /:/ /:/ _/_   ___     ___   /:/ /:/  /    /:/__/      /:/ /:/ _/_ \n" +
                    " /::::|_\\:\\__\\    |:|  |   /:/_/:/\\:\\__\\ /\\  /:/\\:\\__\\ /:/_/:/ /\\__\\ /\\  \\   /\\__\\ /:/_/:/  /    /::\\  \\     /:/_/:/ /\\__\\\n" +
                    " \\:\\~~\\  \\/__/  __|:|__|   \\:\\/:/ /:/  / \\:\\/:/  \\/__/ \\:\\/:/ /:/  / \\:\\  \\ /:/  / \\:\\/:/  /     \\/\\:\\  \\__  \\:\\/:/ /:/  /\n" +
                    "  \\:\\  \\       /::::\\  \\    \\::/ /:/  /   \\::/__/       \\::/_/:/  /   \\:\\  /:/  /   \\::/__/       ~~\\:\\/\\__\\  \\::/_/:/  / \n" +
                    "   \\:\\  \\      ~~~~\\:\\  \\    \\/_/:/  /     \\:\\  \\        \\:\\/:/  /     \\:\\/:/  /     \\:\\  \\          \\::/  /   \\:\\/:/  /  \n" +
                    "    \\:\\__\\          \\:\\__\\     /:/  /       \\:\\__\\        \\::/  /       \\::/  /       \\:\\__\\         /:/  /     \\::/  /   \n" +
                    "     \\/__/           \\/__/     \\/__/         \\/__/         \\/__/         \\/__/         \\/__/         \\/__/       \\/__/    \n",
            "\n" +
                    "      ___                         ___           ___           ___           ___                                  ___     \n" +
                    "     /  /\\          __           /  /\\         /  /\\         /  /\\         /  /\\      ___            ___        /  /\\    \n" +
                    "    /  /::|        |  |\\        /  /::\\       /  /:/        /  /::\\       /  /:/     /  /\\          /__/\\      /  /::\\   \n" +
                    "   /  /:|:|        |  |:|      /__/:/\\:\\     /  /:/        /  /:/\\:\\     /  /:/     /  /::\\         \\__\\:\\    /  /:/\\:\\  \n" +
                    "  /  /:/|:|__      |  |:|     _\\_ \\:\\ \\:\\   /  /::\\ ___   /  /::\\ \\:\\   /  /:/     /  /:/\\:\\        /  /::\\  /  /::\\ \\:\\ \n" +
                    " /__/:/_|::::\\     |__|:|__  /__/\\ \\:\\ \\:\\ /__/:/\\:\\  /\\ /__/:/\\:\\ \\:\\ /__/:/     /  /::\\ \\:\\    __/  /:/\\/ /__/:/\\:\\ \\:\\\n" +
                    " \\__\\/  /~~/:/     /  /::::\\ \\  \\:\\ \\:\\_\\/ \\__\\/  \\:\\/:/ \\  \\:\\ \\:\\_\\/ \\  \\:\\    /__/:/\\:\\ \\:\\  /__/\\/:/~~  \\  \\:\\ \\:\\_\\/\n" +
                    "       /  /:/     /  /:/~~~~  \\  \\:\\_\\:\\        \\__\\::/   \\  \\:\\ \\:\\    \\  \\:\\   \\__\\/  \\:\\_\\/  \\  \\::/      \\  \\:\\ \\:\\  \n" +
                    "      /  /:/     /__/:/        \\  \\:\\/:/        /  /:/     \\  \\:\\_\\/     \\  \\:\\       \\  \\:\\     \\  \\:\\       \\  \\:\\_\\/  \n" +
                    "     /__/:/      \\__\\/          \\  \\::/        /__/:/       \\  \\:\\        \\  \\:\\       \\__\\/      \\__\\/        \\  \\:\\    \n" +
                    "     \\__\\/                       \\__\\/         \\__\\/         \\__\\/         \\__\\/                                \\__\\/    \n",
            "\n" +
                    " __  __    _  _    ___    _                 _        __     _            \n" +
                    "|  \\/  |  | || |  / __|  | |_      ___     | |      / _|   (_)     ___   \n" +
                    "| |\\/| |   \\_, |  \\__ \\  | ' \\    / -_)    | |     |  _|   | |    / -_)  \n" +
                    "|_|__|_|  _|__/   |___/  |_||_|   \\___|   _|_|_   _|_|_   _|_|_   \\___|  \n" +
                    "_|\"\"\"\"\"|_| \"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"| \n" +
                    "\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-' \n",
            "\n" +
                    "$$\\      $$\\            $$$$$$\\  $$\\                 $$\\  $$$$$$\\  $$\\           \n" +
                    "$$$\\    $$$ |          $$  __$$\\ $$ |                $$ |$$  __$$\\ \\__|          \n" +
                    "$$$$\\  $$$$ |$$\\   $$\\ $$ /  \\__|$$$$$$$\\   $$$$$$\\  $$ |$$ /  \\__|$$\\  $$$$$$\\  \n" +
                    "$$\\$$\\$$ $$ |$$ |  $$ |\\$$$$$$\\  $$  __$$\\ $$  __$$\\ $$ |$$$$\\     $$ |$$  __$$\\ \n" +
                    "$$ \\$$$  $$ |$$ |  $$ | \\____$$\\ $$ |  $$ |$$$$$$$$ |$$ |$$  _|    $$ |$$$$$$$$ |\n" +
                    "$$ |\\$  /$$ |$$ |  $$ |$$\\   $$ |$$ |  $$ |$$   ____|$$ |$$ |      $$ |$$   ____|\n" +
                    "$$ | \\_/ $$ |\\$$$$$$$ |\\$$$$$$  |$$ |  $$ |\\$$$$$$$\\ $$ |$$ |      $$ |\\$$$$$$$\\ \n" +
                    "\\__|     \\__| \\____$$ | \\______/ \\__|  \\__| \\_______|\\__|\\__|      \\__| \\_______|\n" +
                    "             $$\\   $$ |                                                          \n" +
                    "             \\$$$$$$  |                                                          \n" +
                    "              \\______/                                                           \n",
            "\n" +
                    " .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------. \n" +
                    "| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |\n" +
                    "| | ____    ____ | || |  ____  ____  | || |    _______   | || |  ____  ____  | || |  _________   | || |   _____      | || |  _________   | || |     _____    | || |  _________   | |\n" +
                    "| ||_   \\  /   _|| || | |_  _||_  _| | || |   /  ___  |  | || | |_   ||   _| | || | |_   ___  |  | || |  |_   _|     | || | |_   ___  |  | || |    |_   _|   | || | |_   ___  |  | |\n" +
                    "| |  |   \\/   |  | || |   \\ \\  / /   | || |  |  (__ \\_|  | || |   | |__| |   | || |   | |_  \\_|  | || |    | |       | || |   | |_  \\_|  | || |      | |     | || |   | |_  \\_|  | |\n" +
                    "| |  | |\\  /| |  | || |    \\ \\/ /    | || |   '.___`-.   | || |   |  __  |   | || |   |  _|  _   | || |    | |   _   | || |   |  _|      | || |      | |     | || |   |  _|  _   | |\n" +
                    "| | _| |_\\/_| |_ | || |    _|  |_    | || |  |`\\____) |  | || |  _| |  | |_  | || |  _| |___/ |  | || |   _| |__/ |  | || |  _| |_       | || |     _| |_    | || |  _| |___/ |  | |\n" +
                    "| ||_____||_____|| || |   |______|   | || |  |_______.'  | || | |____||____| | || | |_________|  | || |  |________|  | || | |_____|      | || |    |_____|   | || | |_________|  | |\n" +
                    "| |              | || |              | || |              | || |              | || |              | || |              | || |              | || |              | || |              | |\n" +
                    "| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |\n" +
                    " '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------' \n",
            "\n" +
                    "                                                                                                                                                                           \n" +
                    "                                                                                                                                                                           \n" +
                    "MMMMMMMM               MMMMMMMM                            SSSSSSSSSSSSSSS hhhhhhh                                lllllll    ffffffffffffffff    iiii                      \n" +
                    "M:::::::M             M:::::::M                          SS:::::::::::::::Sh:::::h                                l:::::l   f::::::::::::::::f  i::::i                     \n" +
                    "M::::::::M           M::::::::M                         S:::::SSSSSS::::::Sh:::::h                                l:::::l  f::::::::::::::::::f  iiii                      \n" +
                    "M:::::::::M         M:::::::::M                         S:::::S     SSSSSSSh:::::h                                l:::::l  f::::::fffffff:::::f                            \n" +
                    "M::::::::::M       M::::::::::Myyyyyyy           yyyyyyyS:::::S             h::::h hhhhh           eeeeeeeeeeee    l::::l  f:::::f       ffffffiiiiiii     eeeeeeeeeeee    \n" +
                    "M:::::::::::M     M:::::::::::M y:::::y         y:::::y S:::::S             h::::hh:::::hhh      ee::::::::::::ee  l::::l  f:::::f             i:::::i   ee::::::::::::ee  \n" +
                    "M:::::::M::::M   M::::M:::::::M  y:::::y       y:::::y   S::::SSSS          h::::::::::::::hh   e::::::eeeee:::::eel::::l f:::::::ffffff        i::::i  e::::::eeeee:::::ee\n" +
                    "M::::::M M::::M M::::M M::::::M   y:::::y     y:::::y     SS::::::SSSSS     h:::::::hhh::::::h e::::::e     e:::::el::::l f::::::::::::f        i::::i e::::::e     e:::::e\n" +
                    "M::::::M  M::::M::::M  M::::::M    y:::::y   y:::::y        SSS::::::::SS   h::::::h   h::::::he:::::::eeeee::::::el::::l f::::::::::::f        i::::i e:::::::eeeee::::::e\n" +
                    "M::::::M   M:::::::M   M::::::M     y:::::y y:::::y            SSSSSS::::S  h:::::h     h:::::he:::::::::::::::::e l::::l f:::::::ffffff        i::::i e:::::::::::::::::e \n" +
                    "M::::::M    M:::::M    M::::::M      y:::::y:::::y                  S:::::S h:::::h     h:::::he::::::eeeeeeeeeee  l::::l  f:::::f              i::::i e::::::eeeeeeeeeee  \n" +
                    "M::::::M     MMMMM     M::::::M       y:::::::::y                   S:::::S h:::::h     h:::::he:::::::e           l::::l  f:::::f              i::::i e:::::::e           \n" +
                    "M::::::M               M::::::M        y:::::::y        SSSSSSS     S:::::S h:::::h     h:::::he::::::::e         l::::::lf:::::::f            i::::::ie::::::::e          \n" +
                    "M::::::M               M::::::M         y:::::y         S::::::SSSSSS:::::S h:::::h     h:::::h e::::::::eeeeeeee l::::::lf:::::::f            i::::::i e::::::::eeeeeeee  \n" +
                    "M::::::M               M::::::M        y:::::y          S:::::::::::::::SS  h:::::h     h:::::h  ee:::::::::::::e l::::::lf:::::::f            i::::::i  ee:::::::::::::e  \n" +
                    "MMMMMMMM               MMMMMMMM       y:::::y            SSSSSSSSSSSSSSS    hhhhhhh     hhhhhhh    eeeeeeeeeeeeee llllllllfffffffff            iiiiiiii    eeeeeeeeeeeeee  \n" +
                    "                                     y:::::y                                                                                                                               \n" +
                    "                                    y:::::y                                                                                                                                \n" +
                    "                                   y:::::y                                                                                                                                 \n" +
                    "                                  y:::::y                                                                                                                                  \n" +
                    "                                 yyyyyyy                                                                                                                                   \n" +
                    "                                                                                                                                                                           \n" +
                    "                                                                                                                                                                           \n",
            "\n" +
                    " __       __             ______   __                  __   ______   __           \n" +
                    "|  \\     /  \\           /      \\ |  \\                |  \\ /      \\ |  \\          \n" +
                    "| $$\\   /  $$ __    __ |  $$$$$$\\| $$____    ______  | $$|  $$$$$$\\ \\$$  ______  \n" +
                    "| $$$\\ /  $$$|  \\  |  \\| $$___\\$$| $$    \\  /      \\ | $$| $$_  \\$$|  \\ /      \\ \n" +
                    "| $$$$\\  $$$$| $$  | $$ \\$$    \\ | $$$$$$$\\|  $$$$$$\\| $$| $$ \\    | $$|  $$$$$$\\\n" +
                    "| $$\\$$ $$ $$| $$  | $$ _\\$$$$$$\\| $$  | $$| $$    $$| $$| $$$$    | $$| $$    $$\n" +
                    "| $$ \\$$$| $$| $$__/ $$|  \\__| $$| $$  | $$| $$$$$$$$| $$| $$      | $$| $$$$$$$$\n" +
                    "| $$  \\$ | $$ \\$$    $$ \\$$    $$| $$  | $$ \\$$     \\| $$| $$      | $$ \\$$     \\\n" +
                    " \\$$      \\$$ _\\$$$$$$$  \\$$$$$$  \\$$   \\$$  \\$$$$$$$ \\$$ \\$$       \\$$  \\$$$$$$$\n" +
                    "             |  \\__| $$                                                          \n" +
                    "              \\$$    $$                                                          \n" +
                    "               \\$$$$$$                                                           \n",
            "\n" +
                    "                                                                                               \n" +
                    "          ____                                                                                 \n" +
                    "        ,'  , `.           .--.--.     ,---,                ,--,                               \n" +
                    "     ,-+-,.' _ |          /  /    '. ,--.' |              ,--.'|     .--.,   ,--,              \n" +
                    "  ,-+-. ;   , ||         |  :  /`. / |  |  :              |  | :   ,--.'  \\,--.'|              \n" +
                    " ,--.'|'   |  ;|         ;  |  |--`  :  :  :              :  : '   |  | /\\/|  |,               \n" +
                    "|   |  ,', |  ':     .--,|  :  ;_    :  |  |,--.   ,---.  |  ' |   :  : :  `--'_       ,---.   \n" +
                    "|   | /  | |  ||   /_ ./| \\  \\    `. |  :  '   |  /     \\ '  | |   :  | |-,,' ,'|     /     \\  \n" +
                    "'   | :  | :  |,, ' , ' :  `----.   \\|  |   /' : /    /  ||  | :   |  : :/|'  | |    /    /  | \n" +
                    ";   . |  ; |--'/___/ \\: |  __ \\  \\  |'  :  | | |.    ' / |'  : |__ |  |  .'|  | :   .    ' / | \n" +
                    "|   : |  | ,    .  \\  ' | /  /`--'  /|  |  ' | :'   ;   /||  | '.'|'  : '  '  : |__ '   ;   /| \n" +
                    "|   : '  |/      \\  ;   :'--'.     / |  :  :_:,''   |  / |;  :    ;|  | |  |  | '.'|'   |  / | \n" +
                    ";   | |`-'        \\  \\  ;  `--'---'  |  | ,'    |   :    ||  ,   / |  : \\  ;  :    ;|   :    | \n" +
                    "|   ;/             :  \\  \\           `--''       \\   \\  /  ---`-'  |  |,'  |  ,   /  \\   \\  /  \n" +
                    "'---'               \\  ' ;                        `----'           `--'     ---`-'    `----'   \n" +
                    "                     `--`                                                                      \n",
            """
                 _   .-')                        .-')    ('-. .-.   ('-.                                ('-.  \s
                ( '.( OO )_                     ( OO ). ( OO )  / _(  OO)                             _(  OO) \s
                 ,--.   ,--.) ,--.   ,--.      (_)---\\_),--. ,--.(,------.,--.        ,------.,-.-') (,------.\s
                 |   `.'   |   \\  `.'  /       /    _ | |  | |  | |  .---'|  |.-') ('-| _.---'|  |OO) |  .---'\s
                 |         | .-')     /        \\  :` `. |   .|  | |  |    |  | OO )(OO|(_\\    |  |  \\ |  |    \s
                 |  |'.'|  |(OO  \\   /          '..`''.)|       |(|  '--. |  |`-' |/  |  '--. |  |(_/(|  '--. \s
                 |  |   |  | |   /  /\\_        .-._)   \\|  .-.  | |  .--'(|  '---.'\\_)|  .--',|  |_.' |  .--' \s
                 |  |   |  | `-./  /.__)       \\       /|  | |  | |  `---.|      |   \\|  |_)(_|  |    |  `---.\s
                 `--'   `--'   `--'             `-----' `--' `--' `------'`------'    `--'    `--'    `------'\s\n""",
            """
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
                ,8'         `         `8.`8888. 8 8888                 `Y8888P ,88P' 8 8888        8 8 888888888888 8 888888888888 8 8888          8 8888 8 888888888888\s\n""",
            """
                ___________________________________________________________________________________
                    _   _    _     _        __     _     _   _____    _      _____      __   _____\s
                    /  /|    |    /       /    )   /    /    /    '   /      /    '     /    /    '
                ---/| /-|----|---/--------\\-------/___ /----/__------/------/__--------/----/__----
                  / |/  |    |  /          \\     /    /    /        /      /          /    /      \s
                _/__/___|____|_/_______(____/___/____/____/____ ___/____/_/________ _/_ __/____ ___
                              /                                                                   \s
                          (_ /                                                                    \s\n"""

    );


    private static String randomizeColor(String s) {
        int r = new Random().nextInt(0, 8);

        return switch (r) {
            case 0 -> Chalk.on(s).red().toString();
            case 1 -> Chalk.on(s).magenta().toString();
            case 2 -> Chalk.on(s).blue().toString();
            case 3 -> Chalk.on(s).cyan().toString();
            case 4 -> Chalk.on(s).gray().toString();
            case 5 -> Chalk.on(s).green().toString();
            case 6 -> Chalk.on(s).yellow().toString();
            case 7 -> Chalk.on(s).white().toString();
            default -> throw new IllegalStateException("Unexpected value: " + r);
        };
    }


    public static void print() {
        String out = CollectionUtils.extractRandomElement(screens);
        String colored = randomizeColor(out);

        Console.out(colored);
    }


}
