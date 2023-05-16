package it.polimi.ingsw.net.tcp.messages;

/**
 * Generic TCP-bound message (either from client to server or from server to client)
 */
public abstract class Message {
    /**
     * Used for deserialization class lookup (for the {@code Message} class hierarchy)
     */
    protected final String CLASS_META_FIELD = getClass().getName();
}