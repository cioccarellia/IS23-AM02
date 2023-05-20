package it.polimi.ingsw.app.client.keepalive;

import it.polimi.ingsw.controller.client.gateways.ClientGateway;
import org.jetbrains.annotations.NotNull;

import java.rmi.RemoteException;

public class KeepAliveDaemon implements Runnable {

    private final ClientGateway gateway;
    private final String username;

    private boolean isActive = true;

    public KeepAliveDaemon(@NotNull ClientGateway gateway, String username) {
        this.gateway = gateway;
        this.username = username;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public void run() {
        while (isActive) {
            try {
                Thread.sleep(5_000);

                gateway.keepAlive(username);
            } catch (InterruptedException | RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
