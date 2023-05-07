package it.polimi.ingsw.app.client;

import it.polimi.ingsw.controller.client.gateways.Gateway;
import it.polimi.ingsw.controller.client.gateways.RmiGateway;
import it.polimi.ingsw.controller.client.gateways.TcpGateway;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;

/**
 * Handles the creation of the proper gateway class for the protocl in use
 * */
public class ClientGatewayFactory {

    /**
     * Creates a {@link Gateway} instance in accordance with the given protocol.
     * */
    public static Gateway create(ClientProtocol proto, String serverHost, int serverPort) {
        switch (proto) {
            case RMI -> {
                return new RmiGateway(serverHost, serverPort);
            }
            case TCP -> {
                return new TcpGateway(serverHost, serverPort);
            }
            default -> throw new IllegalStateException("Unexpected value: " + proto);
        }
    }
}
