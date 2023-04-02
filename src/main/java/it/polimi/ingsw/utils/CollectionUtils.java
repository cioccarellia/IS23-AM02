package it.polimi.ingsw.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class CollectionUtils {

    static public <E> E extractRandomElement(final @NotNull Collection<E> collection) {
        // get random index
        int randomIndex = new Random().nextInt(collection.size());

        // extract and return the random element
        return collection.stream().toList().get(randomIndex);
    }

    static public <E> E extractAndRemoveRandomElement(final @NotNull List<E> collection) {
        // get random index
        int randomIndex = new Random().nextInt(collection.size());

        // extract and save to a temporary variable the random element
        E savedElement = collection.get(randomIndex);

        // remove the element from the original collection
        collection.remove(randomIndex);

        // return the removed element
        return savedElement;
    }

}
