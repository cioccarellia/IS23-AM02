package it.polimi.ingsw.controller.server.wrappers;

import it.polimi.ingsw.controller.server.GameController;
import it.polimi.ingsw.net.rmi.RmiServer;
import it.polimi.ingsw.net.tcp.TcpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

public class ServerPair {

    private static final Logger logger = LoggerFactory.getLogger(ServerPair.class);

    private final ServerTcpWrapper tcp;
    private final ServerRmiWrapper rmi;

    private final RmiServer rmiServer;
    private final TcpServer tcpServer;

    public ServerPair(GameController controller, int tcpPort, int rmiPort) {
        this.tcp = new ServerTcpWrapper(controller);

        try {
            this.rmi = new ServerRmiWrapper(controller);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


        // TCP
        logger.info("Starting up TCP server, tcpPort {}", tcpPort);
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
