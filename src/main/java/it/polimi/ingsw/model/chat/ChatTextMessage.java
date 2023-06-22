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
        String fullTime = this.stamp().toString().split(" ")[1];
        String time = fullTime.substring(0, fullTime.length() - 2);

        return switch (messageRecipient) {
            case MessageRecipient.Broadcast broadcast -> time + " [" + this.senderUsername() + "]: " + this.text();
            case MessageRecipient.Direct direct ->
                    time + " [" + this.senderUsername() + " -> " + direct.username() + "]: " + this.text();
        };
    }
}