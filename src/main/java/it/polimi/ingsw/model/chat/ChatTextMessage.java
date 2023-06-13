package it.polimi.ingsw.model.chat;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Optional;

public record ChatTextMessage(
        String senderUsername,
        MessageRecipient messageRecipient,
        String text,
        Timestamp stamp
) implements Serializable {
    public boolean isBroadcast() {
        return switch (messageRecipient) {
            case MessageRecipient.Broadcast broadcast -> true;
            case MessageRecipient.Direct direct -> false;
        };
    }

    public Optional<String> directUsername() {
        return switch (messageRecipient) {
            case MessageRecipient.Broadcast broadcast -> Optional.empty();
            case MessageRecipient.Direct direct -> Optional.of(direct.username());
        };
    }

    public String toString() {
        String time = this.stamp().toString().split(" ")[1];

        return time + " " + this.senderUsername() + ": " + this.text();
    }
}