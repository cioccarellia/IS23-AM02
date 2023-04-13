package it.polimi.ingsw.model.config.logic;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.model.config.Specifics;

public record LogicSpecifics(
        @SerializedName("common_goal_cards")
        CommonGoalCardLogic commonGoalCardLogic,

        @SerializedName("selection_max_size")
        int maxSelectionSize
) implements Specifics
{
}