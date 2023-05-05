package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.networkProtocol.RMIConnection.ServerGateway;

import java.net.ServerSocket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static it.polimi.ingsw.networkProtocol.RMIConnection.ServerGateway.NAME;

public class AppServer {

    GameController controller = new GameController();

    public AppServer(String serverAddress, int serverPort) {

        try {
            ServerGateway stub = (ServerGateway) UnicastRemoteObject.exportObject(controller, serverPort);

            // Bind the remote object's stub in the registry
            // NO Registry registry = LocateRegistry.getRegistry();
            Registry registry = LocateRegistry.createRegistry(serverPort);

            registry.bind(NAME, stub);


            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.err.println("socket Server Ready");

            System.err.println("Server ready");

        } catch (Exception e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }
}