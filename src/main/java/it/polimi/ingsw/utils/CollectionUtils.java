package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.game.session.SessionManager;
import it.polimi.ingsw.model.player.PlayerSession;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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

    static public <E> E extractAndRemoveRandomElement(@NotNull List<E> list) {
        // get random index
        int randomIndex = new Random().nextInt(list.size());

        // extract and save to a temporary variable the random element
        E savedElement = list.get(randomIndex);

        // remove the element from the original collection
        list.remove(randomIndex);

        // return the removed element
        return savedElement;
    }

    static public <E> List<E> extractAndRemoveRandomElementAmount(final @NotNull List<E> list, final int amount) {
        assert amount > 0;

        List<E> extractedElements = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            extractedElements.add(
                    extractAndRemoveRandomElement(list)
            );
        }

        return extractedElements;
    }


    static public PlayerSession extractRandomElement(final @NotNull SessionManager collection) {
        // get random index
        int randomIndex = new Random().nextInt(collection.size());

        // extract and return the random element
        return collection.values().stream().toList().get(randomIndex);
    }

}