package it.polimi.ingsw.networkProtocol.rmi;

import it.polimi.ingsw.controller.server.ServerService;
import it.polimi.ingsw.controller.server.wrappers.ServerRMIWrapper;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer {

    final private ServerRMIWrapper wrapper;
    final private int serverPort;

    public RmiServer(ServerRMIWrapper wrapper, int serverPort) {
        this.wrapper = wrapper;
        this.serverPort = serverPort;
    }

    public void startServer() {
        // RMI
        try {
            ServerService stub = (ServerService) UnicastRemoteObject.exportObject(wrapper, serverPort);

            // Bind the remote object's stub in the registry
            // NO Registry registry = LocateRegistry.getRegistry();
            Registry registry = LocateRegistry.createRegistry(serverPort);

            registry.bind(ServerRMIWrapper.NAME, stub);
        } catch (Exception e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }
}
