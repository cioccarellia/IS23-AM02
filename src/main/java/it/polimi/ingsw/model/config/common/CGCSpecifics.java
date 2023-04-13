package it.polimi.ingsw.model.config.common;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.model.config.Specifics;

public record CGCSpecifics(
        @SerializedName("active_card_ids")
        int[] activeCardIds
) implements Specifics {}