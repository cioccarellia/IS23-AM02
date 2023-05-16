package it.polimi.ingsw.net.tcp;

import it.polimi.ingsw.controller.server.wrappers.ServerTcpWrapper;
import it.polimi.ingsw.net.tcp.messages.Message;
import it.polimi.ingsw.utils.json.Parsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TcpConnectionHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(TcpConnectionHandler.class);

    private final Socket socket;
    private final ServerTcpWrapper wrapper;

    public TcpConnectionHandler(Socket socket, ServerTcpWrapper wrapper) {
        this.socket = socket;
        this.wrapper = wrapper;
    }

    @Override
    public void run() {
        logger.info("Starting TcpConnectionHandler for socket={}", socket);
        try (
                Scanner in = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream())
        ) {
            logger.info("Actively monitoring socket={}", socket);
            while (socket.isConnected()) { // fixme
                if (in.hasNextLine()) {
                    logger.info("Waiting for new socket line, socket={}", socket);
                    // receive serialized message
                    String serializedJsonMessage = in.nextLine();

                    logger.info("Received request={}", serializedJsonMessage);

                    // de-serialize message from JSON to Message
                    Message inputMessage = Parsers.marshaledGson().fromJson(serializedJsonMessage, Message.class);

                    logger.info("Deserialized request into message={}", inputMessage);


                    // sends the message to the controller, processes it, and returns a reply message
                    wrapper.convertMessageToControllerMethodCall(inputMessage);

                    // fixme no reply | logger.info("Sending reply message {}", replyMessage);

                    // // serialize in JSON the reply message
                    // String serializedReplyMessage = Parsers.marshaledGson().toJson(replyMessage);

                    //logger.info("Serialized reply {}", serializedReplyMessage);

                    // // Send the serialized reply
                    // out.println(serializedReplyMessage);
                    // out.flush();

                    // todo determine when a socket has been closed and stop communicating
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
}
