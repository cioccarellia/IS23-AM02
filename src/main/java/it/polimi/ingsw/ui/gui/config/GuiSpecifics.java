package it.polimi.ingsw.ui.gui.config;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.model.config.Specifics;

public record GuiSpecifics(
        @SerializedName("first")
        int first,
        @SerializedName("second")
        int second,
        @SerializedName("third")
        int third,
        @SerializedName("fourth")
        int fourth
) implements Specifics {
}
