package it.polimi.ingsw.app.client;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.gateways.ClientGateway;
import it.polimi.ingsw.launcher.parameters.ClientExhaustiveConfiguration;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppClient {

    private static final Logger logger = LoggerFactory.getLogger(AppClient.class);

    private ClientController controller; // = new ClientController();
    private final ClientGateway clientGateway;

    public AppClient(@NotNull ClientExhaustiveConfiguration config, String serverHost, int serverPort) {
        logger.info("Starting AppClient, config={}, serverHost={}", config, serverHost);

        ClientProtocol proto = config.protocol();

        clientGateway = ClientGatewayFactory.create(proto, serverHost, serverPort);




        /*
        try {
            var x = gateway.serverStatusRequest();
            logger.info("Requested serverStatusRequest, got {}", x.toString());

            SingleResult<GameStartError> y = gateway.gameStartRequest(GameMode.GAME_MODE_3_PLAYERS, "s", proto);

            switch (y) {
                case SingleResult.Success<GameStartError> s -> {
                    logger.info("Requested gameStartRequest, got successful {}", s);
                }
                case SingleResult.Failure<GameStartError> s -> {
                    logger.error("Requested gameStartRequest, got failure {}, error={}", s, s.error());
                }
            }

            SingleResult<GameStartError> z = gateway.gameStartRequest(GameMode.GAME_MODE_3_PLAYERS, "k", proto);

            switch (z) {
                case SingleResult.Success<GameStartError> s -> {
                    logger.info("Requested gameStartRequest, got successful {}", s);
                }
                case SingleResult.Failure<GameStartError> s -> {
                    logger.error("Requested gameStartRequest, got failure {}, error={}", s, s.error());
                }
            }
        } catch (RemoteException e) {
            logger.error("Got RemoteException", e);
            throw new RuntimeException(e);
        }*/
    }

    public ClientController getController() {
        return controller;
    }

    public ClientGateway getClientGateway() {
        return clientGateway;
    }
}