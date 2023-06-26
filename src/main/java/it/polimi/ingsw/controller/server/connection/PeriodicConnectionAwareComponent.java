package it.polimi.ingsw.controller.server.connection;

import it.polimi.ingsw.app.server.ClientConnectionsManager;

/**
 * Makes a controller aware of the client connection lifecycle, and
 * able to be notified on connection changes event.
 * In this context:
 * <ul>
 * <li><b>disconnection</b> means not sending any message for an amount of time</li>
 * <li><b>closing</b> means taking the connection down</li>
 * </ul>
 * The event notifications should be sent once and represent the full
 * spectrum of detected changes for one interval. i.e. two users out of
 * four disconnect, but only one call is made
 */
public interface PeriodicConnectionAwareComponent {

    ClientConnectionsManager getConnectionsManager();

    void onConnectionChange();
}