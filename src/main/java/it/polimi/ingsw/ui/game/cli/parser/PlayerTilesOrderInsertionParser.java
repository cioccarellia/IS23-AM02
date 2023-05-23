package it.polimi.ingsw.ui.game.cli.parser;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.ui.game.cli.Console;
import it.polimi.ingsw.ui.game.cli.printer.TilePrinter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.board.Tile.*;

public class PlayerTilesOrderInsertionParser {

    final private static List<String> acceptableCharacters = List.
            of("B", "C", "G", "P", "T", "F", "b", "c", "g", "p", "t", "f");

    /**
     * @param s tile character that needs checking
     * @return if the player selected a tile within the given tile characters
     */
    private static boolean isStringValid(@NotNull String s) {
        boolean isSizeCorrect = s.length() == 1;
        boolean isCharacterAcceptable = acceptableCharacters.contains(s);

        return isSizeCorrect && isCharacterAcceptable;
    }

    /**
     * @param tiles the tiles the player selected, not yet ordered
     * @return returns the ordered tiles
     */
    public static List<Tile> scan(List<Tile> tiles) {
        while (true) {
            Console.printnl();
            Console.out("You have selected:");
            // printing tiles and prompting the user to sort them
            tiles.forEach(tile -> {
                String tileText = TilePrinter.print(tile);
                Console.out(" " + tileText);
            });

            Console.printnl();
            Console.out(">Give me your insertion order (the first one is the first to go in the bookshelf).");
            Console.printnl();
            Console.out("Format: Tile1, Tile2...");
            Console.printnl();

            String input = Console.in();

            // checking that the given text matches the necessary requirements
            String[] tokens = input.split(",");

            if (tokens.length != tiles.size()) {
                Console.printnl();
                Console.out("Not valid, you need to order the selected tiles from before.");
                Console.printnl();
                continue;
            }

            for (int i = 0; i < tokens.length; i++) {
                tokens[i] = tokens[i].trim();
            }

            List<Tile> orderedTiles = new ArrayList<>();
            for (String token : tokens) {
                if (isStringValid(token)) {
                    switch (token) {
                        case "B", "b" -> orderedTiles.add(BOOK);
                        case "C", "c" -> orderedTiles.add(CAT);
                        case "G", "g" -> orderedTiles.add(GAME);
                        case "P", "p" -> orderedTiles.add(PLANT);
                        case "T", "t" -> orderedTiles.add(TROPHY);
                        case "F", "f" -> orderedTiles.add(FRAME);
                    }
                } else {
                    Console.out("You need to write at least one tile.");
                    Console.printnl();
                }
            }
            if (orderedTiles.containsAll(tiles) && tiles.containsAll(orderedTiles)) {
                return orderedTiles;
            } else {
                Console.printnl();
                Console.out("Not valid, you need to order the selected tiles from before.");
                Console.printnl();
            }
        }
    }

    public static void main(String[] args) {
        scan(List.of(GAME, FRAME, CAT));
    }

}
