package it.polimi.ingsw.model.config.logic;

import it.polimi.ingsw.model.config.Configuration;
import it.polimi.ingsw.utils.resources.ResourceReader;

/**
 * Manages configuration parameters for the {@link it.polimi.ingsw.model.game.Game} logic, according to
 * the matching specification {@link LogicSpecifics}.
 * The parameters are the amount of common goal cards.
 */
public class LogicConfiguration extends Configuration<LogicSpecifics> {

    // used for singleton pattern
    private static LogicConfiguration instance;

    // deserializes and stores the game specifics (from json)
    private final LogicSpecifics specs = ResourceReader.readAndDeserialize(provideResourcePath(), LogicSpecifics.class);

    public static LogicConfiguration getInstance() {
        if (instance == null) {
            instance = new LogicConfiguration();
        }

        return instance;
    }

    public int maxSelectionSize() {
        return specs.maxSelectionSize();
    }

    public int commonGoalCardAmount() {
        assert specs.commonGoalCardLogic().amount() > 0;
        return specs.commonGoalCardLogic().amount();
    }

    @Override
    protected LogicSpecifics provideSpecs() {
        return specs;
    }

    @Override
    protected String provideResourcePath() {
        return "config/game/logic.json";
    }
}
