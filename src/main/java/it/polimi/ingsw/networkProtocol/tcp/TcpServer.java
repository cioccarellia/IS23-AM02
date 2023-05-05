package it.polimi.ingsw.networkProtocol.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpServer {
    private int port;

    public TcpServer(int port) {
        this.port = port;
    }

    public void startServer() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // Porta non disponibile
            return;
        }

        System.out.println("Server ready");

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                executor.submit(new TcpConnectionHandler(socket));
            } catch (IOException e) {
                break; // Entrerei qui se serverSocket venisse chiuso
            }
        }
        executor.shutdown();
    }
}
