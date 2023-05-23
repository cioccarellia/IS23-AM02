package it.polimi.ingsw.app.server.timeout;

import it.polimi.ingsw.controller.server.ServerController;
import it.polimi.ingsw.controller.server.connection.ClientConnection;
import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
import it.polimi.ingsw.controller.server.connection.PeriodicConnectionAwareComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Timeout handler. Each connection is kept as a {@link it.polimi.ingsw.controller.server.connection.ConnectionStatus}
 * in the {@link it.polimi.ingsw.controller.server.connection.ClientConnection} structure.
 * To make sure values are updated, each keep-alive message updates the last time a connection has been detected.
 * Therefore, each fixed time, this thread takes over and checks that
 */
public class TimeoutKeepAliveHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(TimeoutKeepAliveHandler.class);

    private boolean isTimeoutThreadActive = true;

    /**
     * Period of this thread's execution
     */
    private final static int TIMEOUT = 7_500;

    /**
     * The amount of seconds after which a connection is deemed not active
     */
    private final static int MAX_SECONDS_DISCINNECTION_THRESHOLD = 25;

    /**
     * Component to execute the task on
     */
    private final PeriodicConnectionAwareComponent consumer;

    /**
     * For state-awareness
     */
    private final Map<String, ConnectionStatus> previousState = new HashMap<>();

    public TimeoutKeepAliveHandler(PeriodicConnectionAwareComponent consumer) {
        this.consumer = consumer;
    }

    @Override
    public void run() {
        while (isTimeoutThreadActive) {
            synchronized (ServerController.class) {
                Date now = Calendar.getInstance().getTime();
                var connections = consumer.getConnectionsManager().values();

                boolean hasDetectedAnyChanges = false;

                for (ClientConnection connection : connections) {
                    // how many seconds since last contact
                    long secondsDiff = ChronoUnit.SECONDS.between(connection.getLastSeen().toInstant(), now.toInstant());

                    logger.warn("keep alive checking for @{}: delta is {}s", connection.getUsername(), secondsDiff);


                    if (connection.getStatus() == ConnectionStatus.OPEN && secondsDiff > MAX_SECONDS_DISCINNECTION_THRESHOLD) {
                        // we went over the threshold, flagging that client as disconnected
                        logger.warn("Disconnection threshold reached, flagging {} as DISCONNECTED", connection.getUsername());
                        connection.setStatus(ConnectionStatus.DISCONNECTED);
                    }


                    // check for changes
                    if (previousState.containsKey(connection.getUsername()) && previousState.get(connection.getUsername()) != connection.getStatus()) {
                        // new state detected
                        hasDetectedAnyChanges = true;
                    }

                    // save the current value on the previous state
                    previousState.put(connection.getUsername(), connection.getStatus());
                }

                if (hasDetectedAnyChanges) {
                    consumer.onConnectionChange();
                }
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
