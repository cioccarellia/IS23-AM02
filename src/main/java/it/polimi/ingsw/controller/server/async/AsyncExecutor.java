package it.polimi.ingsw.controller.server.async;

import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Specialized {@link ExecutorService} for networking calls
 * */
public class AsyncExecutor {

    private final ExecutorService service;

    public AsyncExecutor(ExecutorService service) {
        this.service = service;
    }

    public void async(RemoteSAM sam) {
        service.execute(() -> {
            try {
                sam.exec();
            } catch (RemoteException e) {
                System.out.println("Generic network issue");
                e.printStackTrace();
                System.exit(-1);
                throw new RuntimeException(e);
            }
        });
    }

    public static AsyncExecutor newFixedThreadPool(int nThreads) {
        assert nThreads > 0;
        return new AsyncExecutor(Executors.newFixedThreadPool(nThreads));
    }

    public static AsyncExecutor newSingleThreadExecutor() {
        return new AsyncExecutor(Executors.newSingleThreadExecutor());
    }

    public static AsyncExecutor newCachedThreadPool() {
        return new AsyncExecutor(Executors.newCachedThreadPool());
    }
}
