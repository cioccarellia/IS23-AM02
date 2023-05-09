package it.polimi.ingsw.app.server;

import it.polimi.ingsw.controller.server.GameController;
import it.polimi.ingsw.controller.server.wrappers.ServerPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AppServer {

    private static final Logger logger = LoggerFactory.getLogger(AppServer.class);

    GameController controller = new GameController();

    ServerPair pair;

    public AppServer(String serverAddress, int tcpPort, int rmiPort) {
        logger.info("Starting AppServer, serverAddress={}, tcpPort={}, rmiPort={}", serverAddress, tcpPort, rmiPort);
        pair = new ServerPair(controller, tcpPort, rmiPort);
    }
}