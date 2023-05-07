package it.polimi.ingsw.net.rmi;

import it.polimi.ingsw.controller.server.ServerService;
import it.polimi.ingsw.controller.server.wrappers.ServerRmiWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer {

    protected static final Logger logger = LoggerFactory.getLogger(RmiServer.class);

    final private ServerRmiWrapper wrapper;
    final private int serverPort;

    public RmiServer(ServerRmiWrapper wrapper, int serverPort) {
        this.wrapper = wrapper;
        this.serverPort = serverPort;
    }

    public void bind() {
        logger.info("binding rmi server");

        try {
            logger.info("exporting rmi stub");
            ServerService stub = (ServerService) UnicastRemoteObject.exportObject(wrapper, serverPort);

            logger.info("creating rmi registry");
            Registry registry = LocateRegistry.createRegistry(serverPort);

            logger.info("binding rmi stub");
            registry.bind(ServerRmiWrapper.NAME, stub);

            logger.info("rmi registry is bound");
        } catch (Exception e) {
            logger.error("rmi server exception");
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }
}
