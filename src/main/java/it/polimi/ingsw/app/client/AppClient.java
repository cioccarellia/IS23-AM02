package it.polimi.ingsw.app.client;

import it.polimi.ingsw.app.server.AppServer;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.gateways.Gateway;
import it.polimi.ingsw.launcher.parameters.ClientExhaustiveConfiguration;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.launcher.parameters.ClientUiMode;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

public class AppClient {

    protected static final Logger logger = LoggerFactory.getLogger(AppClient.class);

    ClientController controller; // = new ClientController();
    Gateway gateway;

    public AppClient(@NotNull ClientExhaustiveConfiguration config, String serverHost, int serverPort) {
        logger.info("Starting AppClient, config={}, serverHost={}", config, serverHost);

        ClientProtocol proto = config.protocol();

        gateway = ClientGatewayFactory.create(proto, serverHost, serverPort);

        try {
            var x = gateway.serverStatusRequest();
            System.out.println(x);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        AppServer s = new AppServer("localhost", 8080);

        AppClient c1 = new AppClient(new ClientExhaustiveConfiguration(ClientUiMode.CLI, ClientProtocol.RMI), "localhost", 8080);


        AppClient c2 = new AppClient(new ClientExhaustiveConfiguration(ClientUiMode.CLI, ClientProtocol.TCP), "localhost", 8080);

    }
}