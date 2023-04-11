package it.polimi.ingsw.model.config.logic;

import com.google.gson.annotations.SerializedName;

record CommonGoalCardLogic(
        @SerializedName("amount")
        int amount
) {
}
