package it.polimi.ingsw.network.tcp.messages;

import java.io.Serializable;

/**
 * Generic TCP-bound message (either from client to server or from server to client)
 */
public abstract class Message implements Serializable {
    /**
     * Used for deserialization class lookup (for the {@code Message} class hierarchy)
     */
    @SuppressWarnings("unused")
    protected final String CLASS_META_FIELD = getClass().getName();
}