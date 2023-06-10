package it.polimi.ingsw.network.tcp.messages.request;

import it.polimi.ingsw.model.chat.MessageRecipient;
import org.jetbrains.annotations.Nullable;

public class ChatTextMessageRequest extends Request {
    private final String senderUsername;
    private final boolean isBroadcast;
    private final @Nullable String recipientUsername;
    private final String text;


    public ChatTextMessageRequest(String senderUsername, boolean isBroadcast, @Nullable String recipientUsername, String text) {
        this.senderUsername = senderUsername;
        this.isBroadcast = isBroadcast;
        this.recipientUsername = recipientUsername;
        this.text = text;
    }


    public ChatTextMessageRequest(String senderUsername, MessageRecipient recipient, String text) {
        this.senderUsername = senderUsername;
        this.text = text;

        switch (recipient) {
            case MessageRecipient.Broadcast broadcast -> {
                this.isBroadcast = true;
                this.recipientUsername = null;
            }
            case MessageRecipient.Direct direct -> {
                this.isBroadcast = false;
                this.recipientUsername = direct.username();
            }
            default -> throw new IllegalStateException();
        }
    }

    public String senderUsername() {
        return senderUsername;
    }

    public MessageRecipient recipient() {
        if (isBroadcast) {
            return new MessageRecipient.Broadcast();
        } else {
            assert senderUsername != null;
            return new MessageRecipient.Direct(senderUsername);
        }
    }

    public String text() {
        return text;
    }
}
