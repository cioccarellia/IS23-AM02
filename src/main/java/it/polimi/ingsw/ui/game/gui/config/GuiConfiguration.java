package it.polimi.ingsw.ui.game.gui.config;

import it.polimi.ingsw.model.config.Configuration;
import it.polimi.ingsw.utils.resources.ResourceReader;


public class GuiConfiguration extends Configuration<GuiSpecifics> {
    // used for singleton pattern
    private static GuiConfiguration instance;

    // deserializes and stores the game specifics (from json)
    private final GuiSpecifics specs = ResourceReader.readAndDeserialize(provideResourcePath(), GuiSpecifics.class);

    public static GuiConfiguration getInstance() {
        if (instance == null) {
            instance = new GuiConfiguration();
        }

        return instance;
    }

    public int getFirst() {
        return specs.first();
    }

    public int getSecond() {
        return specs.second();
    }

    public int getThird() {
        return specs.third();
    }

    public int getFourth() {
        return specs.fourth();
    }

    public int getFifth() {
        return specs.fifth();
    }


    @Override
    protected GuiSpecifics provideSpecs() {
        return specs;
    }

    @Override
    protected String provideResourcePath() {
        return "config/gui/ranking.json";
    }

}
