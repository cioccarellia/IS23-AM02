package it.polimi.ingsw.network.tcp.messages.response;

import it.polimi.ingsw.model.chat.ChatTextMessage;

import java.util.List;

public class ChatModelUpdateEvent extends Response {
    final private List<ChatTextMessage> messages;

    public ChatModelUpdateEvent(List<ChatTextMessage> messages) {
        this.messages = messages;
    }

    public List<ChatTextMessage> getMessages() {
        return messages;
    }
}
