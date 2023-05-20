package it.polimi.ingsw.model.game.extractors;

import java.util.List;

public abstract class ElementExtractor<E> {

    abstract public E extract();

    abstract public List<E> extractAmount(int amount);
}
