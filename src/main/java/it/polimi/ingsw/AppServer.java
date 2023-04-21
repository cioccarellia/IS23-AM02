package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.game.GameMode;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class AppServer {

    GameController controller = new GameController(GameMode.GAME_MODE_2_PLAYERS);

    public AppServer(String serverAddress, int serverPort) {

        try {
            Callable stub = (Callable) UnicastRemoteObject.exportObject(controller, serverPort);

            // Bind the remote object's stub in the registry
            // NO Registry registry = LocateRegistry.getRegistry();
            Registry registry = LocateRegistry.createRegistry(serverPort);

            registry.bind("Callable", stub);

            System.err.println("Server ready");


        } catch (Exception e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }
}