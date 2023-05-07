package it.polimi.ingsw.controller.server.connection;

/**
 * Defines the status of the client's connection
 */
public enum ConnectionStatus {
    /**
     * The client is currently connected and playing normally
     */
    OPEN,

    /**
     * The client's connection is temporarily broken (e.g. no answering TTLs)
     */
    DISCONNECTED,

    /**
     * The client has closed the connection
     */
    CLOSED
}