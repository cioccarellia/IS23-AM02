package it.polimi.ingsw.ui.game.gui.utils;

import it.polimi.ingsw.model.board.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The StringExtractor class is responsible for extracting images for different tile types.
 * It provides a static method to extract an image based on the given tile type.
 */
public class StringExtractor {
    private static final List<String> state = new ArrayList<>(GuiResources.tilesDomain);

    /**
     * Extracts an image for the specified tile type.
     *
     * @param tileType the tile type
     * @return the extracted url
     * @throws IllegalStateException if the tile type is unexpected
     */
    public static String extract(@NotNull Tile tileType) {
        switch (tileType) {
            case BOOK -> {
                return state.get(new Random().ints(0, 3).findAny().getAsInt());
            }
            case CAT -> {
                return state.get(new Random().ints(3, 6).findAny().getAsInt());
            }
            case FRAME -> {
                return state.get(new Random().ints(6, 9).findAny().getAsInt());
            }
            case GAME -> {
                return state.get(new Random().ints(9, 12).findAny().getAsInt());
            }
            case PLANT -> {
                return state.get(new Random().ints(12, 15).findAny().getAsInt());
            }
            case TROPHY -> {
                return state.get(new Random().ints(15, 18).findAny().getAsInt());
            }
            default -> throw new IllegalStateException("Unexpected value: " + tileType);
        }
    }
}