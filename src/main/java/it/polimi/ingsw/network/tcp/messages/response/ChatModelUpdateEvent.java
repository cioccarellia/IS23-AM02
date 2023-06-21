package it.polimi.ingsw.network.tcp.messages.response;

import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.model.chat.MessageRecipient;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ChatModelUpdateEvent extends Response {
    final private List<String> destructuredSenderUsernames = new ArrayList<>();
    final private List<String> destructurdRecipientStrs = new ArrayList<>();
    final private List<String> destructurdText = new ArrayList<>();
    final private List<Timestamp> destructuredTimestamp = new ArrayList<>();

    final private int size;

    public ChatModelUpdateEvent(List<ChatTextMessage> messages) {
        size = messages.size();

        for (ChatTextMessage it : messages) {
            this.destructuredSenderUsernames.add(it.senderUsername());
            this.destructurdRecipientStrs.add(it.isBroadcast() ? "" : it.directUsername().get());
            this.destructurdText.add(it.text());
            this.destructuredTimestamp.add(it.stamp());
        }

    }

    public List<ChatTextMessage> getMessages() {
        List<ChatTextMessage> m = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            String sender = destructuredSenderUsernames.get(i);
            String recipientUsername = destructurdRecipientStrs.get(i);
            MessageRecipient recipient = recipientUsername.isEmpty() ? new MessageRecipient.Broadcast() : new MessageRecipient.Direct(recipientUsername);

            String text = destructurdText.get(i);
            Timestamp stamp = destructuredTimestamp.get(i);

            m.add(
                    new ChatTextMessage(sender, recipient, text, stamp)
            );
        }

        return m;
    }

    @Override
    public String toString() {
        return "ChatModelUpdateEvent{" +
                "destructuredSenderUsernames=" + destructuredSenderUsernames +
                ", destructurdRecipientStrs=" + destructurdRecipientStrs +
                ", destructurdText=" + destructurdText +
                ", destructuredTimestamp=" + destructuredTimestamp +
                ", size=" + size +
                '}';
    }
}
