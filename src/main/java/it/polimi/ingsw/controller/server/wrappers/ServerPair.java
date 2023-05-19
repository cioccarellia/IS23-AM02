package it.polimi.ingsw.controller.server.wrappers;

import it.polimi.ingsw.app.server.ClientConnectionsManager;
import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.network.rmi.RmiServer;
import it.polimi.ingsw.network.tcp.TcpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

@SuppressWarnings("FieldCanBeLocal")
public class ServerPair {

    private static final Logger logger = LoggerFactory.getLogger(ServerPair.class);

    private final ServerTcpWrapper tcp;
    private final ServerRmiWrapper rmi;

    private final RmiServer rmiServer;
    private final TcpServer tcpServer;

    public ServerPair(ServerController controller, ClientConnectionsManager manager, int tcpPort, int rmiPort) {
        this.tcp = new ServerTcpWrapper(controller);

        try {
            this.rmi = new ServerRmiWrapper(controller, manager);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        // TCP
        logger.info("Starting up TCP server (+ listener thread), tcpPort {}", tcpPort);
        tcpServer = new TcpServer(tcp, tcpPort);
        tcpServer.start();

        // RMI
        logger.info("Starting up RMI server, rmiPort {}", rmiPort);
        rmiServer = new RmiServer(rmi, rmiPort);
        rmiServer.bind();
    }

    public ServerTcpWrapper tcp() {
        return tcp;
    }

    public ServerRmiWrapper rmi() {
        return rmi;
    }
}
