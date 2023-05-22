package it.polimi.ingsw.app.server.network;

import it.polimi.ingsw.app.server.timeout.TimeoutKeepAliveHandler;
import it.polimi.ingsw.controller.server.ServerController;
import org.jetbrains.annotations.NotNull;

public class ServerNetworkLayer {

    public static void scheduleTimeoutThread(@NotNull ServerController masterController) {
        new Thread(new TimeoutKeepAliveHandler(masterController)).start();
    }

}
