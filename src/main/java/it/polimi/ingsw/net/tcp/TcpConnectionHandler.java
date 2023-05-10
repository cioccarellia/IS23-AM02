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
        try (
                Scanner in = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream())
        ) {
            while (true) { // fixme
                // receive serialized message
                String serializedJsonMessage = in.nextLine();

                // de-serialize message from JSON to Message
                Message inputMessage = Parsers.tcpMarshaledJson().fromJson(serializedJsonMessage, Message.class);

                // sends the message to the controller, processes it, and returns a reply message
                Message replyMessage = wrapper.receiveAndReturnMessage(inputMessage);

                // serialize in JSON the reply message
                String serializedReplyMessage = Parsers.tcpMarshaledJson().toJson(replyMessage);

                // Send the serialized reply
                out.print(serializedReplyMessage);

                // todo determine when a socket has been closed and stop communicating
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.error("Can not close socket");
            }
        }
    }
}
