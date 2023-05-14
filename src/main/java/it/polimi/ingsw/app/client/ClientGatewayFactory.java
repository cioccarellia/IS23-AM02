package it.polimi.ingsw.app.client;

import it.polimi.ingsw.controller.client.gateways.ClientGateway;
import it.polimi.ingsw.controller.client.gateways.RmiClientGateway;
import it.polimi.ingsw.controller.client.gateways.TcpClientGateway;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;

/**
 * Handles the creation of the proper gateway class for the protocol in use
 */
public class ClientGatewayFactory {

    /**
     * Creates a {@link ClientGateway} instance in accordance with the given protocol.
     */
    public static ClientGateway create(ClientProtocol proto, String serverHost, int serverPort) {
        switch (proto) {
            case RMI -> {
                return new RmiClientGateway(serverHost, serverPort);
            }
            case TCP -> {
                return new TcpClientGateway(serverHost, serverPort);
            }
            default -> throw new IllegalStateException("Unexpected value: " + proto);
        }
    }
}
