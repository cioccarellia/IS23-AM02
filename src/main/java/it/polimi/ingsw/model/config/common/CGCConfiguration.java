package it.polimi.ingsw.model.config.common;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.cards.common.CommonGoalCardIdentifier;
import it.polimi.ingsw.model.config.Configuration;
import it.polimi.ingsw.utils.resources.ResourceReader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Manages configuration parameters for {@link Board}, according to
 * the matching specification {@link it.polimi.ingsw.model.config.common.CGCSpecifics}.
 * The parameters are dimension and cell pattern matrix.
 */
public class CGCConfiguration extends Configuration<CGCSpecifics> {

    // used for singleton pattern
    private static CGCConfiguration instance;

    // deserializes and stores the game specifics (from json)
    private final CGCSpecifics specs = ResourceReader.readAndDeserialize(provideResourcePath(), CGCSpecifics.class);

    public static CGCConfiguration getInstance() {
        if (instance == null) {
            instance = new CGCConfiguration();
        }

        return instance;
    }

    public List<CommonGoalCardIdentifier> getActiveCommonGoalCardIds() {
        var activeIds = IntStream.of(specs.activeCardIds()).boxed().toList();

        return Arrays.stream(CommonGoalCardIdentifier.values()).filter(cgcId -> {
            int cardSerializedId = cgcId.ordinal() + 1; // ordinal is 0-indexed

            return activeIds.contains(cardSerializedId);
        }).toList();
    }

    @Override
    protected CGCSpecifics provideSpecs() {
        return specs;
    }

    @Override
    protected String provideResourcePath() {
        return "board/board.json";
    }
}
