package it.polimi.ingsw.model;

import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.model.chat.MessageRecipient;
import org.jetbrains.annotations.TestOnly;

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
                    switch (it.messageRecipient()) {
                        case MessageRecipient.Broadcast broadcast -> {
                            return true;
                        }
                        case MessageRecipient.Direct direct -> {
                            return direct.username().equals(viewerUsername) || it.senderUsername().equals(viewerUsername);
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + it.messageRecipient());
                    }
                })
                .toList();
    }

    public List<ChatTextMessage> getAllMessages() {
        return messages.stream().toList();
    }
}
