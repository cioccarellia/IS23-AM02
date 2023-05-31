package it.polimi.ingsw.model.chat;

import java.io.Serializable;

public sealed interface Recipient {

    record Direct(String username) implements Recipient, Serializable {
    }

    record Broadcast() implements Recipient, Serializable {
    }

}
