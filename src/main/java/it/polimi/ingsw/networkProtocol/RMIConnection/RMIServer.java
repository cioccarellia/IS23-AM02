package it.polimi.ingsw.networkProtocol.RMIConnection;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.launcher.parameters.ClientExhaustiveConfiguration;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.model.game.GameMode;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer {
    GameController controller = new GameController(GameMode.GAME_MODE_2_PLAYERS);

    public RMIServer(String serverAddress, int serverPort) {

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
