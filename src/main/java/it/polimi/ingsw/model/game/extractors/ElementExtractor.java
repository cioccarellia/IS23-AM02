package it.polimi.ingsw.model.game.extractors;

import java.io.Serializable;
import java.util.List;

public abstract class ElementExtractor<E> implements Serializable {

    abstract public E extract();

    abstract public List<E> extractAmount(int amount);
}
