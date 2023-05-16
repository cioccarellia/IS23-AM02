package it.polimi.ingsw.net.tcp.messages;

public abstract class Message {
    protected String type = getClass().getName();
}