package it.polimi.ingsw.ui.game.gui;

public enum GuiErrors {
    SELECTION_ERROR_WRONG_TILE("Selection error: You can't select this tile"),
    SELECTION_ERROR_AT_LEAST_ONE("Selection error: You have to select at least one tile"),
    SELECTION_ERROR_TOO_MANY_TILES("Selection error: You selected too many tiles"),
    SELECTION_ERROR_INVALID("Selection error: invalid selection"),
    INSERTION_ERROR_SELECT_ALL_TILES("Insertion error: You have to select all tiles"),
    INSERTION_ERROR_SELECT_COLUMN("Insertion error: You have to select a column"),
    STANDBY_ERROR_NO_SEND_CHAT("Game in standby: you can't send messages"),
    STANDBY_ERROR_NO_SELECTION("Game in standby: you can't select tiles"),
    STANDBY_ERROR_NO_INSERTION("Game in standby: you can't insert tiles");

    private final String errorMessage;

    GuiErrors(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String errorText(){
        return errorMessage;
    }
}
