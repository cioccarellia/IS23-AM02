package it.polimi.ingsw.ui.game.cli.printer;

import com.github.tomaslanger.chalk.Chalk;
import it.polimi.ingsw.model.chat.ChatTextMessage;

import java.util.Comparator;
import java.util.List;

public class ChatPrinter {

    public static void printChatLastMessage(List<ChatTextMessage> messages, String owner) {
        if (messages.isEmpty()) {
            return;
        }

        ChatTextMessage lastMessageForUser = messages.get(messages.size() - 1);

        printMessage(lastMessageForUser, lastMessageForUser.senderUsername().equals(owner));
    }

    public static void printLastNMessages(List<ChatTextMessage> messages, String owner, int n) {
        if (messages == null || messages.isEmpty() || n <= 0) {
            return;
        }

        if (n > messages.size()) {
            n = messages.size();
        }

        List<ChatTextMessage> sortedMessages = messages.stream()
                .sorted(Comparator.comparingInt(it -> it.stamp().getNanos()))
                .toList();

        for (int i = 0; i < n; i++) {
            ChatTextMessage message = sortedMessages.get(i);

            printMessage(message, message.senderUsername().equals(owner));
        }
    }

    private static void printMessage(ChatTextMessage message, boolean fromSelf) {
        String fullTime = message.stamp().toString().split(" ")[1];
        String time = fullTime.substring(0, fullTime.length() - 2);

        StringBuilder sb = new StringBuilder();

        if (fromSelf) {
            sb.append("chat(!) ");
        } else {
            sb.append("chat    ");
        }

        sb.append("@").append(time).append(" ");


        switch (message.messageRecipient()) {
            case it.polimi.ingsw.model.chat.MessageRecipient.Broadcast broadcast -> {
                sb.append("[").append(message.senderUsername()).append("]: ").append(message.text()).append("\n");
                var chalk = Chalk.on(sb.toString()).bgGreen().toString();
                System.out.print(chalk);
            }
            case it.polimi.ingsw.model.chat.MessageRecipient.Direct direct -> {
                sb.append("[").append(message.senderUsername()).append(" -> ").append(direct.username()).append("]: ").append(message.text()).append("\n");
                var chalk = Chalk.on(sb.toString()).bgBlue().toString();
                System.out.print(chalk);
            }
        }
    }

}
