package it.polimi.ingsw.model.chat;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Optional;

public record ChatTextMessage(
        String senderUsername,
        Recipient recipient,
        String text,
        Timestamp stamp
) implements Serializable {
    public boolean isBroadcast() {
        return switch (recipient) {
            case Recipient.Broadcast broadcast -> true;
            case Recipient.Direct direct -> false;
        };
    }

    public Optional<String> directUsername() {
        return switch (recipient) {
            case Recipient.Broadcast broadcast -> Optional.empty();
            case Recipient.Direct direct -> Optional.of(direct.username());
        };
    }
}