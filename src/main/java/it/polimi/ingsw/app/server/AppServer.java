package it.polimi.ingsw.app.server;

import it.polimi.ingsw.app.server.network.ServerNetworkLayer;
import it.polimi.ingsw.app.server.storage.StorageManager;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.wrappers.ServerPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Launches a server instance
 */
public class AppServer {

    private static final Logger logger = LoggerFactory.getLogger(AppServer.class);

    /**
     * Handles connection
     */
    ClientConnectionsManager connectionsManager = new ClientConnectionsManager();


    /**
     * Handles record creation/deletion/reading
     */
    StorageManager storageManager = new StorageManager();

    /**
     * Root controller
     */
    ServerController controller = new ServerController(connectionsManager, storageManager);

    /**
     * RMI and TCP active servers
     */
    ServerPair pair;

    public AppServer(String serverAddress, int tcpPort, int rmiPort) {
        logger.info("Starting AppServer, serverAddress={}, tcpPort={}, rmiPort={}", serverAddress, tcpPort, rmiPort);

        pair = new ServerPair(controller, connectionsManager, serverAddress, tcpPort, rmiPort);
        pair.bindAndStartServers();

        initializeServerThreads();
    }

    private void initializeServerThreads() {
        ServerNetworkLayer.scheduleTimeoutThread(controller);
    }
}