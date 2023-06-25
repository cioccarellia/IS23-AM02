package it.polimi.ingsw.app.client.keepalive;

import it.polimi.ingsw.app.server.flags.ServerFlags;
import it.polimi.ingsw.controller.client.gateways.ClientGateway;
import org.jetbrains.annotations.NotNull;

import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

/**
 * Daemon thread responsible for sending keep-alive signals to the server at regular intervals.
 */
public class KeepAliveDaemon implements Runnable {

    private final ClientGateway gateway;
    private final String username;

    private boolean isActive = true;

    /**
     * Constructs a KeepAliveDaemon with the specified ClientGateway and username.
     *
     * @param gateway  The ClientGateway used for sending keep-alive signals.
     * @param username The username associated with the client.
     */
    public KeepAliveDaemon(@NotNull ClientGateway gateway, String username) {
        this.gateway = gateway;
        this.username = username;
    }

    /**
     * Checks if the KeepAliveDaemon is active.
     *
     * @return true if the daemon is active, false otherwise.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets the activity status of the KeepAliveDaemon.
     *
     * @param active true to activate the daemon, false to deactivate it.
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public void run() {
        if (!ServerFlags.ENABLE_TTLS) {
            return;
        }

        while (isActive) {
            try {
                TimeUnit.SECONDS.sleep(5);
                gateway.keepAlive(username);
            } catch (InterruptedException | RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
