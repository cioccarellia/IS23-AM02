package it.polimi.ingsw.model.config.logic;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.model.config.Specifics;

public record LogicSpecifics(
        @SerializedName("commonGoalCards")
        CommonGoalCardLogic commonGoalCardLogic
) implements Specifics
{
}

