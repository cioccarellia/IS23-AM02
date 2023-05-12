package it.polimi.ingsw.app.server;

import it.polimi.ingsw.controller.server.GameController;
import it.polimi.ingsw.controller.server.wrappers.ServerPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AppServer {

    private static final Logger logger = LoggerFactory.getLogger(AppServer.class);

    /**
     * Handles connection
     * */
    ClientConnectionsManager connectionsManager = new ClientConnectionsManager();

    /**
     * Root controller
     * */
    GameController controller = new GameController(connectionsManager);

    /**
     * RMI and TCP active servers
     * */
    ServerPair pair;

    public AppServer(String serverAddress, int tcpPort, int rmiPort) {
        logger.info("Starting AppServer, serverAddress={}, tcpPort={}, rmiPort={}", serverAddress, tcpPort, rmiPort);
        pair = new ServerPair(controller, connectionsManager, tcpPort, rmiPort);
    }
}