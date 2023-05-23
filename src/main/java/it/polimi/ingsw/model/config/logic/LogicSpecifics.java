package it.polimi.ingsw.model.config.logic;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.model.config.Specifics;

public record LogicSpecifics(
        @SerializedName("common_goal_cards")
        CommonGoalCardLogic commonGoalCardLogic,

        @SerializedName("selection_max_size")
        int maxSelectionSize,

        @SerializedName("tile_types_amount")
        int tileTypesAmount,

        @SerializedName("max_tiles_per_type")
        int maxTilesPerType
) implements Specifics {
}