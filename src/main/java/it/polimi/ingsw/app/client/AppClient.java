package it.polimi.ingsw.app.client;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.gateways.ClientGateway;
import it.polimi.ingsw.launcher.parameters.ClientExhaustiveConfiguration;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Launches a client instance
 */
public class AppClient {

    private static final Logger logger = LoggerFactory.getLogger(AppClient.class);
    private final ClientGateway gateway;
    private ClientController controller; // = new ClientController();

    public AppClient(@NotNull ClientExhaustiveConfiguration config, String serverHost, int serverPort) {
        logger.info("Starting AppClient, config={}, serverHost={}", config, serverHost);

        ClientProtocol proto = config.protocol();
        gateway = ClientGatewayFactory.create(proto, serverHost, serverPort);
    }

    public ClientController getController() {
        return controller;
    }

    public ClientGateway getClientGateway() {
        return gateway;
    }
}