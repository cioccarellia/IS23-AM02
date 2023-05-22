package it.polimi.ingsw.app.client.layers.network;

import it.polimi.ingsw.app.client.keepalive.KeepAliveDaemon;
import it.polimi.ingsw.controller.client.gateways.ClientGateway;
import it.polimi.ingsw.controller.client.gateways.RmiClientGateway;
import it.polimi.ingsw.controller.client.gateways.TcpClientGateway;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;

public class ClientNetworkLayer {

    public static void scheduleReceiverExecutionThread(@NotNull ClientGateway clientGateway, @NotNull ExecutorService executorService) {
        switch (clientGateway) {
            case TcpClientGateway tcpGateway -> executorService.execute(tcpGateway);
            case RmiClientGateway rmiGateway -> {
            }
            default -> throw new IllegalStateException();
        }
    }


    private static KeepAliveDaemon daemon = null;

    public static void scheduleKeepAliveThread(String username, @NotNull ClientGateway clientGateway, @NotNull ExecutorService executorService) {
        assert daemon == null;
        daemon = new KeepAliveDaemon(clientGateway, username);
        executorService.execute(daemon);
    }
}
