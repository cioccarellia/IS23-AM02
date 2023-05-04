package it.polimi.ingsw;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import it.polimi.ingsw.launcher.parameters.*;
import it.polimi.ingsw.networkProtocol.RMIConnection.ServerGateway;
import org.jetbrains.annotations.NotNull;

public class AppClient {

    public AppClient(@NotNull ClientExhaustiveConfiguration config, String serverIp, int serverPort) {
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
            ServerGateway stub = (ServerGateway) registry.lookup(ServerGateway.NAME);

            // Calling the remote method using the obtained object
            var status = stub.serverStatusRequest();



        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }
}