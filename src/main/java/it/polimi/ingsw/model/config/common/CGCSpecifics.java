package it.polimi.ingsw.model.config.common;

import it.polimi.ingsw.model.config.Specifics;

public record CGCSpecifics(
        int[] activeCardIds
) implements Specifics {}