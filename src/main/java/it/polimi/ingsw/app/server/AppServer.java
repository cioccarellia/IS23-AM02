package it.polimi.ingsw.app.server;

import it.polimi.ingsw.controller.server.GameController;
import it.polimi.ingsw.controller.server.wrappers.ServerPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AppServer {

    protected static final Logger logger = LoggerFactory.getLogger(AppServer.class);

    GameController controller = new GameController();

    ServerPair pair;

    public AppServer(String serverAddress, int serverPort) {
        logger.info("Starting AppServer, serverAddress={}, serverPort={}", serverAddress, serverPort);
        pair = new ServerPair(controller, serverPort, serverPort + 1);
    }
}