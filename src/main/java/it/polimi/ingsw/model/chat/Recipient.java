package it.polimi.ingsw.model.chat;

public sealed interface Recipient {

    record Direct(String username) implements Recipient {
    }

    record Broadcast() implements Recipient {
    }

}
