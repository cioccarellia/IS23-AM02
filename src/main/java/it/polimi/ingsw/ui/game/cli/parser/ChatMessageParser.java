package it.polimi.ingsw.ui.game.cli.parser;

import it.polimi.ingsw.model.chat.MessageRecipient;

public class ChatMessageParser {

    public static String parseMessageText(String message) {
        if (message == null || message.isEmpty()) return null;

        if (message.contains("[") && message.contains("]")) {
            return message.split("]")[1];
        } else {
            // broadcast message
            return message;
        }
    }

    public static MessageRecipient parseMessageRecipient(String message) {
        if (message == null || message.isEmpty()) return null;

        if (message.startsWith("[") && message.contains("]")) {
            String splitRightBracket = message.split("]")[0];
            String splitFull = splitRightBracket.substring(1);
            return new MessageRecipient.Direct(splitFull);
        } else {
            // broadcast message
            return new MessageRecipient.Broadcast();
        }
    }
}
