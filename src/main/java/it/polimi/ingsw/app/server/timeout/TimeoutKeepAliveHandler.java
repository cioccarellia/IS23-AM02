package it.polimi.ingsw.app.server.timeout;

import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * Timeout handler. Each connection is kept as a {@link it.polimi.ingsw.controller.server.connection.ConnectionStatus}
 * in the {@link it.polimi.ingsw.controller.server.connection.ClientConnection} structure.
 * To make sure values are updated, each keep-alive message updates the last time a connection has been detected.
 * Therefore, each fixed time, this thread takes over and checks that
 * */
public class TimeoutKeepAliveHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(TimeoutKeepAliveHandler.class);

    private boolean isTimeoutThreadActive = true;

    /**
     * Period of this thread's execution
     * */
    private final static int TIMEOUT = 7_500;

    /**
     * The amount of seconds after which a connection is deemed not active
     * */
    private final static int MAX_SECONDS_DISCINNECTION_THRESHOLD = 25;

    /**
     * Controller to execute the task on
     * */
    final ServerController masterController;

    public TimeoutKeepAliveHandler(ServerController masterController) {
        this.masterController = masterController;
    }

    @Override
    public void run() {
        while (isTimeoutThreadActive) {
            Date now = Calendar.getInstance().getTime();
            var connections = masterController.getConnectionsManager().values();

            synchronized (ServerController.class) {
                connections.forEach(connection -> {
                    if (connection.getStatus() != ConnectionStatus.OPEN) {
                        long secondsDiff = ChronoUnit.SECONDS.between(connection.getLastSeen().toInstant(), now.toInstant());

                        logger.warn("keep alive checking for {}: delta is {}", connection.getUsername(), secondsDiff);

                        if (secondsDiff > MAX_SECONDS_DISCINNECTION_THRESHOLD) {
                            logger.warn("Disconnection threshold reached, flagging {} as DISCONNECTED", connection.getUsername());
                            connection.setStatus(ConnectionStatus.DISCONNECTED);
                        }
                    }
                });
            }

            try {
                Thread.sleep(TIMEOUT);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isTimeoutThreadActive() {
        return isTimeoutThreadActive;
    }

    public void setTimeoutThreadActive(boolean timeoutThreadActive) {
        isTimeoutThreadActive = timeoutThreadActive;
    }
}
