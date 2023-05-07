package it.polimi.ingsw;

import it.polimi.ingsw.controller.server.GameController;
import it.polimi.ingsw.controller.server.wrappers.ServerRMIWrapper;
import it.polimi.ingsw.controller.server.wrappers.ServerTCPWrapper;
import it.polimi.ingsw.networkProtocol.rmi.RmiServer;
import it.polimi.ingsw.networkProtocol.tcp.TcpServer;

import java.rmi.RemoteException;


public class AppServer {

    GameController controller = new GameController();

    ServerTCPWrapper tcp;
    ServerRMIWrapper rmi;

    public AppServer(String serverAddress, int serverPort) {

        tcp = new ServerTCPWrapper(controller);

        try {
            rmi = new ServerRMIWrapper(controller);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


        // TCP
        TcpServer tcpServer = new TcpServer(tcp, serverPort);
        tcpServer.startServer();

        // RMI
        RmiServer rmiServer = new RmiServer(rmi, serverPort);
        rmiServer.startServer();
    }
}