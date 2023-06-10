package it.polimi.ingsw.model.chat;

import java.io.Serializable;

public sealed interface MessageRecipient {

    record Direct(String username) implements MessageRecipient, Serializable {
    }

    record Broadcast() implements MessageRecipient, Serializable {
    }

}
