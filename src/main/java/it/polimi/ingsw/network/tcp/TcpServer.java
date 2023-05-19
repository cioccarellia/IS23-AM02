package it.polimi.ingsw.network.tcp;

import it.polimi.ingsw.controller.server.wrappers.ServerTcpWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class TcpServer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(TcpServer.class);

    private final ServerTcpWrapper wrapper;
    private final int serverTcpPort;

    /**
     * Used to schedule TcpConnectionHandler tasks
     */
    private final ExecutorService executorService;

    /**
     * Signals whether the current server thread is up and running,
     * actively listening to incoming TCP connections
     */
    private boolean running;

    public TcpServer(ServerTcpWrapper wrapper, int serverTcpPort) {
        this.wrapper = wrapper;
        this.serverTcpPort = serverTcpPort;

        executorService = Executors.newFixedThreadPool(5);
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

    /**
     * Context-blocking thread
     */
    @Override
    public void run() {
        logger.info("Starting TCP server thread");
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(serverTcpPort);
        } catch (IOException e) {
            logger.error("Failed to create the socket [on port={}]", serverTcpPort);
            logger.error("IOException:", e);
            return;
        }

        logger.info("Server socket created successfully. TCP server ready [serverSocket={}]", serverSocket);

        while (!executorService.isShutdown()) {
            try {
                logger.info("TcpServer going on sleep, awaiting for new TCP connection [on port {}]", serverTcpPort);
                Socket socket = serverSocket.accept();

                logger.info("Accepted new TCP connection, starting TcpConnectionHandler for socket={}", socket.toString());


                TcpConnectionHandler handler = new TcpConnectionHandler(socket, wrapper);
                registeredHandlers.add(handler);
                executorService.execute(handler);
            } catch (IOException e) {
                logger.error("Exception while awaiting/accepting TCP connections", e);
                break;
            }
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        logger.warn("Reached the end of TCP thread");
    }


    /**
     * Keeps track of all the
     * */
    private static List<TcpConnectionHandler> registeredHandlers = new ArrayList<>();

    public static List<TcpConnectionHandler> getRegisteredHandlers() {
        return registeredHandlers;
    }

}
