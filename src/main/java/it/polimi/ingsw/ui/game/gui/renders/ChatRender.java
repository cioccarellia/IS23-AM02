package it.polimi.ingsw.ui.game.gui.renders;

import it.polimi.ingsw.model.chat.ChatTextMessage;
import javafx.scene.control.MenuItem;

public class ChatRender {
    public static void renderChatMenuItemOnSelection(MenuItem item, String name) {
        item.setDisable(false);
        item.setVisible(true);
        item.setText(name);
    }

    public static String renderMessage(ChatTextMessage message) {
        String time = message.stamp().toString().split(" ")[1];

        return time + " " + message.senderUsername() + ": " + message.text();
    }
}
