package it.polimi.ingsw.controller.server.connection.stash;

import it.polimi.ingsw.launcher.parameters.ClientProtocol;

public class StashFactory {
    /**
     * Creates a {@link ProtocolStash} instance in accordance for the given protocol.
     */
    public static ProtocolStash create(ClientProtocol proto) {
        switch (proto) {
            case RMI -> {
                return new RmiStash();
            }
            case TCP -> {
                return new TcpStash();
            }
            default -> throw new IllegalStateException("Unexpected value: " + proto);
        }
    }
}
