package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.board.Tile;

import javafx.scene.image.Image;

import java.util.*;

import static it.polimi.ingsw.ui.gui.GuiResources.tilesDomain;

public class ImageExtractor {

    private static final List<Image> state = new ArrayList<>(tilesDomain);

    public Set<Image> domain() {
        return new HashSet<>(state);
    }

    public static Image extract(Tile tileType) {

        switch (tileType){
            case BOOK -> {
                return state.get(new Random().ints(0,3).findAny().getAsInt());
            }
            case CAT -> {
                return state.get(new Random().ints(3,6).findAny().getAsInt());
            }
            case FRAME -> {
                return state.get(new Random().ints(6,9).findAny().getAsInt());
            }
            case GAME -> {
                return state.get(new Random().ints(9,12).findAny().getAsInt());
            }
            case PLANT -> {
                return state.get(new Random().ints(12,15).findAny().getAsInt());
            }
            case TROPHY -> {
                return state.get(new Random().ints(15, 18).findAny().getAsInt());
            }
            default -> throw new IllegalStateException("Unexpected value: " + tileType);
        }
    }
}