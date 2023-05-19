package it.polimi.ingsw.network.tcp;

import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.SingleResult;
import it.polimi.ingsw.controller.server.result.failures.BookshelfInsertionFailure;
import it.polimi.ingsw.controller.server.result.failures.TileSelectionFailures;
import it.polimi.ingsw.controller.server.wrappers.ServerTcpWrapper;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.network.tcp.messages.Message;
import it.polimi.ingsw.services.ClientService;
import it.polimi.ingsw.utils.json.Parsers;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class TcpConnectionHandler implements Runnable, ClientService {

    private static final Logger logger = LoggerFactory.getLogger(TcpConnectionHandler.class);

    private final Socket socket;
    private final ServerTcpWrapper wrapper;

    private boolean isActivelyListeningOnSocket = true;

    /**
     * Used to intercept and associate a username with a socket connection
     * */
    private String username = null;

    public TcpConnectionHandler(Socket socket, ServerTcpWrapper wrapper) {
        this.socket = socket;
        this.wrapper = wrapper;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActivelyListeningOnSocket() {
        return isActivelyListeningOnSocket;
    }

    public void setActivelyListeningOnSocket(boolean activelyListeningOnSocket) {
        isActivelyListeningOnSocket = activelyListeningOnSocket;
    }

    @Override
    public void run() {
        logger.info("Starting TcpConnectionHandler for socket={}", socket);
        try (
                Scanner in = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream())
        ) {
            logger.info("Actively monitoring socket={}", socket);
            while (isActivelyListeningOnSocket) {
                if (in.hasNextLine()) {
                    logger.info("Waiting for new socket line, socket={}", socket);
                    // receive serialized message
                    String serializedJsonMessage = in.nextLine();

                    logger.info("Received request={}", serializedJsonMessage);

                    // de-serialize message from JSON to Message
                    Message inputMessage = Parsers.marshaledGson().fromJson(serializedJsonMessage, Message.class);

                    logger.info("Deserialized request into message={}", inputMessage);


                    // sends the message to the controller (converting it into the appropriate function call)
                    // for the controller to processes it
                    wrapper.convertMessageToControllerAndForwardMethodCall(inputMessage, TcpConnectionHandler.this);
                }
            }
        } catch (IOException e) {
            logger.error("Error handling TCP connection: {}", e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.error("Error closing socket: socket={}, message={}", socket, e.getMessage());
            }
        }
    }


    @Override
    public void injectUsername(String string) {

    }

    @Override
    public void serverStatusUpdateEvent(ServerStatus status, List<Pair<String, ConnectionStatus>> playerInfo) {

    }

    @Override
    public void gameStartedEvent() {

    }

    @Override
    public void modelUpdateEvent(Game game) {

    }

    @Override
    public void gameSelectionTurnEvent(SingleResult<TileSelectionFailures> turnResult) {

    }

    @Override
    public void gameInsertionTurnEvent(SingleResult<BookshelfInsertionFailure> turnResult) {

    }

    @Override
    public void playerConnectionStatusUpdateEvent(List<Pair<String, ConnectionStatus>> usernames) {

    }

    @Override
    public void gameEndedEvent() {

    }
}
