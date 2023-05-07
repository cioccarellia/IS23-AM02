package it.polimi.ingsw;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.controller.client.gateways.RMIGateway;
import it.polimi.ingsw.controller.client.gateways.TCPGateway;
import it.polimi.ingsw.controller.server.ServerService;
import it.polimi.ingsw.launcher.parameters.ClientExhaustiveConfiguration;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import org.jetbrains.annotations.NotNull;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static it.polimi.ingsw.controller.server.ServerService.NAME;

public class AppClient {

    ClientController controller; // = new ClientController();

    ServerService server;

    public AppClient(@NotNull ClientExhaustiveConfiguration config, String serverIp, int serverPort) {
        ClientProtocol proto = config.protocol();

        switch (proto) {
            case RMI -> {
                server = new RMIGateway(serverIp, serverPort);
            }
            case TCP -> {
                server = new TCPGateway(serverIp, serverPort);
            }
        }



        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(serverIp, serverPort);

            // Looking up the registry for the remote object
            ServerService stub = (ServerService) registry.lookup(NAME);

            // Calling the remote method using the obtained object
            var status = stub.serverStatusRequest();


        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }
}