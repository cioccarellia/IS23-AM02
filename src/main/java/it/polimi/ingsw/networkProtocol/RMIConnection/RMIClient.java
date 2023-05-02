package it.polimi.ingsw.networkProtocol.RMIConnection;

import it.polimi.ingsw.launcher.parameters.ClientExhaustiveConfiguration;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public RMIClient(String serverIp, int serverPort) {

        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(serverIp, serverPort);

            // Looking up the registry for the remote object
            Callable stub = (Callable) registry.lookup("Callable");

            // Calling the remote method using the obtained object
            stub.onPlayerSignUpRequest("marco");



        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }

}
