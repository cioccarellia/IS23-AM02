package it.polimi.ingsw.app.client;

import it.polimi.ingsw.app.server.AppServer;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.gateways.ClientGateway;
import it.polimi.ingsw.launcher.parameters.ClientExhaustiveConfiguration;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.launcher.parameters.ClientUiMode;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppClient {

    private static final Logger logger = LoggerFactory.getLogger(AppClient.class);

    ClientController controller; // = new ClientController();
    ClientGateway clientGateway;

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

    public static void main(String[] args) {
        int tcpPort = 12000, rmiPort = 13000;
        AppServer s = new AppServer("localhost", tcpPort, rmiPort);

        AppClient c1 = new AppClient(new ClientExhaustiveConfiguration(ClientUiMode.CLI, ClientProtocol.RMI), "localhost", rmiPort);
        AppClient c2 = new AppClient(new ClientExhaustiveConfiguration(ClientUiMode.CLI, ClientProtocol.TCP), "localhost", tcpPort);
    }
}