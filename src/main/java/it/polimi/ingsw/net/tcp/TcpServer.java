package it.polimi.ingsw.net.tcp;

import it.polimi.ingsw.controller.server.wrappers.ServerTcpWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * */
public class TcpServer implements Runnable {

    protected static final Logger logger = LoggerFactory.getLogger(TcpServer.class);

    /**
     *
     * */
    private final ServerTcpWrapper wrapper;
    private final int port;

    private final ExecutorService executorService;
    private volatile boolean running;

    public TcpServer(ServerTcpWrapper wrapper, int port) {
        this.wrapper = wrapper;
        this.port = port;

        executorService = Executors.newCachedThreadPool();
    }

    public void start() {
        if (running) {
            logger.warn("Server already started");
            throw new IllegalStateException("Server is already running");
        }

        logger.info("Starting TCP server thread");

        running = true;
        new Thread(this).start();
    }

    public void stop() {
        logger.info("Stopping TCP server");

        running = false;
        executorService.shutdown();
    }

    @Override
    public void run() {
        logger.info("Starting TCP server thread");
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            logger.error("Failed to create the socket", e);
            System.err.println(e.getMessage()); // Porta non disponibile
            return;
        }

        logger.info("TCP server ready");

        while (true) {
            try {
                logger.info("Awaiting for new TCP connection");
                Socket socket = serverSocket.accept();

                logger.info("Accepted TCP connection, starting TcpConnectionHandler for socket={}", socket.toString());

                executorService.submit(new TcpConnectionHandler(socket, wrapper));
            } catch (IOException e) {
                logger.error("Exception while awaiting/accepting TCP connections", e);
                break;
            }
        }

        logger.warn("Reached the end of TCP thread");
    }
}
