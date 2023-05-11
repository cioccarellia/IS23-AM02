package it.polimi.ingsw.ui.cli.parser;

import it.polimi.ingsw.model.board.Tile;
import it.polimi.ingsw.ui.cli.Console;
import it.polimi.ingsw.ui.cli.printer.TilePrinter;
import org.jetbrains.annotations.NotNull;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static it.polimi.ingsw.model.board.Tile.*;

public class PlayerTilesOrderInsertionParser {

    final private static List<String> acceptableCharacters = List.of("B", "C", "G", "P", "T", "F");

    private static boolean isStringValid(@NotNull String s) {
        boolean isSizeCorrect = s.length() == 1;
        boolean isCharacterAcceptable = acceptableCharacters.contains(s);

        return isSizeCorrect && isCharacterAcceptable;
    }

    public static List<Tile> scan(Set<Tile> tiles) {
        while (true) {
            Console.out("You have selected:");
            // printing tiles and prompting the user to sort them
            tiles.forEach(tile -> {
                String tileText = TilePrinter.print(tile);
                Console.out(" " + tileText);
            });

            Console.out("\n> ");
            Console.out("Give me your insertion order (the first one is the first to go in the bookshelf).");

            String input = Console.in(); // A,C,B

            // check that the

            // checking that the given text matches the necessary
            String[] tokens = input.split(",");

            if (tokens.length != tiles.size()) {
                Console.out("Not valid, you need to order the selected tiles from before.");
                continue;
            }

            for (int i = 0; i < tokens.length; i++) {
                tokens[i] = tokens[i].trim();
            }
            List<Tile> orderedTiles = new ArrayList<>();
            for (int i = 0; i < tokens.length; i++) {
                if (isStringValid(tokens[i])) {
                    switch (tokens[i]) {
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
                Console.out("Thank you for ordering your tiles.")
                return orderedTiles;
            } else {
                Console.out("Not valid, you need to order the selected tiles from before.");
                continue;
            }
        }
    }

    public static void main(String[] args) {
        scan(Set.of(Tile.GAME, Tile.FRAME, Tile.CAT));
    }

}
