package it.polimi.ingsw.ui.cli.parser;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.ui.cli.Console;
import it.polimi.ingsw.ui.cli.printer.TilePrinter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.board.Tile.*;

public class PlayerTilesOrderInsertionParser {

    final private static List<String> acceptableCharacters = List.of("B", "C", "G", "P", "T", "F");

    private static boolean isStringValid(@NotNull String s) {
        boolean isSizeCorrect = s.length() == 1;
        boolean isCharacterAcceptable = acceptableCharacters.contains(s);

        return isSizeCorrect && isCharacterAcceptable;
    }

    public static List<Tile> scan(List<Tile> tiles) {
        while (true) {
            Console.out("\nYou have selected: \n");
            // printing tiles and prompting the user to sort them
            tiles.forEach(tile -> {
                String tileText = TilePrinter.print(tile);
                Console.out(" " + tileText);
            });

            Console.out("\n> ");
            Console.out("Give me your insertion order (the first one is the first to go in the bookshelf).\n");
            Console.out("Format: Tile1, Tile2...\n");
            Console.flush();


            String input = Console.in();

            // checking that the given text matches the necessary requirements
            String[] tokens = input.split(",");

            if (tokens.length != tiles.size()) {
                Console.out("\nNot valid, you need to order the selected tiles from before.\n");
                Console.flush();
                continue;
            }

            for (int i = 0; i < tokens.length; i++) {
                tokens[i] = tokens[i].trim();
            }

            List<Tile> orderedTiles = new ArrayList<>();
            for (String token : tokens) {
                if (isStringValid(token)) {
                    switch (token) {
                        case "B" -> orderedTiles.add(BOOK);
                        case "C" -> orderedTiles.add(CAT);
                        case "G" -> orderedTiles.add(GAME);
                        case "P" -> orderedTiles.add(PLANT);
                        case "T" -> orderedTiles.add(TROPHY);
                        case "F" -> orderedTiles.add(FRAME);
                    }
                }
            }
            if (orderedTiles.containsAll(tiles) && tiles.containsAll(orderedTiles)) {
                return orderedTiles;
            } else {
                Console.out("\nNot valid, you need to order the selected tiles from before.\n ");
            }
        }
    }

    public static void main(String[] args) {
        scan(List.of(Tile.GAME, Tile.FRAME, Tile.CAT));
    }

}
