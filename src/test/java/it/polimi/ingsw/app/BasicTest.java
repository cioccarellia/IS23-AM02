package it.polimi.ingsw.app;

import it.polimi.ingsw.app.client.AppClient;
import it.polimi.ingsw.app.server.AppServer;
import it.polimi.ingsw.launcher.parameters.ClientExhaustiveConfiguration;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.launcher.parameters.ClientUiMode;
import org.junit.jupiter.api.Test;

public class BasicTest {

    @Test
    public void x() {
        int tcpPort = 12000, rmiPort = 13000;
        AppServer s = new AppServer("localhost", tcpPort, rmiPort);

        AppClient c1 = new AppClient(new ClientExhaustiveConfiguration(ClientUiMode.CLI, ClientProtocol.RMI), "localhost", rmiPort);
        AppClient c2 = new AppClient(new ClientExhaustiveConfiguration(ClientUiMode.GUI, ClientProtocol.TCP), "localhost", tcpPort);
        AppClient c3 = new AppClient(new ClientExhaustiveConfiguration(ClientUiMode.CLI, ClientProtocol.TCP), "localhost", tcpPort);
    }

}
