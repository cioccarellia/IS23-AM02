package it.polimi.ingsw.controller.client.lifecycle;

public interface AppLifecycle {

    void initialize();


    void authorize(String username);

    void terminate();

}
