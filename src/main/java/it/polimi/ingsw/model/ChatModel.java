package it.polimi.ingsw.model;

import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.model.chat.MessageRecipient;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ChatModel {
    private final List<ChatTextMessage> messages = new ArrayList<>();

    public void addMessage(String senderUsername, MessageRecipient messageRecipient, String text) {
        ChatTextMessage m = new ChatTextMessage(
                senderUsername,
                messageRecipient,
                text,
                Timestamp.from(Instant.now())
        );
        messages.add(m);
    }

    public List<ChatTextMessage> getMessagesFor(String viewerUsername) {
        return messages.stream()
                .filter(it -> {
                    if (it.directUsername().isPresent()) {
                        return it.directUsername().get().equals(viewerUsername);
                    } else {
                        return it.isBroadcast(); // should always be true
                    }
                })
                .toList();
    }
}
