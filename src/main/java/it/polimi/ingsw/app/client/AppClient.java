package it.polimi.ingsw.app.client;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.gateways.ClientGateway;
import it.polimi.ingsw.controller.client.gateways.TcpClientGateway;
import it.polimi.ingsw.launcher.parameters.ClientExhaustiveConfiguration;
import it.polimi.ingsw.model.game.GameMode;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Launches a client instance
 */
public class AppClient implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(AppClient.class);

    private final ClientExhaustiveConfiguration config;
    private final String serverHost;
    private final int serverPort;

    private final ClientGateway gateway;
    private final ClientController controller;

    private final static ExecutorService executorService = Executors.newCachedThreadPool();


    public AppClient(@NotNull ClientExhaustiveConfiguration config, String serverHost, int serverPort) {
        logger.info("Instantiating AppClient, config={}, serverHost={}", config, serverHost);

        this.config = config;
        this.serverHost = serverHost;
        this.serverPort = serverPort;

        this.gateway = ClientGatewayFactory.create(config.protocol(), serverHost, serverPort);
        this.controller = new ClientController(gateway);

        this.gateway.linkController(controller);

        if (gateway instanceof TcpClientGateway gateway) {
            executorService.execute(gateway);
        }
    }

    @Override
    public void run() {
        try {
            gateway.gameStartRequest("cioccarellia", GameMode.GAME_MODE_3_PLAYERS, config.protocol(), controller);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    public ClientController getController() {
        return controller;
    }

    public ClientGateway getClientGateway() {
        return gateway;
    }
}