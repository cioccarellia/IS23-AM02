package it.polimi.ingsw.net.tcp;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.server.wrappers.ServerTcpWrapper;
import it.polimi.ingsw.net.tcp.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TcpConnectionHandler implements Runnable {

    protected static final Logger logger = LoggerFactory.getLogger(TcpConnectionHandler.class);

    private final Socket socket;
    private final ServerTcpWrapper wrapper;

    private final Gson gson = new Gson();

    public TcpConnectionHandler(Socket socket, ServerTcpWrapper wrapper) {
        this.socket = socket;
        this.wrapper = wrapper;
    }

    public void run() {
        try (Scanner in = new Scanner(socket.getInputStream()); PrintWriter out = new PrintWriter(socket.getOutputStream())) {

            while (socket.isBound()) { // fixme
                // receive serialized message
                String serializedJsonMessage = in.nextLine();

                // de-serialize message from JSON to Message
                Message inputMessage = gson.fromJson(serializedJsonMessage, Message.class);

                // sends the message to the controller, processes it, and returns a reply message
                Message replyMessage = wrapper.receiveAndReturnMessage(inputMessage);

                // serialize in JSON the reply message
                String serializedReplyMessage = gson.toJson(replyMessage);

                // Send the serialized reply
                out.print(serializedReplyMessage);

                // todo determine when a socket has been closed and stop communicating
            }

            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            // fixme
        }
    }
}
