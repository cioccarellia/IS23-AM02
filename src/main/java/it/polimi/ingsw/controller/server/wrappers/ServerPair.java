package it.polimi.ingsw.controller.server.wrappers;

import it.polimi.ingsw.app.server.ClientConnectionsManager;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.network.rmi.RmiServer;
import it.polimi.ingsw.network.tcp.TcpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

public class ServerPair {

    private static final Logger logger = LoggerFactory.getLogger(ServerPair.class);

    private final ServerTcpWrapper tcp;
    private final int tcpPort;
    private final TcpServer tcpServer;

    private final ServerRmiWrapper rmi;
    private final int rmiPort;
    private final RmiServer rmiServer;

    public ServerPair(ServerController controller, ClientConnectionsManager manager, int tcpPort, int rmiPort) {
        this.tcp = new ServerTcpWrapper(controller);
        this.tcpPort = tcpPort;
        this.rmiPort = rmiPort;

        try {
            this.rmi = new ServerRmiWrapper(controller, manager);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        // TCP
        tcpServer = new TcpServer(tcp, tcpPort);

        // RMI
        rmiServer = new RmiServer(rmi, rmiPort);

        logger.info("Server pair pre-initialization completed");
    }

    public void bindAndStartServers() {
        logger.info("Starting up TCP server (+ listener thread), tcpPort {}", tcpPort);
        tcpServer.start();

        logger.info("Starting up RMI server, rmiPort {}", rmiPort);
        rmiServer.bind();
    }

    public ServerTcpWrapper tcp() {
        return tcp;
    }

    public ServerRmiWrapper rmi() {
        return rmi;
    }
}
