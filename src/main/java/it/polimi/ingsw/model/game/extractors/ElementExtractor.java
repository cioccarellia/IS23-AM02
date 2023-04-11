package it.polimi.ingsw.model.game.extractors;

import java.util.List;

abstract class ElementExtractor<E> {

    abstract public E extract();

    abstract public List<E> extractAmount(int amount);
}
