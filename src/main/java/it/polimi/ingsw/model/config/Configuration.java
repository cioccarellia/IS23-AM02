package it.polimi.ingsw.model.config;

/**
 * Abstract class describing a configuration for a specific element in the game model.
 * This class contains the logic for extracting the correct specifics for the
 * current model object, and returns them.
 *
 * @implNote {@link Specifics} is a marker interface.
 */
abstract public class Configuration<T extends Specifics> {
    /**
     * Produces (deserializes) an object implementing {@link Specifics},
     * meant to represent the raw form of the model specifics.
     * This can be mapped to the actual parameters in appropriate class methods.
     */
    protected abstract T provideSpecs();

    /**
     * Defines the path of the file containing the serialized data used to build the configuration.
     */
    protected abstract String provideResourcePath();
}
