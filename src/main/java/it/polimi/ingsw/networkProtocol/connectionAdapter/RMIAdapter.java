package it.polimi.ingsw.networkProtocol.connectionAdapter;

import it.polimi.ingsw.networkProtocol.RMIConnection.RMIServer;
import it.polimi.ingsw.networkProtocol.socketConnection.socketClient;

public class RMIAdapter extends RMIServer implements socketClient {
    public RMIAdapter(String serverAddress, int serverPort) {
        super(serverAddress, serverPort);
    }
}
