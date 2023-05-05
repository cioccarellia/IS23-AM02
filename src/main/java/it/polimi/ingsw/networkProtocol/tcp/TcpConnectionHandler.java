package it.polimi.ingsw.networkProtocol.tcp;

import com.google.gson.Gson;
import it.polimi.ingsw.networkProtocol.tcp.messages.request.Request;
import it.polimi.ingsw.networkProtocol.tcp.messages.response.Response;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TcpConnectionHandler implements Runnable {
    private final Socket socket;

    public TcpConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    private PrintWriter out;

    void receiveMessage(Request clientMessage) {

    }

    void sendResponse(Response response) {
        Gson gson = new Gson();

        String serializedMessage = gson.toJson(response);

    }

    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());

            // Leggo e scrivo nella connessione finche' non ricevo "quit"
            while (true) {
                String line = in.nextLine();
                if (line.equals("quit")) {
                    break;
                } else {
                    out.println("Received: " + line);
                    out.flush();
                }
            }

            // Chiudo gli stream e il socket
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
