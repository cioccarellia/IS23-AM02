package it.polimi.ingsw;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import it.polimi.ingsw.launcher.parameters.*;

public class AppClient {

    public AppClient(ClientExhaustiveConfiguration config, String serverIp, int serverPort) {

        ClientProtocol proto = config.protocol();

        switch (proto) {
            case RMI -> {

            }
            case SOCKET -> {

            }
        }


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