package it.polimi.ingsw.ui.game.cli.printer;

import com.github.tomaslanger.chalk.Chalk;
import it.polimi.ingsw.model.chat.ChatTextMessage;
import it.polimi.ingsw.ui.game.cli.Console;

import java.util.Comparator;
import java.util.List;

public class ChatPrinter {

    public static void printChatLastMessage(List<ChatTextMessage> messages, String owner) {
        if (messages.isEmpty()) {
            return;
        }

        ChatTextMessage lastMessageForUser = messages.get(messages.size() - 1);

        if (owner.equals(lastMessageForUser.senderUsername())) {
            // if the owner sent the message I don't want to read it back
            return;
        }

        printMessage(lastMessageForUser);
    }

    public static void printLastNMessages(List<ChatTextMessage> messages, String owner, int n) {
        if (messages == null || messages.isEmpty() || n <= 0) {
            return;
        }

        if (n > messages.size()) {
            n = messages.size();
        }

        var sortedMessages = messages.stream()
                .sorted((Comparator.comparingInt(it -> it.stamp().getNanos())))
                .toList();

        for (int i = n; i > 0; i--) {
            printMessage(sortedMessages.get(i - 1));
        }
    }

    private static void printMessage(ChatTextMessage message) {
        String fullTime = message.stamp().toString().split(" ")[1];
        String time = fullTime.substring(0, fullTime.length() - 2);

        switch (message.messageRecipient()) {
            case it.polimi.ingsw.model.chat.MessageRecipient.Broadcast broadcast -> {
                String out = time + " [" + message.senderUsername() + "]: " + message.text() + "\n";
                Console.out(Chalk.on(out).bgGreen().toString());
            }
            case it.polimi.ingsw.model.chat.MessageRecipient.Direct direct -> {
                String out = time + " [" + message.senderUsername() + " > " + direct.username() + "]: " + message.text() + "\n";
                Console.out(Chalk.on(out).bgBlue().toString());
            }
        }
    }

}
