package it.polimi.ingsw.app.client;

import it.polimi.ingsw.app.client.layers.network.ClientNetworkLayer;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.gateways.ClientGateway;
import it.polimi.ingsw.launcher.parameters.ClientExhaustiveConfiguration;
import it.polimi.ingsw.ui.game.cli.splash.SplashScreen;
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

    /**
     * Initial configuration the client has been launched in. Retains all information about protocol and UI.
     */
    private final ClientExhaustiveConfiguration config;

    /**
     * Server connection parameters
     */
    private final String serverHost;
    private final int serverPort;

    /**
     * Client gateway, to communicate with the server on a protocol-independent basis
     */
    private final ClientGateway gateway;

    /**
     * Client controller for handling network callbacks, server interaction and view management
     */
    private final ClientController controller;

    /**
     * Client-side thread pool executor
     */
    public final static ExecutorService clientExecutorService = Executors.newCachedThreadPool();


    public AppClient(@NotNull ClientExhaustiveConfiguration config, String serverHost, int serverPort) {
        logger.info("Instantiating AppClient, config={}, serverHost={}", config, serverHost);

        this.config = config;
        this.serverHost = serverHost;
        this.serverPort = serverPort;

        // creates the gateway (to communicate with server) and the controller (which uses the gateway)
        this.gateway = ClientGatewayFactory.create(config.protocol(), serverHost, serverPort);

        try {
            this.controller = new ClientController(gateway, config);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        // injects the current controller inside the network reference for async callbacks
        this.gateway.linkController(controller);

        SplashScreen.print();

        initializeClientThreads();
    }

    private void initializeClientThreads() {
        ClientNetworkLayer.scheduleReceiverExecutionThread(gateway, clientExecutorService);
    }

    /**
     * When the client is initialized and ran on its own thread
     */
    @Override
    public void run() {
        controller.initialize();
    }
}